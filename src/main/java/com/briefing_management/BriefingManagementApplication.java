package com.briefing_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.briefing_management.*.dao")
public class BriefingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriefingManagementApplication.class, args);
    }

}
