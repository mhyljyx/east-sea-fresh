package com.east.sea.genie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 旅小秘系统启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.east.sea"})
public class TrigGenieApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrigGenieApplication.class, args);
    }
}
