package com.zhsq.biz.common;

import java.util.List;

import com.abc.complexus.RecordComplexus;
import com.abc.query.criteria.Criteria;
import com.abc.query.criteria.CriteriaFactory;
@Deprecated
public  class KieIdentityQuery  extends AbstractIdentityQuery{

	private String sessionName;
	
	public KieIdentityQuery(String sessionName){
		this.sessionName=sessionName;
	}

	@Override
	protected List<Criteria> bizCriteriaList(RecordComplexus complexus) {
		return KIEHelper.getBizCriteriaListFromKIE(complexus,SessionFactory.findSessionKeepContainer(sessionName));
	}

}
