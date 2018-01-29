package com.file.manager.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
	
    /** 
     * 使用 Map按value进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();  
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());  
        Collections.sort(entryList, new MapValueComparator());  
        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();  
        Map.Entry<String, Integer> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        
        return sortedMap;  
    } 
}
