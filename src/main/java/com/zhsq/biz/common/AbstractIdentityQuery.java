package com.zhsq.biz.common;

import java.util.List;

import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.rrc.query.queryrecord.criteria.Criteria;


public abstract class AbstractIdentityQuery  implements IdentityQuery{

	public List<Criteria> getCriteriaList(RecordComplexus complexus) {
		
		RecordCodeOnlyIQ recordCodeOnlyIQ = new RecordCodeOnlyIQ();
		List<Criteria> result = recordCodeOnlyIQ.getCriteriaList(complexus);
		if (result.size() > 0) {
			return result;
		}

		List<Criteria> criteriaList = bizCriteriaList(complexus);

		if (criteriaList==null || criteriaList.size() == 0) {
			NoElementForIQ noElementForIQ = new NoElementForIQ();
			criteriaList=noElementForIQ.getCriteriaList(complexus);
		}
		return criteriaList;
	}

	protected abstract  List<Criteria> bizCriteriaList(RecordComplexus complexus);

}
