package kr.ac.dankook.dobby.service;

import kr.ac.dankook.dobby.component.UrlBloomFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlFilterService {
    private final UrlBloomFilter urlBloomFilter;

    public void cacheUrl(String url, boolean isMalicious) {
        urlBloomFilter.addUrl(url, isMalicious);
    }

    public boolean mightContain(String url) {
        // Bloom Filter에서 URL 존재 여부 확인
        return urlBloomFilter.getJedis().bfExists(urlBloomFilter.getBloomFilterKey(), url);
    }

    public Boolean isUrlMalicious(String url) {
        // Redis에서 URL의 정상/악성 여부 조회
        String result = urlBloomFilter.getJedis().get("url:" + url);
        return result != null ? Boolean.parseBoolean(result) : null;
    }

}
