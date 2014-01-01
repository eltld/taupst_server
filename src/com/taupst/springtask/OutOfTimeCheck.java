package com.taupst.springtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("outOfTimeCheck")
public class OutOfTimeCheck {
	@Scheduled(cron = "0 0 0 * * ?") //每天凌晨执行
	public void job(){
		System.out.println("任务正在进行。。。");
	}
}
