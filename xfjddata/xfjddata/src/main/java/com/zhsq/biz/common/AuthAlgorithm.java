package com.zhsq.biz.common;

import java.util.HashMap;
import java.util.Map;

import com.zhsq.biz.constant.EnumKeyValue;

public class AuthAlgorithm {
	
	public static String getAreaAuthCode(Integer areaCode) {
		if(areaCode==null){
			return noAreaAuth;
		}
		String authcode= areaMappingAuth.get(areaCode);
		if(authcode==null) {
			return noAreaAuth;
		}else {
			return authcode;
		}
	}
	
	public static String getNoAreaAuthCode( ) {
		return noAreaAuth;
	}
	
	public static Map<Integer,String> areaMappingAuth=new HashMap<Integer,String>();
	public static String noAreaAuth="91ff74535cc741f6ae1a39363c643f8f";//没有正确社区的数据访问权限
	
	static{
		areaMappingAuth.put(EnumKeyValue.ENUM_祥符街道社区_勤丰社区, "f23b9f4eb14a409fb78498dc4457c190");
		areaMappingAuth.put(EnumKeyValue.ENUM_祥符街道社区_祥符桥社区, "27e14645b42341aea6fc2727e34855b4");
		areaMappingAuth.put(EnumKeyValue.ENUM_祥符街道社区_新文社区, "209080a4f6b6403eb4f735e4e5287512");
	}


}
