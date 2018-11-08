package com.zhsq.biz.people;

import com.abc.application.BizFusionContext;
import com.abc.complexus.RecordComplexus;
import com.abc.ops.RecordCROpsPair;
import com.abc.ops.RecordCompoundOps;
import com.abc.ops.RecordRelationOps;
import com.abc.record.RecordCompound;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

public class PeopleBNBTimer extends PeopleBNB {

	@Override
	public RecordCompoundOps improveFirst(RecordCompound arg0) {
		return KIEHelper.getRecordCompoundOpsFromKIE(arg0,SessionFactory.findSessionKeepContainer("ks-people-ipm-timer"));
	}

	@Override
	public RecordRelationOps improveSecond(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		//return 	 KIEHelper.getRecordRelationOpsFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-people-ripm"));
		return null;
	}

	@Override
	public RecordCROpsPair improveThird(BizFusionContext bizFusionContext, String recordCode, RecordComplexus recordComplexus) {
		return  KIEHelper.getRecordCROpsPairFromKIE(bizFusionContext,recordCode,recordComplexus,SessionFactory.findSessionKeepContainer("ks-people-tipm-timer"));
	}
}
