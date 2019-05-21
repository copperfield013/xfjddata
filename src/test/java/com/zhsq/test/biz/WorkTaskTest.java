package com.zhsq.test.biz;


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
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

    	long startTime = System.currentTimeMillis();
		BizFusionContext context=new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
//		context.setToEntityRange(BizFusionContext.ENTITY_CONTENT_RANGE_ABCNODE_CONTAIN);
		context.setUserCode("e10adc3949ba59abbe56e057f28888d5");
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		IntegrationMsg imsg=integration.integrate(context,entity);
		String code=imsg.getCode();
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		/*logger.debug(code + " : "+ result.toJson());*/
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		//entity.putValue("唯一编码", "ddcce68feae8407c8ac7ca451ad89d67");
		entity.putValue("任务标题", "第9个任务"); 
		entity.putValue("任务状态", "新建");
		entity.putValue("任务结束时间", "2019-02-15");
		entity.putValue("任务是否超时", "否");
		entity.putValue("保存派发", "保存");
		
		Entity relationentity = new Entity("任务执行人");
		relationentity.putValue("唯一编码", "4f884568d75b47f4b2de0b37bc4407b4");
		relationentity.putValue("用户名", "xfjd");
		entity.putRelationEntity("任务执行人","任务执行人", relationentity);
		
		/*Entity relationentity1 = new Entity("用户");
		relationentity1.putValue("唯一编码", "e10adc3949ba59abbe56e057f28888u5");
		relationentity1.putValue("用户名", "admin");
		entity.putRelationEntity("任务创建人","创建人", relationentity1);*/
		return entity;
	}
	
}