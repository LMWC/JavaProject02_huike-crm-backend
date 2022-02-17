package com.huike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 * 
 * 
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableScheduling
public class HuiKeApplication{

    public static void main(String[] args){
        SpringApplication.run(HuiKeApplication.class, args);
    }

}
