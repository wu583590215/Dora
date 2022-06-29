package com.wq.dora;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wq.dora.mapper")
public class DoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoraApplication.class, args);
    }

}
