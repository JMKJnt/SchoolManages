package com.cn.common;

import java.util.Map;

/**
 * 工具类
 * @author songzhili
 * 2016年11月14日下午4:19:56
 */
public class Utils {

	/**
	 * 将空对象转化成空字符串""
	 * @param object
	 * @return
	 */
	public static String nullToString(Object object){
		String result=object==null?"":object.toString();
		return result;
	}
	public static boolean isEmpty(String source){
		
		if(source == null || source.trim().length() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean areNotEmpty(String a ,String b){
		
		if(!isEmpty(a) && !isEmpty(b)){
			return true;
		}
		return false;
	}
	/****/
	public static String[] obtainCodeAndVersion(Map<String, Object> source){
		
		String[] result = {"",""};
		if(source.get("code") != null){
			result[0] = source.get("code").toString();
		}
		if(source.get("version") != null){
			result[1] = source.get("version").toString();
		}
		return result;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> transferSourceToMap(Map<String, Object> source){
		
		Object object = source.get("jsonStr");
		if(object instanceof Map){
			Map<String,Object> objectThree = (Map<String,Object>)object;
			return objectThree;
		}
		return null;
	}
}
