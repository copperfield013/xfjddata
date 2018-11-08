package com.zhsq.biz.common;

import java.util.Collection;

import com.abc.auth.UserComplexus;
import com.abc.auth.constant.UserItem;
import com.abc.auth.service.ServiceFactory;
import com.abc.auth.service.UserInfoService;
import com.abc.rrc.record.RootRecord;

public class AuthUtil {
	
	private static UserInfoService instance = ServiceFactory.getUserInfoService();

	public static Collection<String> getUserAuth(String userCode, Collection<String> labels) {

		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.findAuthHadTheseLabels(labels);

	}
	
	public static String getUserName(String userCode) {

		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.getUserRecordCompound().getfindAttribute(UserItem.昵称).getValueStrToShow();
	}

	public static Collection<String> getUserAuth(String userCode, String label) {

		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.findAuthHadLabel(label);

	}
	
	public static Collection<String> getUserRole(String userCode, Collection<String> labels) {
		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.findRoleHadTheseLabels(labels);

	}

	public static Collection<String> getUserRole(String userCode, String label) {
		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.findRoleHadLabel(label);
	}
	
	public static RootRecord getUserRecordCompound(String userCode) {
		UserComplexus userComplexus = instance.getUserComplexus(userCode);
		return userComplexus.getUserRecordCompound();
	}

}
