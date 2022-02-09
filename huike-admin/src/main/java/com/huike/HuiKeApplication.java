package com.huike;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.scheduling.annotation.EnableScheduling;
=======
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.huike.clues.service.ITbActivityService;
import com.huike.clues.service.ITbRuleAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

/**
 * 启动程序
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableScheduling
public class HuiKeApplication{

    public static void main(String[] args){
        SpringApplication.run(HuiKeApplication.class, args);
    }

<<<<<<< HEAD
=======
    @Override
    public void run(String... args)  {
        try{
            //加载所有活动code到缓存
            activityService.loadAllActivityCode();
            //加载所有规则
            ruleAssignService.loadRule();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //mp分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
}
