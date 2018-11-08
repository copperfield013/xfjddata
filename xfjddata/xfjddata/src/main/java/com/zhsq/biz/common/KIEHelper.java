package com.zhsq.biz.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import com.abc.application.BizFusionContext;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.ErrorMessage;
import com.abc.fuse.attribute.FuseAttribute;
import com.abc.fuse.attribute.FuseMultiAttribute;
import com.abc.fuse.transfer.impl.FirstAttributeTransform;
import com.abc.ops.RecordCROpsPair;
import com.abc.ops.RecordCompoundOps;
import com.abc.ops.RecordCompoundOpsBuilder;
import com.abc.ops.RecordRelationOps;
import com.abc.ops.RecordRelationOpsBuilder;
import com.abc.query.criteria.Criteria;
import com.abc.record.Attribute;
import com.abc.record.Record;
import com.abc.record.RecordCompound;
import com.abc.relation.RelationCorrelation;
import com.zhsq.biz.constant.people.PeopleItem;

public class KIEHelper {

	public static List<Criteria> getBizCriteriaListFromKIE(RecordComplexus complexus, KieSession kSession) {
		Record record = complexus.getHostRecordCompound().getRecord();
		List<Criteria> criteriaList = new ArrayList<Criteria>();

		FirstAttributeTransform firstAttributeTransform = new FirstAttributeTransform(record);
		firstAttributeTransform.transfer();
		firstAttributeTransform.getFuseAttributeList().forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		kSession.setGlobal("entityType", record.getRecordType());
		// kSession.startProcess("peopleQuery");

		kSession.fireAllRules();
		FuseAttribute fa = null;
		QueryResults results = kSession.getQueryResults("query criteria");

		for (QueryResultsRow row : results) {
			criteriaList.add((Criteria) row.get("criteria"));
		}
		kSession.destroy();
		return criteriaList;
	}

	public static RecordCompoundOps getRecordCompoundOpsFromKIE(RecordCompound recordCompound, KieSession kSession) {

		FirstAttributeTransform firstAttributeTransform = new FirstAttributeTransform(recordCompound.getRecord());
		firstAttributeTransform.transfer();
		firstAttributeTransform.getFuseAttributeList().forEach(fuseAttribute -> kSession.insert(fuseAttribute));

		List<Integer> addedLabelList = new ArrayList<Integer>();
		List<Integer> removedLabelList = new ArrayList<Integer>();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		List<FuseMultiAttribute> putFuseMultiAttributeList = new ArrayList<FuseMultiAttribute>();
		List<String> removedMultiAttrList = new ArrayList<String>();// 多值属性 name
		Map<String, String> removedMultiAttrMap = new HashMap<String, String>();

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

			kSession.setGlobal("putFuseMultiAttributeList", putFuseMultiAttributeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			kSession.setGlobal("removedMultiAttrList", removedMultiAttrList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			kSession.setGlobal("removedMultiAttrMap", removedMultiAttrMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kSession.setGlobal("recordCompound", recordCompound);
		} catch (Exception e) {
			e.printStackTrace();
		}

		kSession.fireAllRules();
		// 执行相关查询

		kSession.destroy();
		RecordCompoundOpsBuilder recordCompoundOpsBuilder = new RecordCompoundOpsBuilder();
		recordCompoundOpsBuilder.removeLabels(removedLabelList);
		recordCompoundOpsBuilder.putLabels(addedLabelList);
		recordCompoundOpsBuilder.putAttributes(attributeList);
		// 删除多值属性
		for (String fmAttr : removedMultiAttrList) {
			recordCompoundOpsBuilder.removeMultiAttribute(fmAttr);
		}

		for (String key : removedMultiAttrMap.keySet()) {
			recordCompoundOpsBuilder.removeMultiAttributeValue(removedMultiAttrMap.get(key), key);
		}

		// 添加多值属性
		for (FuseMultiAttribute fmAttr : putFuseMultiAttributeList) {
			recordCompoundOpsBuilder.putMultiAttrbuteValue(fmAttr.getKeyValue(), fmAttr);
		}

		return recordCompoundOpsBuilder.getRecordCompoundOps();
	}

	public static List<ErrorMessage> getErrorMessageFromKIE( String recordCode,RecordComplexus recordComplexus, KieSession kSession) {
		List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

		Record record = recordComplexus.getHostRecordCompound().getRecord();
		FirstAttributeTransform firstAttributeTransform = new FirstAttributeTransform(record);
		firstAttributeTransform.transfer();
		firstAttributeTransform.getFuseAttributeList().forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		if (relationCorrelation != null) {
			relationCorrelation.getRecordRelation().forEach(recordRelation -> kSession.insert(recordRelation));
		}
		
		kSession.setGlobal("entityType", record.getRecordType());
		
		try {
			kSession.setGlobal("recordComplexus", recordComplexus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		kSession.fireAllRules();

		QueryResults results = kSession.getQueryResults("query error message");

		for (QueryResultsRow row : results) {
			errorList.add((ErrorMessage) row.get("errorMessage"));
		}
		// List<DisabledError> disableErrorList=new ArrayList<DisabledError>();
		//
		// DisabledError disabledError;
		// Attribute attribute;
		// for(ErrorMessage em:errorList){
		// disabledError=new DisabledError(em.getContent(), em.getContent());
		// attribute=record.findAttribute(DisabledItem.姓名);
		// if(attribute!=null){
		// disabledError.setDisabledName(attribute.getValueStrToSql());
		// }
		// attribute=record.findAttribute(DisabledItem.身份证);
		// if(attribute!=null){
		// disabledError.setDisabledIDCard(attribute.getValueStrToSql());
		// }
		// attribute=record.findAttribute(DisabledItem.行政区域编码);
		// if(attribute!=null){
		// disabledError.setDisabledAreaCode(attribute.getValueStrToSql());
		// }
		// disableErrorList.add(disabledError );
		// }
		//
		// DisabledErrorManager.saveError(record.getCode(),disableErrorList);
		//
		// disabledCheckCallBack.afterCheck(disableErrorList);
		kSession.destroy();
		return errorList;
	}
	
	public static RecordRelationOps getRecordRelationOpsFromKIE(BizFusionContext bizFusionContext, String recordCode,
			RecordComplexus recordComplexus, KieSession kSession) {
		String userCode = bizFusionContext.getUserCode();
		String recordType = recordComplexus.getRecordCompound(recordCode).getRecordType();
		RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordCode,
				recordType);

		kSession.setGlobal("userCode", userCode);
		kSession.setGlobal("recordCode", recordCode);
		kSession.setGlobal("entityType", recordComplexus.getRecordCompound(recordCode).getRecordType());
		kSession.setGlobal("recordComplexus", recordComplexus);
		kSession.setGlobal("recordRelationOpsBuilder", recordRelationOpsBuilder);

		FirstAttributeTransform firstAttributeTransform = new FirstAttributeTransform(
				recordComplexus.getRecordCompound(recordCode).getRecord());
		firstAttributeTransform.transfer();
		firstAttributeTransform.getFuseAttributeList().forEach(fuseAttribute -> kSession.insert(fuseAttribute));
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		if (relationCorrelation != null) {
			relationCorrelation.getRecordRelation().forEach(recordRelation -> kSession.insert(recordRelation));
		}
		kSession.fireAllRules();
		
		kSession.destroy();
		return recordRelationOpsBuilder.getRecordRelationOps();
	}
	
	public static RecordCROpsPair getRecordCROpsPairFromKIE(BizFusionContext bizFusionContext, String recordCode,
			RecordComplexus recordComplexus, KieSession kSession) {
		FirstAttributeTransform firstAttributeTransform = new FirstAttributeTransform(recordComplexus.getRecordCompound(recordCode).getRecord());
		firstAttributeTransform.transfer();
		firstAttributeTransform.getFuseAttributeList().forEach(fuseAttribute -> kSession.insert(fuseAttribute));

		List<Integer> addedLabelList = new ArrayList<Integer>();
		List<Integer> removedLabelList = new ArrayList<Integer>();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		List<FuseMultiAttribute> putFuseMultiAttributeList = new ArrayList<FuseMultiAttribute>();
		List<String> removedMultiAttrList = new ArrayList<String>();// 多值属性 name
		Map<String, String> removedMultiAttrMap = new HashMap<String, String>();

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

			kSession.setGlobal("putFuseMultiAttributeList", putFuseMultiAttributeList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			kSession.setGlobal("removedMultiAttrList", removedMultiAttrList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			kSession.setGlobal("removedMultiAttrMap", removedMultiAttrMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			kSession.setGlobal("recordCompound", recordComplexus.getRecordCompound(recordCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String recordType = recordComplexus.getRecordCompound(recordCode).getRecordType();
		String hostType = recordComplexus.getHostType();
		String hostCode = recordComplexus.getHostCode();
	///////
		String userCode = bizFusionContext.getUserCode();
		RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordCode,
				recordType);

		kSession.setGlobal("userCode", userCode);
		kSession.setGlobal("recordCode", recordCode);
		kSession.setGlobal("entityType", recordType);
		kSession.setGlobal("recordComplexus", recordComplexus);
		kSession.setGlobal("recordRelationOpsBuilder", recordRelationOpsBuilder);
		
		try {
			kSession.setGlobal("hostType", hostType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			kSession.setGlobal("hostCode", hostCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		if (relationCorrelation != null) {
			relationCorrelation.getRecordRelation().forEach(recordRelation -> kSession.insert(recordRelation));
		}
		
		kSession.fireAllRules();
		// 执行相关查询

		kSession.destroy();
		RecordCompoundOpsBuilder recordCompoundOpsBuilder = new RecordCompoundOpsBuilder();
		recordCompoundOpsBuilder.removeLabels(removedLabelList);
		recordCompoundOpsBuilder.putLabels(addedLabelList);
		recordCompoundOpsBuilder.putAttributes(attributeList);
		// 删除多值属性
		for (String fmAttr : removedMultiAttrList) {
			recordCompoundOpsBuilder.removeMultiAttribute(fmAttr);
		}

		for (String key : removedMultiAttrMap.keySet()) {
			recordCompoundOpsBuilder.removeMultiAttributeValue(removedMultiAttrMap.get(key), key);
		}

		// 添加多值属性
		for (FuseMultiAttribute fmAttr : putFuseMultiAttributeList) {
			recordCompoundOpsBuilder.putMultiAttrbuteValue(fmAttr.getKeyValue(), fmAttr);
		}
		
		RecordCROpsPair recordCROpsPair = new RecordCROpsPair();
		recordCROpsPair.setRecordCompoundOps(recordCompoundOpsBuilder.getRecordCompoundOps());
		recordCROpsPair.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());
		
		return recordCROpsPair;
	}


}
