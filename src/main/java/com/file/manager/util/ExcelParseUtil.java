package com.file.manager.util;

import java.util.HashMap;
import java.util.Map;

public class ExcelParseUtil {
	public static String[] keyws = {"法律状态公告日","法律状态信息","变更事项","变更项目","登记生效日","申请日","申请公布日","发明名称","变更前权利人","<变更前>","变更前","变更后权利人","<变更后>","变更后","授权公告日","法律状态信息","IPC(主分类)"};//顺序不能改，法律状态信息在最后
	public static String[] keyEgnore = {"变更事项","变更项目","变更前权利人","<变更前>","</变更前>","变更前","变更后权利人","<变更后>","</变更后>","变更后","地址"};//去除的多余文字
	public static String[] keyPerple = {"变更前权利人","<变更前>","变更后权利人","<变更后>"};//关于权利人的猛戳
	public static String EXCEL_DATA_GGR = "GGR";
	public static String EXCEL_DATA_BGSX = "BGSX";
	public static String EXCEL_DATA_DJSXR = "DJSXR";
	public static String EXCEL_DATA_BGQQLR = "BGQQLR";
	public static String EXCEL_DATA_BGHQLR = "BGHQLR";
	
	private static Map<String,String> keyMap = new HashMap<String,String>();
	
	private static void initMap(){
		keyMap.put("法律状态公告日", EXCEL_DATA_GGR);
		keyMap.put("变更事项", EXCEL_DATA_BGSX);
		keyMap.put("变更项目", EXCEL_DATA_BGSX);
		keyMap.put("登记生效日", EXCEL_DATA_DJSXR);
		keyMap.put("变更前权利人", EXCEL_DATA_BGQQLR);
		keyMap.put("<变更前>", EXCEL_DATA_BGQQLR);
		keyMap.put("变更前", EXCEL_DATA_BGQQLR);
		keyMap.put("变更后权利人", EXCEL_DATA_BGHQLR);
		keyMap.put("<变更后>", EXCEL_DATA_BGHQLR);
		keyMap.put("变更后", EXCEL_DATA_BGHQLR);
	}
	
	public Map<String,String> getKeyMap(){
		if(keyMap.size()==0)
			initMap();
		return keyMap;
	}
	
	
	public String getKeyName(String key){
		if(keyMap.size()==0)
			initMap();
		return keyMap.get(key);
	}
}
