import socket
import sys
import threading
import select
import time

serverIP = "127.0.0.1"
multicastIP = "224.0.0.1"
server_port = 9008
multicast_port = 9009
nick = ""
tcp_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_multicast_client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
CLIENT = [tcp_client, udp_client, udp_multicast_client]
time.sleep(2)
print_lock = threading.Lock()
THREADS = []
_running = True


def multicast_initialization():
    udp_multicast_client.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  # bind all sockets to the same addr and port
    udp_multicast_client.bind((serverIP, multicast_port))
    membership = socket.inet_aton(multicastIP) + socket.inet_aton(serverIP)
    udp_multicast_client.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, membership)
    udp_multicast_client.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 1)


def recv_msg():
    while _running:
        readable, _, _ = select.select(CLIENT, [], [], 5)
        for sock in readable:
            if sock == tcp_client:
                recv_tcp_msg()
            elif sock == udp_client:
                recv_udp_msg()
            elif sock == udp_multicast_client:
                recv_multicast_msg()


def recv_tcp_msg():
    try:
        message = tcp_client.recv(2048).decode()
        print_message(message)
    except ConnectionResetError:
        return


def recv_udp_msg():
    try:
        buff, address = udp_client.recvfrom(65536)
        print_message(buff.decode())
    except ConnectionResetError:
        return


def recv_multicast_msg():
    try:
        buff, address = udp_multicast_client.recvfrom(65536)
        msg = buff.decode()
        if msg[:msg.find(':')] == nick:
            return
        print_message(msg)
    except ConnectionResetError:
        return


def send_nick():
    global nick
    global _running
    nick = sys.stdin.readline()[:-1]
    if nick == "exit":
        handle_exit()
    else:
        tcp_client.send(bytes(nick, 'utf-8'))


def send_msg():
    global _running
    try:
        while _running:
            message = sys.stdin.readline()
            if message == "exit\n":
                handle_exit()
                return
            elif message[0:2] == "U ":
                if message[2:9] == "default":
                    with open('message.txt', 'r') as file:
                        data = file.read()
                        udp_client.sendto(bytes(data, 'utf-8'), (serverIP, server_port))
                else:
                    udp_client.sendto(bytes(message[2:], 'utf-8'), (serverIP, server_port))
            elif message[0:2] == "M ":
                udp_multicast_client.sendto(bytes(nick + ": " + message[2:], 'utf-8'), (multicastIP, multicast_port))
            else:
                tcp_client.send(bytes(message, 'utf-8'))
            print_message("You: " + message)
    except ConnectionResetError:
        print('Server shutdown')
        _running = False
        return


def handle_exit():
    global _running
    tcp_client.send(bytes("exit", 'utf-8'))
    _running = False


def print_message(msg):
    print_lock.acquire()
    print(msg, flush=True)
    print_lock.release()


def main():
    try:
        tcp_client.connect((serverIP, server_port))
        udp_client.bind(tcp_client.getsockname())
        multicast_initialization()
    except ConnectionError:
        print('Connection failed')
        tcp_client.close()
        udp_client.close()
        udp_multicast_client.close()
        return
    print('Client successfully connected! Enter your nick')
    send_nick()
    try:
        THREADS.append(threading.Thread(target=send_msg))
        THREADS.append(threading.Thread(target=recv_msg))
        for thread in THREADS:
            thread.start()
    except KeyboardInterrupt:
        print('Goodbye!')
    finally:
        for thread in THREADS:
            thread.join()
        tcp_client.close()
        udp_client.close()
        udp_multicast_client.close()
        print('cleaned up!')


if __name__ == '__main__':
    main()
