package com.zhsq.biz.timertask;

import java.util.List;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.application.FusionContext;
import com.abc.auth.constant.AuthConstant;
import com.abc.panel.Integration;
import com.abc.panel.PanelFactory;
import com.abc.query.criteria.Criteria;
import com.abc.service.RecordFactory;

public class LoadEntityToWorkMemory {
	public static void loadEntity(String entityType, List<Criteria> criteria, BizNoBusy bizNoBusy) {
		List<String> codes=RecordFactory.query(criteria);
		Integration integration=PanelFactory.getIntegration();
		BizFusionContext context = new BizFusionContext();
		context.putBizMap(entityType, bizNoBusy);
		context.setUserCode(AuthConstant.SUPERCODE);
		context.setSource(FusionContext.SOURCE_COMMON);
		if(codes!=null) {
			
			//integration.integrate(codes.get(0), context);
			for(String code :codes) {
				integration.integrate(code, context);
			}
		}
	}
	
}
