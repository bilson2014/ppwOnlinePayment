package com.panfeng.util;

import java.util.Collection;
import java.util.Map;

public class ValidateUtil {
	
	public static boolean isValid(final String str){
		
		if(str !=null && !"".equals(str)){
			
			return true;
		}
		return false;
	}
	
	public static boolean isValid(final String[] str){
		
		if(str != null && str.length > 0){
			
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isValid(final Collection col){
		
		if(col == null || col.isEmpty()){
			return false;
		}
		
		return true;
	}
	
	public static boolean isValid(final long[] ids) {
		 
		if(ids != null && ids.length > 0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isValid(Map map) {
		
		if(map != null && !map.isEmpty()){
			return true;
		}
		return false;
	}
	
}
