package kr.ac.dankook.dobby.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bloom.filter")
public class BloomFilterConfig {
    private String redisHost;
    private int redisPort;
    private String key;
    private double errorRate;
    private long capacity;
    private String urlFilePath;
}

