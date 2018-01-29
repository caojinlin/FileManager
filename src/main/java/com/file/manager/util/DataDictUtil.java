package com.file.manager.util;

import java.util.HashMap;
import java.util.Map;

public class DataDictUtil {
	
	public static Map<String, String> findByKey(String key){
		Map<String, String> retMap = new HashMap<String, String>();
		Map<String, String> result = new HashMap<String, String>();
		try {
			retMap = ConfigUtil.loadClassPathResource("config.properties", "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String value = retMap.get(key);
		
		if (value.contains(";")) {
			String[] split = value.split(";");
			for (String str : split) {
				if (str.contains(",")) {
					String[] eles = str.split(",");
					result.put(eles[0], eles[1]);
				}
			}	
		} 
		
		return result;
	}
	
}
