package com.taupst.threadconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration  
@ComponentScan(basePackages="com.chszs.threadtask") 
public class ThreadConfig {
	 @Bean  
     public ThreadPoolTaskExecutor taskExecutor(){  
             ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();  
             pool.setCorePoolSize(5);  
             pool.setMaxPoolSize(10);  
             pool.setWaitForTasksToCompleteOnShutdown(true);  
             return pool;  
     }  
}
