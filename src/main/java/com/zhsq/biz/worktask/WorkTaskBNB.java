package com.zhsq.biz.worktask;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.improve.Improvement;
import com.abc.fuse.improve.ImprveResult;
import com.abc.fuse.improve.ops.complexus.OpsComplexus;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.common.AbstractIdentityQuery;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

//@Repository(value = "XFJDE379")
public class WorkTaskBNB implements BizNoBusy, IdentityQuery, Improvement, IFusitionCallBack {
	
	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findSessionKeepContainer("ks-worktask-idt-query"));
	
	}

	@Override
	public ImprveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, opsComplexus, recordComplexus,
				SessionFactory.findSessionKeepContainer("ks-worktask-preipm"));
	}

	@Override
	public ImprveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findSessionKeepContainer("ks-worktask-ipm"));
	} 

	@Override
	public boolean afterFusition(String recordCode, BizFusionContext context) {

		return false;
	}

	@Override
	public ImprveResult postImprove(BizFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
