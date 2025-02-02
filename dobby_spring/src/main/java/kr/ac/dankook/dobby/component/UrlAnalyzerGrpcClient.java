package kr.ac.dankook.dobby.component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import urlanalyzer.UrlAnalyzerServiceGrpc;
import urlanalyzer.UrlAnalyzer;


@Component
public class UrlAnalyzerGrpcClient {
    private final UrlAnalyzerServiceGrpc.UrlAnalyzerServiceBlockingStub stub;

    public UrlAnalyzerGrpcClient(@Value("${spring.grpc.host}") String grpcHost, @Value("${spring.grpc.port}") int grpcPort) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
        stub = UrlAnalyzerServiceGrpc.newBlockingStub(channel);
    }

    // gRPC를 통해 AI 모델과 통신하여 URL을 검사
    public boolean analyzeUrlUsingAI(String url) {
        // URL 요청 메시지 생성
        UrlAnalyzer.UrlAnalyzerRequest request = UrlAnalyzer.UrlAnalyzerRequest.newBuilder()
                .setUrl(url)
                .build();

        // AI 모델에 요청하고 응답 받기
        UrlAnalyzer.UrlAnalyzerResponse response = stub.checkURL(request);

        // 응답에서 isMalicious 필드 반환
        return response.getIsMalicious();
    }
}
