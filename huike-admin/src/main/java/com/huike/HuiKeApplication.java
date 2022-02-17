package com.huike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.huike.clues.service.ITbActivityService;

/**
 * 启动程序
 * 
 * 
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableScheduling
public class HuiKeApplication implements CommandLineRunner {

	@Autowired
    private ITbActivityService activityService;

    public static void main(String[] args){
        SpringApplication.run(HuiKeApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        try{
            //加载所有活动code到缓存
            activityService.loadAllActivityCode();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
