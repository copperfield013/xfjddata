package com.zhsq.biz.people;

import com.abc.application.BizFusionContext;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.improve.ImprveResult;
import com.abc.fuse.improve.ops.complexus.OpsComplexus;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

public class PeopleBNBTimer extends PeopleBNB {

	
	
	@Override
	public ImprveResult preImprove(BizFusionContext context, String recordCode, OpsComplexus opsComplexus,
			RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImprveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findSessionKeepContainer("ks-people-ipm-ipmTimer"));
	} 

	@Override
	public ImprveResult postImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}
}
