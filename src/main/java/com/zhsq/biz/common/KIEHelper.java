package com.zhsq.biz.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import com.abc.application.BizFusionContext;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.improve.ImprveResult;
import com.abc.fuse.improve.attribute.leaf.FuseLeafAttribute;
import com.abc.fuse.improve.ops.builder.RecordRelationOpsBuilder;
import com.abc.fuse.improve.ops.builder.RootRecordOpsBuilder;
import com.abc.fuse.improve.ops.complexus.OpsComplexus;
import com.abc.fuse.improve.transfer.BizzAttributeTransfer;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.abc.rrc.query.criteria.BizzCriteriaFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.record.RootRecord;
import com.abc.transfer.builder.IRootRecordBuilder;
import com.abc.transfer.builder.RootRecordBuilderFactory;
import com.zhsq.biz.constant.BaseConstant;
import com.abc.rrc.record.Attribute;

public class KIEHelper {
	
	public static List<Criteria> getBizCriteriaListFromKIE(String recordCode, RecordComplexus complexus,
			KieSession kSession) {
		RootRecord record = complexus.getHostRootRecord();
		//List<Criteria> criteriaList = new ArrayList<Criteria>();

		BizzAttributeTransfer.transfer(record).forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		kSession.setGlobal("recordName", record.getName());
		
		BizzCriteriaFactory bizzCriteriaFactory =null;
		try {
			bizzCriteriaFactory = new BizzCriteriaFactory(record.getName());
			kSession.setGlobal("bizzCriteriaFactory", bizzCriteriaFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// kSession.startProcess("peopleQuery");

		kSession.fireAllRules();
		
		List<Criteria> criteriaList = bizzCriteriaFactory.getCriterias();
		
		/*QueryResults results = kSession.getQueryResults("query criteria");

		for (QueryResultsRow row : results) {
			criteriaList.add((Criteria) row.get("criteria"));
		}*/
		
		kSession.destroy();
		return criteriaList;
	}

	public static ImprveResult getImproveResultFromKIE(BizFusionContext bizFusionContext, String recordCode,
			OpsComplexus opsComplexus, RecordComplexus recordComplexus, KieSession kSession) {
		String userCode = bizFusionContext.getUserCode();
		
		RootRecord rootRecord = recordComplexus.getRootRecord(recordCode);
		String recordName = rootRecord.getName();
		String hostCode = recordComplexus.getHostCode();
		String hostType = recordComplexus.getHostType();
		// 定义 全局变量
		
		List<RecordRelation> recordRelationList = new ArrayList<RecordRelation>();
		List<RootRecord> rootRecordList = new ArrayList<RootRecord>();
		List<Integer> addedLabelList = new ArrayList<Integer>();
		List<Integer> removedLabelList = new ArrayList<Integer>();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		List<FuseLeafAttribute> putFuseLeafAttributeList = new ArrayList<FuseLeafAttribute>();
		List<FuseLeafAttribute> addedLeafAttrList = new ArrayList<FuseLeafAttribute>();
		Map<String, String> removedLeafAttrMap = new HashMap<String, String>();
		
		RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordName,
				recordCode);

		kSession.setGlobal("userCode", userCode);
		kSession.setGlobal("recordCode", recordCode);
		kSession.setGlobal("recordName", rootRecord.getName());
		kSession.setGlobal("recordComplexus", recordComplexus);
		kSession.setGlobal("recordRelationOpsBuilder", recordRelationOpsBuilder);
		
		try {
			kSession.setGlobal("recordRelationList", recordRelationList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			kSession.setGlobal("rootRecordList", rootRecordList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			kSession.setGlobal("hostCode", hostCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			kSession.setGlobal("hostType", hostType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			kSession.setGlobal("addedLabelList", addedLabelList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kSession.setGlobal("removedLabelList", removedLabelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			
			kSession.setGlobal("attributeList", attributeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			kSession.setGlobal("putFuseLeafAttributeList", putFuseLeafAttributeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			
			kSession.setGlobal("addedLeafAttrList", addedLeafAttrList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			kSession.setGlobal("removedLeafAttrMap", removedLeafAttrMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kSession.setGlobal("rootRecord", rootRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kSession.setGlobal("recordComplexus", recordComplexus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// insert object
		BizzAttributeTransfer.transfer(rootRecord).forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		if (relationCorrelation != null) {
			relationCorrelation.getRecordRelation().forEach(recordRelation -> kSession.insert(recordRelation));
		}
		
		if (opsComplexus != null) {
			if (opsComplexus.getRootRecordOps(recordCode) != null) {
				BizzAttributeTransfer.transfer(opsComplexus.getRootRecordOps(recordCode))
						.forEach(opsAttr -> kSession.insert(opsAttr));
			}

			if (opsComplexus.getRecordRelationOps(recordCode) != null) {
				BizzAttributeTransfer.transfer(opsComplexus.getRecordRelationOps(recordCode))
						.forEach(opsRelation -> kSession.insert(opsRelation));
			}

		}

		// 触发规则
		kSession.fireAllRules();
		kSession.destroy();

		// 组装结果
		RootRecordOpsBuilder rootRecordOpsBuilder = RootRecordOpsBuilder.getInstance(recordName, recordCode);
		rootRecordOpsBuilder.setRemoveLabel(removedLabelList);
		rootRecordOpsBuilder.setAddLabel(addedLabelList);
		rootRecordOpsBuilder.setUpdateAttribute(attributeList);
		
		// 添加多值属性

		rootRecordOpsBuilder.setAddedLeafAttribute(addedLeafAttrList);
		// 删除的多值属性
		for (String key : removedLeafAttrMap.keySet()) {
			rootRecordOpsBuilder.setRemoveLeaf(removedLeafAttrMap.get(key), key);
		}
		// 添加更新的多值属性
		rootRecordOpsBuilder.setUpdateLeafAttribute(putFuseLeafAttributeList);
		
		ImprveResult imprveResult = new ImprveResult();
		imprveResult.setRootRecordOps(rootRecordOpsBuilder.getRootRecordOps());
		imprveResult.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());
		imprveResult.setAddedRecords(rootRecordList);
		imprveResult.setAddedRecordRelation(recordRelationList);
		return imprveResult;
	}

	public static ImprveResult getImproveResultFromKIE(BizFusionContext bizFusionContext, String recordCode,
			RecordComplexus recordComplexus, KieSession kSession) {
		return getImproveResultFromKIE(bizFusionContext, recordCode, null, recordComplexus, kSession);
	}

}
