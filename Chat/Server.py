import socket
import concurrent.futures
import select

server_port = 9008
tcp_server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
udp_server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
tcp_server_socket.bind(('localhost', server_port))
udp_server_socket.bind(('localhost', server_port))
SERVER = [tcp_server_socket, udp_server_socket]
CLIENTS = []
tcp_server_socket.listen()


def send_server_msg(message):
    for (nick, sock, addr) in CLIENTS:
        try:
            sock.send(bytes(message, 'utf-8'))
        except ConnectionError:
            remove_client((nick, sock, addr))


def send_tcp_data(client, message):
    (sender_nick, sender_sock, sender_addr) = client
    for (nick, sock, _) in CLIENTS:
        if nick == sender_nick and sock == sender_sock:
            continue
        try:
            sock.send(bytes(sender_nick + ": " + message, 'utf-8'))
        except ConnectionError:
            remove_client(client)


def send_udp_data(client_address, message):
    (sender_nick, sender_sock, sender_addr) = find_client_by_address(client_address)
    for (nick, _, address) in CLIENTS:
        if nick == sender_nick and address == sender_addr:
            continue
        udp_server_socket.sendto(bytes(sender_nick + ": " + message, 'utf-8'), address)


def connected_client(sockfd):
    for (nick, sock, _) in CLIENTS:
        if sock == sockfd:
            return True
    return False


def find_client_by_socket(sockfd):
    for (nick, sock, addr) in CLIENTS:
        if sock == sockfd:
            return nick, sock, addr


def find_client_by_address(address):
    for (nick, sock, addr) in CLIENTS:
        if addr == address:
            return nick, sock, addr


def remove_client(client):
    nick, sock, addr = client
    sock.close()
    CLIENTS.remove(client)
    send_server_msg(nick + ' left the room\n')


def handle_tcp_connection(sockfd, addr):
    while True:
        data = sockfd.recv(4096).decode()
        if data == "exit":
            if connected_client(sockfd):
                remove_client(find_client_by_socket(sockfd))
            return
        if not connected_client(sockfd):
            nick = data
            CLIENTS.append((nick, sockfd, addr))
            send_server_msg(nick + " entered the room\n")
        else:
            send_tcp_data(find_client_by_socket(sockfd), data)


def main():
    print('PYTHON SERVER')
    with concurrent.futures.ThreadPoolExecutor(max_workers=20) as executor:
        try:
            while True:
                readable, _, _ = select.select(SERVER, [], [])
                for sock in readable:
                    if sock == tcp_server_socket:
                        sockfd, addr = tcp_server_socket.accept()
                        executor.submit(handle_tcp_connection, sockfd, addr)
                    elif sock == udp_server_socket:
                        buff, address = udp_server_socket.recvfrom(65536)
                        send_udp_data(address, buff.decode())
        except KeyboardInterrupt:
            send_server_msg("Server shutdown")
        finally:
            udp_server_socket.close()
            tcp_server_socket.close()


if __name__ == '__main__':
    main()
