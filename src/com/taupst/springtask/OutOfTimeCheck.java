package com.taupst.springtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taupst.controller.BaseController;

@Component("outOfTimeCheck")
public class OutOfTimeCheck extends BaseController{

	//private static Logger log = Logger.getLogger(OutOfTimeCheck.class.getName());
	
	//@Scheduled(cron = "0 0 0 * * ?") //每天凌晨执行
	//@Scheduled(cron = "0/1 * * * * ?") //每天凌晨执行
	public void job(){
		/*String dateStart = util.getDate(0, null);
		log.info(dateStart + "  定时任务执行开始......");
		if(taskService.updateTaskState()){
			String dateSucceed = util.getDate(0, null);
			log.info(dateSucceed + "  定时任务执行成功......");
		}else{
			String dateFail = util.getDate(0, null);
			log.info(dateFail + "  定时任务执行失败......");
		}
		String dateEnd = util.getDate(0, null);
		log.info(dateEnd + "  定时任务执行结束......");*/
	}
}
