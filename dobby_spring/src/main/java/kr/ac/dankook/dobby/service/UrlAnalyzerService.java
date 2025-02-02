package kr.ac.dankook.dobby.service;

import kr.ac.dankook.dobby.component.UrlAnalyzerGrpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlAnalyzerService {
    private final UrlFilterService urlFilterService; // Bloom Filter 객체
    private final UrlAnalyzerGrpcClient analyzerGrpcClient; // AI 모델 gRPC 클라이언트 객체


    // URL을 확인하고 악성 여부를 반환하는 메서드
    public boolean checkUrl(String url) {
        // Step 1: Bloom Filter에서 URL 확인
        if (!urlFilterService.mightContain(url)) {
            // System.out.println("존재안함");
            // Bloom Filter에 URL이 없으면 AI 모델로 분석 요청
            return analyzeAndCacheUrl(url);
        }

        
        // Step 2: Redis에서 URL 상태 조회
        Boolean urlStatus  = urlFilterService.isUrlMalicious(url);
        if (urlStatus  != null) {
            // DB 데이터 존재 시 또는 캐싱된 결과가 존재 시 반환
            // System.out.println("존재함");
            return urlStatus;
        } else {
            // Redis에 결과가 없으면 AI 모델로 분석 요청
            // System.out.println("존재안함");
            return analyzeAndCacheUrl(url);
        }
    }

    private boolean analyzeAndCacheUrl(String url) {
        boolean isMalicious = analyzerGrpcClient.analyzeUrlUsingAI(url);
        cacheMaliciousStatusAsync(url, isMalicious);
        return isMalicious;
    }

    @Async
    public void cacheMaliciousStatusAsync(String url, boolean analysisResult) {
        // System.out.printf("분석 url: %s \t 캐싱될 결과: %b\n",url ,analysisResult);
        // 최종 결과를 Redis와 Bloom Filter에 캐싱
        urlFilterService.cacheUrl(url, analysisResult);
    }
}
