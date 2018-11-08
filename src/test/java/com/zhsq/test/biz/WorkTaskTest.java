package com.zhsq.test.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.drools.SessionFactory;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.SimpleEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;
import com.zhsq.biz.timertask.worktask.WorkTaskTime;
@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkTaskTest {
	
	private static Logger logger = Logger.getLogger(WorkTaskTest.class);
	protected String mapperName = "工作任务";

   @Before
    public void setUp() throws Exception {
        System.out.println("------------Before------------");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("------------After ------------");
    }
	
    @Test
	public void readData() {

		/*long startTime = System.currentTimeMillis();
		BizFusionContext context=new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
//		context.setToEntityRange(BizFusionContext.ENTITY_CONTENT_RANGE_ABCNODE_CONTAIN);
		context.setUserCode("e10adc3949ba59abbe56e057f28888u5");
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		String code=integration.integrate(entity, context);
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		logger.debug(code + " ---:--- "+ result.toJson());
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);*/
	}

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("唯一编码", "184d0b0b345248e4b07f87bef2db43ef");
		entity.putValue("任务标题", "ffffffff测试任务超时未否qqqqqq"); 
		entity.putValue("任务状态", 30126);
		entity.putValue("任务结束时间", "2018-09-12");
		entity.putValue("任务是否超时", "否");
		entity.putValue("保存派发", "派发");
		
		Entity relationentity = new Entity("用户");
		relationentity.putValue("唯一编码", "66c56da7f1954f689ba9dd78668dd5fb");
		relationentity.putValue("用户名", "xfqsg1");
		entity.putRelationEntity("任务执行人","任务执行人", relationentity);
		
//		Entity relationentity1 = new Entity("用户");
//		relationentity1.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888u5");
//		relationentity1.putValue("用户名", "admin");
//		entity.putRelationEntity("任务创建人","创建人", relationentity1);
		return entity;
	}
	

}