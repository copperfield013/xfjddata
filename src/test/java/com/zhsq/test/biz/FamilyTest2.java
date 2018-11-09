package com.zhsq.test.biz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.SimpleEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.constant.family.FamilyItem;
import com.zhsq.biz.constant.people.PeopleItem;
import com.zhsq.biz.people.algorithm.BirthdayIntrospection;
import com.zhsq.biz.people.algorithm.IDIntrospection;
import com.zhsq.biz.timertask.people.PeopleTimeTask;
@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FamilyTest2 {
	
	private static Logger logger = Logger.getLogger(FamilyTest2.class);
	protected String mapperName = "家庭信息";
	
	@Test
	public void readData() {

		long startTime = System.currentTimeMillis();
		BizFusionContext context=new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
//		context.setToEntityRange(BizFusionContext.ENTITY_CONTENT_RANGE_ABCNODE_CONTAIN);
		context.setUserCode("e10adc3949ba59abbe56e057f28888u5");
		Integration integration=PanelFactory.getIntegration();
		Entity entity=createEntity(mapperName);
		logger.debug(entity.toJson());
		IntegrationMsg imsg=integration.integrate(context,entity);
		String code=imsg.getCode();
		Discoverer discoverer=PanelFactory.getDiscoverer(context);
		Entity result=discoverer.discover(code);
		logger.debug(code + " : "+ result.toJson());
		
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	private Entity createEntity(String mappingName) {
		Entity entity = new Entity(mappingName);
		entity.putValue("唯一编码", "a7eede5431324da0bfxerc71484a331p");
		entity.putValue("户籍地址", "西湖路1263号"); 
		entity.putValue("家庭总人数", "4");
		entity.putValue("家庭分类", EnumKeyValue.ENUM_家庭分类_户籍家庭);
		
		Entity relationentity = new Entity("人口信息");
		relationentity.putValue("唯一编码", "a7eede54313e4daabfxer1714843331d");
		relationentity.putValue("姓名", "未发文"); 
		relationentity.putValue("人口类型", "户籍人口");
		relationentity.putValue("和户主关系", EnumKeyValue.ENUM_和户主关系_户主);
		relationentity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		relationentity.putValue("身份证号码", "110101194810130545");
		entity.putRelationEntity("家庭人口","户主", relationentity);
		
		/*Entity relationentity1 = new Entity("人口信息");
		relationentity1.putValue("唯一编码", "a7eede54323c4da0bfdere714848331t");
		relationentity1.putValue("姓名", "晓强老婆"); 
		relationentity1.putValue("和户主关系", EnumKeyValue.ENUM_和户主关系_配偶);
		relationentity1.putValue("人口类型", "户籍人口");
		relationentity1.putValue("身份证号码", "110101194810130574");
		relationentity1.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		entity.putRelationEntity("家庭人口","成员",relationentity1);*/
		/*
		Entity relationentity2 = new Entity("人口信息");
		relationentity2.putValue("唯一编码", "a7eede54323c4da0bfdere714848331k");
		relationentity2.putValue("姓名", "晓强女儿"); 
		relationentity2.putValue("和户主关系", EnumKeyValue.ENUM_和户主关系_女);
		relationentity2.putValue("人口类型", "户籍人口");
		relationentity2.putValue("身份证号码", "110101194810130578");
		relationentity2.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		entity.putRelationEntity("家庭人口","户主",relationentity2);
	*/
		
		return entity;
	}
	
}
