package kr.ac.dankook.dobby.component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kr.ac.dankook.dobby.config.BloomFilterConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@Getter
public class UrlBloomFilter {
    private final JedisPooled jedis;
    private final String bloomFilterKey;
    private final double errorRate;
    private final long capacity;
    private final String urlFilePath;

    public UrlBloomFilter(BloomFilterConfig config) {
        this.jedis = new JedisPooled(config.getRedisHost(), config.getRedisPort());
        this.bloomFilterKey = config.getKey();
        this.errorRate = config.getErrorRate();
        this.capacity = config.getCapacity();
        this.urlFilePath = config.getUrlFilePath();
    }

    @PostConstruct
    public void initializeBloomFilter() {
        // Bloom Filter 생성 (오차율 1%, 최대 100만 개 예상)
        jedis.bfReserve(bloomFilterKey, errorRate, capacity);
        loadUrlsIntoRedis();
    }

    private void loadUrlsIntoRedis() {
        try (BufferedReader br = new BufferedReader(new FileReader(urlFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String url = parts[0];
                boolean isMalicious = "1".equals(parts[1]);

                addUrl(url, isMalicious);
            }
            System.out.println("URL 데이터가 성공적으로 Redis에 로드되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUrl(String url, boolean isMalicious) {
        // Bloom Filter에 URL 추가
        jedis.bfAdd(bloomFilterKey, url);
        // URL 정상/악성 여부를 Redis에 저장
        jedis.set("url:" + url, Boolean.toString(isMalicious));
    }

    @PreDestroy
    public void close() {
        jedis.close();
    }
}
