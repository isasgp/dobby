from concurrent import futures
import grpc
import UrlAnalyzer_pb2
import UrlAnalyzer_pb2_grpc
import deeplearning

# URLCheckerService의 서비스 구현
class UrlAnalyzerService(UrlAnalyzer_pb2_grpc.UrlAnalyzerServiceServicer):
    def CheckURL(self, request, context):   # isMalicious: False -> 정상 도메인, True -> 악성 도메인
        isMalicious = False
        features = deeplearning.validate_malicious_domain(request.url)

        if features == 1:
            isMalicious = True
        return UrlAnalyzer_pb2.UrlAnalyzerResponse(isMalicious=isMalicious)

# 서버 시작
def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    UrlAnalyzer_pb2_grpc.add_UrlAnalyzerServiceServicer_to_server(UrlAnalyzerService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server is running on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
