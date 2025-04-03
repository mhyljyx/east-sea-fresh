package com.east.sea.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 布隆过滤器 配置属性
 * @author : tztang
 * @since 2025/04/03
 **/
@Data
@ConfigurationProperties(prefix = BloomFilterProperties.PREFIX)
public class BloomFilterProperties {

    public static final String PREFIX = "bloom-filter";
    
    private String name;
    
    private Long expectedInsertions = 20000L;
    
    private Double falseProbability = 0.01D;

}
