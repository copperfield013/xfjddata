package com.zhsq.biz.common;

import com.abc.fuse.attribute.FuseAttribute;
import com.abc.god.impl.GodCriteria;
import com.abc.query.criteria.Criteria;

public class CriteriaFactory {


	
	public static Criteria createGodCriteria(FuseAttribute fuseAttribute,String entityType) {
		
			return new GodCriteria(fuseAttribute,entityType);
	
	}



}
