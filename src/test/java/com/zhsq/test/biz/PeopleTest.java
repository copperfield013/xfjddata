package com.zhsq.test.biz;


import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.FusionContext;
import com.abc.auth.constant.AuthConstant;
import com.abc.fuse.improve.attribute.OpsAttribute;
import com.abc.fuse.improve.transfer.BizzAttributeTransfer;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.SimpleEntity;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.abc.rrc.record.Attribute;
import com.zhsq.biz.constant.DateUtils;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.constant.people.PeopleItem;
import com.zhsq.biz.people.algorithm.BirthdayIntrospection;
import com.zhsq.biz.people.algorithm.IDIntrospection;
import com.zhsq.biz.timertask.people.PeopleTimeTask;
@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PeopleTest {
	
	private static Logger logger = Logger.getLogger(PeopleTest.class);
	protected String mapperName = "人口信息";

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
		entity.putValue("唯一编码", "fd1fe04845894d0f97bae06ef9b9fbfa");
		entity.putValue("姓名", "乐乐1"); 
		entity.putValue("人口类型", "户籍人口");
		//entity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_勤丰社区);
		/*entity.putValue("户籍所在地", "杭州ef1");
		entity.putValue("户籍地门牌号", "西湖73829号fefw");*/
		entity.putValue("身份证号码", "110101191503070898");
		//entity.putValue("性别", EnumKeyValue.ENUM_性别_女);
		//entity.putValue("出生日期", "2018-11-14");
		
		/*SimpleEntity sentity = new SimpleEntity("居住信息");
		sentity.putValue("居住地址", "祥符桥社区->红郡公寓->４幢->１单元->９０４室");
		sentity.putValue("居住地门牌号", "");
		entity.putMultiAttrEntity(sentity);*/
		
		//Entity relationentity = new Entity("人口信息");
		//relationentity.putValue("唯一编码", "2350fdeefd2a45048ff2d337b90802c6");
		//relationentity.putValue("姓名", "张三00999"); 
		//relationentity.putValue("人口类型", "户籍人口");
		//relationentity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		//relationentity.putValue("身份证号码", "110101191403070751");
		
		
		//return relationentity;
		
		/*entity.putRelationEntity("子女信息","子女", relationentity);*/
		
		/*Entity relationentity1 = new Entity("人口信息");
		relationentity1.putValue("唯一编码", "0effa5c786034f5388cce004595ef6e8");
		relationentity1.putValue("姓名", "小强大女儿"); 
		relationentity1.putValue("人口类型", "户籍人口");
		relationentity1.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		relationentity1.putValue("身份证号码", "23231112");
		entity.putRelationEntity("子女信息","子女", relationentity1);*/
		
		/*SimpleEntity sentity3 = new SimpleEntity("老人补助信息");
		sentity3.putValue("补助类型", EnumKeyValue.ENUM_老人补助枚举_居家养老补助);
		sentity3.putValue("补助金额", "3232");
		entity.putMultiAttrEntity(sentity3);
		*/
		/*SimpleEntity sentity3 = new SimpleEntity("人口错误信息");
		sentity3.putValue("错误类型", EnumKeyValue.ENUM_错误类型_身份证错误);
		sentity3.putValue("描述", "身份错误4444");
		entity.putMultiAttrEntity(sentity3);*/
		
		/*SimpleEntity sentity2 = new SimpleEntity("残疾信息");
		sentity2.putValue("残疾类别", null);
		sentity2.putValue("残疾等级", EnumKeyValue.ENUM_残疾等级_一级);
		entity.putMultiAttrEntity(sentity2);*/
		
		/*SimpleEntity sentity1 = new SimpleEntity("户籍变更");
		sentity1.putValue("申报人姓名", "李好帅");
		sentity1.putValue("变动前街路巷", "好帅社区1");
		sentity1.putValue("变动前门（楼）详址", "51幢3单元504");
		sentity1.putValue("变动后街路巷", "明星小区1");
		sentity1.putValue("变动后门（楼）详址", "41幢8单元902");
		sentity1.putValue("更改户籍门牌号", EnumKeyValue.ENUM_是否_是);
		sentity1.putValue("变动日期", "2018-10-14");
		entity.putMultiAttrEntity(sentity1);*/
		
		/*SimpleEntity sentity = new SimpleEntity("户籍变更");
		sentity.putValue("申报人姓名", "李好帅");
		sentity.putValue("变动前街路巷", "好帅社区");
		sentity.putValue("变动前门（楼）详址", "5幢3单元504");
		sentity.putValue("变动后街路巷", "明星小区");
		sentity.putValue("变动后门（楼）详址", "4幢8单元902");
		sentity.putValue("更改户籍门牌号", EnumKeyValue.ENUM_是否_是);
		sentity.putValue("变动日期", "2018-10-10");
		entity.putMultiAttrEntity(sentity);*/
		
		
		/*Entity relationentity = new Entity("配置文件名称");*/
//		relationentity.putValue("名字", "刘志华5");
//		entity.putRelationEntity("关系名称", "关系标签（具体关系）", relationentity);
		return entity;
	}
	
	@Test
	public void fun() {
		new PeopleTimeTask().doSomething();
	}
	
	
}
