package com.zhsq.biz.timertask.worktask;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.timertask.LoadEntityToWorkMemory;
import com.zhsq.biz.worktask.WorkTaskBNBTimer;

@Component
@Lazy(value=false)
public class WorkTaskTime {
/*	
	private String entityType = BaseConstant.TYPE_工作任务;*/
	 //@Scheduled(cron = "0 0/5 * * * ?")//每5分钟执行一次
	//@Scheduled(cron = "0 0 1 * * ?")
	/*public void doSomething() {
		 
		 List<Criteria> buildCriteria = buildCriteria();
		 LoadEntityToWorkMemory.loadEntity(BaseConstant.TYPE_工作任务, buildCriteria, new WorkTaskBNBTimer());
	}*/
	/* 
	 private List<Criteria> buildCriteria(){
			List<Criteria> criterias = new ArrayList<Criteria>();
			BizCriteriaFactory criteriaFactory = new BizCriteriaFactory();
			Criteria common;
			common = criteriaFactory.createOpenBetweenQueryCriteria(WorkTaskItem.任务结束时间, null, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			criterias.add(common);
			return criterias; 
		}*/

}
