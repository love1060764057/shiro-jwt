package com.tongchenggo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com/tongchenggo/Mapper")
@SpringBootApplication
public class TongchenggoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TongchenggoApplication.class, args);
    }

}
