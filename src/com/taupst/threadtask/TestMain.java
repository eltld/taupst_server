package com.taupst.threadtask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.taupst.threadconfig.ThreadConfig;

public class TestMain {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ThreadConfig.class);
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ctx
				.getBean("taskExecutor");

		SendNews printTask1 = (SendNews) ctx.getBean("sendNews");
		printTask1.setName("Thread 1");
		taskExecutor.execute(printTask1);

		SendNews printTask2 = (SendNews) ctx.getBean("sendNews");
		printTask2.setName("Thread 2");
		taskExecutor.execute(printTask2);

		SendNews printTask3 = (SendNews) ctx.getBean("sendNews");
		printTask3.setName("Thread 3");
		taskExecutor.execute(printTask3);
		for (;;) {
			int count = taskExecutor.getActiveCount();
			System.out.println("Active Threads : " + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				taskExecutor.shutdown();
				break;
			}
		}
	}
}
