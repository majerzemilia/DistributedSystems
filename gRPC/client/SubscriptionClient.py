import grpc
import sys
import time
from grpc._channel import _Rendezvous

sys.path.insert(1, './gen')
import subscription_pb2
import subscription_pb2_grpc

channel = None
stub = None
category = None
is_alive = False


def initialize():
    global channel, stub, is_alive
    channel = grpc.insecure_channel('localhost:50051')
    stub = subscription_pb2_grpc.SubscriptionStub(channel)
    initialize_category()
    print('Waiting for server...')
    wait_for_connection()
    print('Ready to send meetings\' info!\n')
    make_subscription()


def wait_for_connection():
    global is_alive
    while not is_alive:
        try:
            time.sleep(1)
            _ = stub.Ping(subscription_pb2.PingMsg())
        except:
            continue
        is_alive = True
        print('Server reachable\n')


def reconnect():
    wait_for_connection()
    make_subscription()


def ask_for_valid_category():
    global category
    print(
        'Select category to subscribe:\nEnter 0 for BUSINESS\nEnter 1 for FASHION\nEnter 2 for IT\nEnter 3 for MUSIC' +
        '\nEnter 4 for SPORT\n')
    input_category = int(sys.stdin.readline()[:-1])
    if input_category in [0, 1, 2, 3, 4]:
        category = subscription_pb2.Category.Name(input_category)


def initialize_category():
    global category
    while category is None:
        ask_for_valid_category()


def make_subscription():
    global category, is_alive
    subscription_request = subscription_pb2.SubscriptionRequest(category=category)
    try:
        stream = stub.InformAboutMeeting(subscription_request)
        for f in stream:
            print(f)
    except grpc._channel._Rendezvous:
        is_alive = False
        print('Cannot reach server. Begin reconnecting...')
        reconnect()
    except KeyboardInterrupt:
        print('Goodbye!')


def main():
    initialize()


if __name__ == '__main__':
    main()
