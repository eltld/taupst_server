package com.taupst.springtask;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taupst.service.RankingService;

@Component("outOfTimeCheck")
public class OutOfTimeCheck {
	
	@Resource(name="rankingService")
	private RankingService rankingService;
	// private static Logger log =
	// Logger.getLogger(OutOfTimeCheck.class.getName());

	//@Scheduled(cron = "0 0 0 1 * ?") //每月的1号零点触发
	// @Scheduled(cron = "0 0 0 * * ?") //每天凌晨执行
	@Scheduled(cron = "0/1 * * * * ?") //每天凌晨执行
	public void job() {
		/*
		 * String dateStart = util.getDate(0, null); log.info(dateStart +
		 * "  定时任务执行开始......"); if(taskService.updateTaskState()){ String
		 * dateSucceed = util.getDate(0, null); log.info(dateSucceed +
		 * "  定时任务执行成功......"); }else{ String dateFail = util.getDate(0, null);
		 * log.info(dateFail + "  定时任务执行失败......"); } String dateEnd =
		 * util.getDate(0, null); log.info(dateEnd + "  定时任务执行结束......");
		 */
		//File file = new File(File.separator);
		//System.out.println(file.getAbsolutePath());
	}

	//@Scheduled(cron = "* 0/20 * * * ?")
//	@Scheduled(cron = "5/10 * * * * ?")
//	public void deleteCode(HttpServletRequest request) {
//		
//		File file = new File(File.separator);
//		System.out.println(file.getAbsolutePath());
//		System.out.println("request = " + request.toString());
//	}
	@Scheduled(cron = "5/10 * * * * ?")
	public void deleteCode() {
		
		File file = new File("/TaUpst/image/code");
		try {
			//file.exists();
			System.out.println(file.exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("request = " + request.toString());
	}

	public static void main(String[] args) {
//		File file = new File(File.pathSeparator);
		File file = new File("/TaUpst/image/code");
		String filePath = file.getAbsolutePath();
		System.out.println(filePath);
		filePath = filePath.substring(0, filePath.length() - 1) + "image\\code";
		File code = new File(filePath);
		//String[] paths = code.list();
//		for (String string : paths) {
//			System.out.println(string);
//		}
	}
}
