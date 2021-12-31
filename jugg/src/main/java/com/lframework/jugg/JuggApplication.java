package com.lframework.jugg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.lframework")
@MapperScan("com.lframework.**.mappers")
public class JuggApplication {

    public static void main(String[] args) {

        SpringApplication.run(JuggApplication.class, args);
    }
}
