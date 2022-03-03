package com.wiredcraft.testing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wiredcraft.testing.*.mapper")
public class TestBackendJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBackendJavaApplication.class, args);
    }

}
