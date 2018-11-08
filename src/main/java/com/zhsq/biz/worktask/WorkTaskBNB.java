package com.zhsq.biz.worktask;

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
import com.abc.quality.check.Check;
import com.abc.quality.improve.Improvement;
import com.abc.query.criteria.Criteria;
import com.abc.record.RecordCompound;
import com.zhsq.biz.common.AbstractIdentityQuery;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

//@Repository(value = "XFJDE379")
public class WorkTaskBNB implements BizNoBusy,IdentityQuery, Improvement,IFusitionCallBack,Check{
	@Override
	public List<Criteria> getCriteriaList(RecordComplexus complexus) {
		return new AbstractIdentityQuery(){
			@Override
			protected List<Criteria> bizCriteriaList(RecordComplexus complexus) {
				return KIEHelper.getBizCriteriaListFromKIE(complexus,SessionFactory.findSessionKeepContainer("ks-worktask-idt-query"));
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
	public List<ErrorMessage> afterCheck(BizFusionContext arg0, String recordCode, RecordComplexus arg2) {
		return 	 KIEHelper.getErrorMessageFromKIE(recordCode, arg2,SessionFactory.findSessionKeepContainer("ks-worktask-check"));
	}

	@Override
	public RecordCompoundOps improveFirst(RecordCompound arg0) {
		return KIEHelper.getRecordCompoundOpsFromKIE(arg0,SessionFactory.findSessionKeepContainer("ks-worktask-ipm"));
	}

	@Override
	public RecordRelationOps improveSecond(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		return 	 KIEHelper.getRecordRelationOpsFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-worktask-ripm"));
	}

	@Override
	public RecordCROpsPair improveThird(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		return  KIEHelper.getRecordCROpsPairFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-worktask-tipm"));
	}

}
