package com.maxt.system.hospital.service.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Maxt
 * @Date 2022/4/6 08:56
 * @Version 1.0
 * @Description
 */
@SpringBootApplication
@EnableSwagger2
public class AppointmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentServiceApplication.class);
    }
}
