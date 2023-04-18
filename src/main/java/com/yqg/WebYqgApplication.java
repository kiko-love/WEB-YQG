package com.yqg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KIKO
 */
@MapperScan("com.yqg.mapper")
@SpringBootApplication
public class WebYqgApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebYqgApplication.class, args);
    }

}
