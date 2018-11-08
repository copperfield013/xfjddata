package com.zhsq.biz.people;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.ErrorMessage;
import com.abc.identity.query.IdentityQuery;
import com.abc.ops.RecordCROpsPair;
import com.abc.ops.RecordCompoundOps;
import com.abc.ops.RecordRelationOps;
import com.abc.ops.complexus.RecordComplexusOps;
import com.abc.quality.check.Check;
import com.abc.quality.improve.Improvement;
import com.abc.query.criteria.Criteria;
import com.abc.record.Attribute;
import com.abc.record.RecordCompound;
import com.zhsq.biz.common.AbstractIdentityQuery;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;
import com.zhsq.biz.constant.people.PeopleItem;

@Repository(value = "XFJDE001")
public class PeopleBNB implements BizNoBusy,IdentityQuery, Improvement,IFusitionCallBack,Check{
	@Override
	public List<Criteria> getCriteriaList(RecordComplexus complexus) {
		return new AbstractIdentityQuery(){
			@Override
			protected List<Criteria> bizCriteriaList(RecordComplexus complexus) {
				return KIEHelper.getBizCriteriaListFromKIE(complexus,SessionFactory.findSessionKeepContainer("ks-people-idt-query"));
			}
			
		}.getCriteriaList(complexus);
		
	}

	@Override
	public boolean afterFusition(String code,BizFusionContext context) {
		return true;
	}


	@Override
	public List<ErrorMessage> beforeCheck(RecordComplexus arg0) {
		
		return null;
	}

	@Override
	public List<ErrorMessage> afterCheck(BizFusionContext arg0,  String recordCode, RecordComplexus arg2) {
		return 	 KIEHelper.getErrorMessageFromKIE(recordCode, arg2,SessionFactory.findSessionKeepContainer("ks-people-check"));
	}

	@Override
	public RecordCompoundOps improveFirst(RecordCompound arg0) {
		return KIEHelper.getRecordCompoundOpsFromKIE(arg0,SessionFactory.findSessionKeepContainer("ks-people-ipm"));
	}

	@Override
	public RecordRelationOps improveSecond(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		return 	 KIEHelper.getRecordRelationOpsFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-people-ripm"));
	}
	
	@Override
	public RecordCROpsPair improveThird(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		return  KIEHelper.getRecordCROpsPairFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-people-tipm"));
	}
	
	

}
