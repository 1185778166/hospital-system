package com.maxt.system.hospital.service.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Maxt
 * @Date 2022/4/7 07:25
 * @Version 1.0
 * @Description
 */
@SpringBootApplication
@EnableSwagger2
public class CommonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }
}
