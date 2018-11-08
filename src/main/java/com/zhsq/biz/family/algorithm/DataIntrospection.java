package com.zhsq.biz.family.algorithm;

import java.util.Collection;

import com.abc.complexus.RecordComplexus;
import com.abc.record.RecordCompound;
import com.abc.relation.RecordRelation;
import com.abc.relation.RelationCorrelation;
import com.zhsq.biz.constant.RelationType;

public class DataIntrospection {
	
	
	/**
	 * 获取家庭中:家庭人口的数量
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Integer getFamilyPeoCount(RecordComplexus recordComplexus, String recordCode) {
			Integer count = 0;
			Collection<RecordRelation> recordRelation = getRecordRelation(recordComplexus, recordCode);
			for (RecordRelation recordRelation2 : recordRelation) {
					count++;
			}
		return count;
	}
	
	/**
	 * 获取家庭中户主的数量
	 * @param recordComplexus
	 * @param recordCode
	 * @return
	 */
	public static Integer getHzCount(RecordComplexus recordComplexus, String recordCode) {
			Integer count = 0;
			Collection<RecordRelation> recordRelation = getRecordRelation(recordComplexus, recordCode);
			for (RecordRelation recordRelation2 : recordRelation) {
				if (RelationType.RR_家庭信息_户主_人口信息.equals(recordRelation2.getType())) {
					count++;
				}
			
		}
		
		return count;
	}
	
	private static Collection<RecordRelation> getRecordRelation(RecordComplexus recordComplexus, String recordCode) {
		RecordCompound recordCompound=recordComplexus.getHostRecordCompound();
		RelationCorrelation relationCorrelation = recordComplexus.getRelationCorrelation(recordCode);
		if (relationCorrelation != null) {
			return relationCorrelation.getRecordRelation();
		}
		
		return null;
	}
	
}
