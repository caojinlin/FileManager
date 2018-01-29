package com.file.manager.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapValueComparator implements Comparator<Entry<String, Integer>> {
	public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {  
		return -(me1.getValue().compareTo(me2.getValue()));  
	}  
}