package com.zhsq.biz.timertask.people;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.query.queryrecord.criteria.CriteriaFactory;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.constant.people.PeopleItem;
import com.zhsq.biz.constant.worktask.WorkTaskItem;
import com.zhsq.biz.people.PeopleBNBTimer;
import com.zhsq.biz.timertask.LoadEntityToWorkMemory;
import com.zhsq.biz.worktask.WorkTaskBNBTimer;

@Component
@Lazy(value=false)
public class PeopleTimeTask {
	/*	
	private String entityType = BaseConstant.TYPE_工作任务;*/
	//@Scheduled(cron = "0 0/3 * * * ?")//每3分钟执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void doSomething() {
		 LoadEntityToWorkMemory.loadEntity(BaseConstant.TYPE_人口信息, buildCriteria(), new PeopleBNBTimer());
	}
	 
	private List<Criteria> buildCriteria(){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Long time = (long) (59*365*24*60*60*1000);
			Date date = new Date(time);
			String format = sdf.format(date);
		
			List<Criteria> criterias = new ArrayList<Criteria>();
			CriteriaFactory criteriaFactory = new CriteriaFactory();
			Criteria common;
			common = criteriaFactory.createBetweenCriteria(BaseConstant.TYPE_人口信息,PeopleItem.出生日期, null, format);
			criterias.add(common);
			return criterias; 
	}

}
