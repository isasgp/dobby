# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import UrlAnalyzer_pb2 as UrlAnalyzer__pb2


class UrlAnalyzerServiceStub(object):
    """서비스 정의
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.CheckURL = channel.unary_unary(
                '/urlanalyzer.UrlAnalyzerService/CheckURL',
                request_serializer=UrlAnalyzer__pb2.UrlAnalyzerRequest.SerializeToString,
                response_deserializer=UrlAnalyzer__pb2.UrlAnalyzerResponse.FromString,
                )


class UrlAnalyzerServiceServicer(object):
    """서비스 정의
    """

    def CheckURL(self, request, context):
        """URL 유효성 검사를 위한 RPC 메서드 정의
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_UrlAnalyzerServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'CheckURL': grpc.unary_unary_rpc_method_handler(
                    servicer.CheckURL,
                    request_deserializer=UrlAnalyzer__pb2.UrlAnalyzerRequest.FromString,
                    response_serializer=UrlAnalyzer__pb2.UrlAnalyzerResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'urlanalyzer.UrlAnalyzerService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class UrlAnalyzerService(object):
    """서비스 정의
    """

    @staticmethod
    def CheckURL(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/urlanalyzer.UrlAnalyzerService/CheckURL',
            UrlAnalyzer__pb2.UrlAnalyzerRequest.SerializeToString,
            UrlAnalyzer__pb2.UrlAnalyzerResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
