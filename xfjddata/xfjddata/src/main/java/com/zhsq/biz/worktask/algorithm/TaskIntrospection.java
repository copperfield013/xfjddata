package com.zhsq.biz.worktask.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIntrospection {
	
	/**
	 * 
	 * @param time 任务结束时间和当前时间进行比较
	 * @return  true 已超时  false： 未超时
	 * @throws ParseException 
	 */
	public static boolean inspectTimeOut(String time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = sdf.parse(time);
		Date date = new Date();
		
		if (parse.getTime() <date.getTime()) {
			return true;
		}
		return false;
	}
}
