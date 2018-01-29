package com.file.manager.util;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

public class CommonUtil {

	/**
	 * 抽取具体地址信息
	 * @param information
	 * @return
	 */
	public static String extrctProvince(String information) {
		String[] provinceArr = { "北京", "上海", "天津", "重庆", "黑龙江", "吉林", "辽宁", "江苏",
				"山东", "安徽", "河北", "河南", "湖北", "湖南", "江西", "陕西", "山西", "四川",
				"青海", "海南", "广东", "贵州", "浙江", "福建", "台湾", "甘肃", "云南", "内蒙古",
				"宁夏", "新疆", "西藏", "广西", "香港", "澳门" , "美国", "英国", "德国", "日本", "韩国",
				"瑞士", "瑞典", "荷兰", "芬兰", "法国", "新加坡", "加拿大", "澳大利亚", "南非"};
		for (String prov : provinceArr) {
			if (information.contains(prov)) {
				return prov;
			}
		}
		
		//出了循环还未return结果,说明不在该map里面,需要从province.properties文件中精细匹配
		ProvinceConfigHelper provinceHelper = new ProvinceConfigHelper();
		Properties prop = provinceHelper.getInstance();
		Iterator itr = prop.entrySet().iterator();
		while (itr.hasNext()) {
			Entry e = (Entry) itr.next();
			//根据key取得value,遍历value
			String cities = (String) e.getValue();
			String [] cityArr = cities.split(",");
			for (String city : cityArr) {
				if (information.contains(city)) {
					return (String) e.getKey();
				}
			}
		}
		
		return information;
	}
}
