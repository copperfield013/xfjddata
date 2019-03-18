package  com.zhsq.biz.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.application.BizFusionContext;
import com.abc.application.BizNoBusy;
import com.abc.callback.IFusitionCallBack;
import com.abc.complexus.RecordComplexus;
import com.abc.fuse.identity.query.IdentityQuery;
import com.abc.fuse.improve.Improvement;
import com.abc.fuse.improve.ImproveResult;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.zhsq.biz.common.KIEHelper;
import com.zhsq.biz.common.SessionFactory;

@Repository(value = "ABCBE002")
public class UserBNB implements BizNoBusy, IdentityQuery, Improvement, IFusitionCallBack {

	@Override
	public boolean afterFusition(String code, BizFusionContext context) {

		// 给没有基本权限的用户增加基本权限
		// 1.查询是否以及有基本权限了

		// RelationCriteriaFactory.getInstance().addLeftCode(code).addRightCode(AuthConstant.CODE_AUTH_BASIC).addRTypeCode(arg0);

		// 2.若没有，增加基本权限

		return true;
	}

	@Override
	public ImproveResult improve(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return KIEHelper.getImproveResultFromKIE(context, recordCode, recordComplexus,
				SessionFactory.findSessionKeepContainer("ks-user-ipm"));
	}
	
	@Override
	public ImproveResult secondImprove(BizFusionContext context, String recordCode, RecordComplexus recordComplexus) {
		return null;
	}

	@Override
	public ImproveResult postImprove(BizFusionContext arg0, String arg1, RecordComplexus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImproveResult preImprove(BizFusionContext arg0, String arg1, OpsComplexus arg2, RecordComplexus arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Criteria> getCriteriaList(String recordCode, RecordComplexus complexus) {

		return KIEHelper.getBizCriteriaListFromKIE(recordCode, complexus,
				SessionFactory.findSessionKeepContainer("ks-user-idt-query"));

	}

}
