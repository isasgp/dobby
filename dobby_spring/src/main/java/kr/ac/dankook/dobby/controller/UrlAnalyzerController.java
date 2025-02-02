package kr.ac.dankook.dobby.controller;

import kr.ac.dankook.dobby.dto.UrlAnalyzerResponse;
import kr.ac.dankook.dobby.service.UrlAnalyzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlAnalyzerController {
    private final UrlAnalyzerService urlAnalysisService;

    // URL을 수신하여 악성 여부를 체크하고, 결과를 반환하는 엔드포인트
    @GetMapping
    public UrlAnalyzerResponse analyzeUrl(@RequestParam String url) {
        boolean isMalicious = urlAnalysisService.checkUrl(url);
        return new UrlAnalyzerResponse(isMalicious); // JSON 형식으로 응답 반환
    }
}
