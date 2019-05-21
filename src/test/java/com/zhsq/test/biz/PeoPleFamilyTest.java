package com.zhsq.test.biz;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.application.FusionContext;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.improve.ImproveResult;
import com.abc.fuse.improve.OneRoundImprovement;
import com.abc.fuse.improve.ThreeRoundImprovement;
import com.abc.fuse.improve.attribute.FuseAttribute;
import com.abc.fuse.improve.ops.builder.RootRecordBizzOpsBuilder;
import com.abc.fuse.improve.transfer.BizzAttributeTransfer;
import com.abc.mapping.entity.Entity;
import com.abc.mapping.entity.EntityRelationParser;
import com.abc.message.Message;
import com.abc.ops.builder.RecordRelationOpsBuilder;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.panel.Discoverer;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.record.RootRecord;
import com.abc.transfer.builder.IRootRecordBuilder;
import com.abc.transfer.builder.RootRecordBuilderFactory;
import com.zhsq.biz.common.AuthAlgorithm;
import com.zhsq.biz.constant.BaseConstant;
import com.zhsq.biz.constant.CommonAlgorithm;
import com.zhsq.biz.constant.EnumKeyValue;
import com.zhsq.biz.constant.RelationType;
import com.zhsq.biz.constant.family.FamilyItem;
import com.zhsq.biz.constant.people.PeopleItem;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PeoPleFamilyTest {

	private static Logger logger = Logger.getLogger(PeoPleFamilyTest.class);
	protected String mappingName = "人口信息";

	@Test
	public void fuse() {
		// Session session = sessionFactory.getCurrentSession();
		long startTime = System.currentTimeMillis();
		BizFusionContext context = new BizFusionContext();
		context.setSource(FusionContext.SOURCE_COMMON);
		 context.putBizMap("XFJDE001", new PeopleFamilyBNB());
		// context.setDictionaryMappingName(dictionaryMappingName);
		Integration integration = PanelFactory.getIntegration();
		Entity entity = createEntity(mappingName,null);
		logger.debug(entity.toJson());
		IntegrationMsg msg = integration.integrate(context, entity);
		String code = msg.getCode();
		/*Discoverer discoverer = PanelFactory.getDiscoverer(context);
		Entity result = discoverer.discover(code);
		logger.debug(code + " : " + result.toJson());*/
		//String editTime1=result.getDateStringValue("编辑时间");
		//EntityRelationParser entityRelationParser = new EntityRelationParser(result);
		// entityRelationParser.getRelatedCodes().forEach(c->{
		// logger.debug(entityRelationParser.getRelatedEntity(c).toJson());
		// });
		/*entityRelationParser.getRelationNames().forEach(c -> {
			entityRelationParser.getRelatedCodes(c).forEach(d -> {
				logger.debug(entityRelationParser.getRelatedLabelStr(c, d));
				logger.debug(entityRelationParser.getRelatedEntity(c, d).toJson());
			});
		});*/
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entity = updateEntity(mappingName,code);
		msg = integration.integrate(context, entity);
		/*result = discoverer.discover(code);
		logger.debug("更新后"+code + " : " + result.toJson());
		String editTime2=result.getDateStringValue("编辑时间");*/
		
		//assertEquals(true, editTime2.compareTo(editTime1)>0);
		
		//logger.debug(new EntityRelationParser(result).toJson());
		long endTime = System.currentTimeMillis();// 记录结束时间
		logger.debug((float) (endTime - startTime) / 1000);
	}

	private Entity createEntity(String mappingName,String code) {
		Entity entity = new Entity(mappingName);
		entity.putValue("唯一编码", code);
		entity.putValue("姓名", "张三丰");
		entity.putValue("身份证号码", "370105196906281912");
		entity.putValue("所属社区", EnumKeyValue.ENUM_祥符街道社区_祥符桥社区);
		entity.putValue("户籍所在地", "西湖区1号");
		entity.putValue("是否死亡", "否");
		
		/*Entity relationentity = new Entity("户籍家庭");
		//relationentity.putValue("唯一编码", "2350fdeefd2a45048ff2d337b90802c6");
		relationentity.putValue("家庭分类", "户籍家庭"); 
		relationentity.putValue("户籍地址", "西湖区1号");
		
		entity.putRelationEntity("户籍家庭","户主家庭", relationentity);*/
		return entity;
	}
	
	private Entity updateEntity(String mappingName,String code) {
		Entity entity = new Entity(mappingName);
		entity.putValue("唯一编码", code);
		entity.putValue("户籍所在地", "西湖区2号");
		
		/*Entity relationentity = new Entity("户籍家庭");
		//relationentity.putValue("唯一编码", "2350fdeefd2a45048ff2d337b90802c6");
		relationentity.putValue("家庭分类", "户籍家庭"); 
		relationentity.putValue("户籍地址", "西湖区2号");
		
		entity.putRelationEntity("户籍家庭","户主家庭", relationentity);*/
		
		return entity;
	}

	protected class PeopleFamilyBNB implements BizNoBusy, IdentityQuery, ThreeRoundImprovement, IFusitionCallBack {

		@Override
		public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
			return null;
		}

		@Override
		public ImproveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
				RecordComplexus recordComplexus) {
			return null;
		}

		@Override
		public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
			
			RootRecord record = recordComplexus.getRootRecord(recordCode);
			RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(record.getName(),
					recordCode);
			recordRelationOpsBuilder.putRelation(RelationType.RR_人口信息_可被访问_权限,AuthAlgorithm.getAreaAuthCode(EnumKeyValue.ENUM_祥符街道社区_祥符桥社区));
			ImproveResult imprveResult = new ImproveResult();
			//imprveResult.setRootRecordOps(rootRecordOpsBuilder.getRootRecordOps());
			imprveResult.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());
			//imprveResult.setGeneratedRecords(rootRecordList);
			
			return imprveResult;
		} 

		@Override
		public boolean afterFusition(String recordCode, BizFusionContext context) {

			return false;
		}

		@Override
		public ImproveResult postImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
			return null;
		}

		@Override
		public ImproveResult secondImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
			RootRecord recordroot = recordComplexus.getRootRecord(recordCode);
			Object dataValue = CommonAlgorithm.getDataValue(recordComplexus, recordCode, PeopleItem.户籍所在地);
			
			List<RootRecord> rootRecordList = new ArrayList<RootRecord>();
			//存放新建
			List<RecordRelationOpsBuilder> recordRelationOpsBuilderNew = new ArrayList<RecordRelationOpsBuilder>();
			RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordroot.getName(),
					recordCode);
			
			// 组装结果
			RootRecordBizzOpsBuilder rootRecordOpsBuilder = RootRecordBizzOpsBuilder.getInstance(recordroot.getName(), recordCode);
			//新建家庭
			IRootRecordBuilder iRootRecordBuilder = RootRecordBuilderFactory.getPersistRootRecordBuilder(BaseConstant.TYPE_家庭信息);
			iRootRecordBuilder.putAttribute(FamilyItem.户籍地址, dataValue);
			iRootRecordBuilder.putAttribute(FamilyItem.家庭分类, EnumKeyValue.ENUM_家庭分类_户籍家庭);
			RootRecord record = iRootRecordBuilder.getRootRecord();
			rootRecordList.add(record);
			
			//添加关系
			RecordRelationOpsBuilder builderNew = RecordRelationOpsBuilder.getInstance(BaseConstant.TYPE_家庭信息,
						record.getCode());
			builderNew.putRelation(RelationType.RR_家庭信息_成员_人口信息, recordCode); 
			recordRelationOpsBuilderNew.add(builderNew);	
			
			ImproveResult imprveResult = new ImproveResult();
			imprveResult.setRootRecordOps(rootRecordOpsBuilder.getRootRecordOps());
			imprveResult.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());
			imprveResult.setGeneratedRecords(rootRecordList);
			for (RecordRelationOpsBuilder builder : recordRelationOpsBuilderNew) {
				imprveResult.putDerivedRecordRelationOps(builder.getRecordRelationOps());
			}
			
			return imprveResult;
		}

		@Override
		public ImproveResult thirdImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {

			return null;
		}

	}

}
