# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
import grpc

import subscription_pb2 as subscription__pb2


class SubscriptionStub(object):
    """Missing associated documentation comment in .proto file"""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.InformAboutMeeting = channel.unary_stream(
                '/subscription.Subscription/InformAboutMeeting',
                request_serializer=subscription__pb2.SubscriptionRequest.SerializeToString,
                response_deserializer=subscription__pb2.Event.FromString,
                )
        self.Ping = channel.unary_unary(
                '/subscription.Subscription/Ping',
                request_serializer=subscription__pb2.PingMsg.SerializeToString,
                response_deserializer=subscription__pb2.PingMsg.FromString,
                )


class SubscriptionServicer(object):
    """Missing associated documentation comment in .proto file"""

    def InformAboutMeeting(self, request, context):
        """Missing associated documentation comment in .proto file"""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def Ping(self, request, context):
        """Missing associated documentation comment in .proto file"""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_SubscriptionServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'InformAboutMeeting': grpc.unary_stream_rpc_method_handler(
                    servicer.InformAboutMeeting,
                    request_deserializer=subscription__pb2.SubscriptionRequest.FromString,
                    response_serializer=subscription__pb2.Event.SerializeToString,
            ),
            'Ping': grpc.unary_unary_rpc_method_handler(
                    servicer.Ping,
                    request_deserializer=subscription__pb2.PingMsg.FromString,
                    response_serializer=subscription__pb2.PingMsg.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'subscription.Subscription', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class Subscription(object):
    """Missing associated documentation comment in .proto file"""

    @staticmethod
    def InformAboutMeeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(request, target, '/subscription.Subscription/InformAboutMeeting',
            subscription__pb2.SubscriptionRequest.SerializeToString,
            subscription__pb2.Event.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def Ping(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/subscription.Subscription/Ping',
            subscription__pb2.PingMsg.SerializeToString,
            subscription__pb2.PingMsg.FromString,
            options, channel_credentials,
            call_credentials, compression, wait_for_ready, timeout, metadata)
