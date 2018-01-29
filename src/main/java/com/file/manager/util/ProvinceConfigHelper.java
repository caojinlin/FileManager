package com.file.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 读取src/config.properties文件的单例类
 * 
 * @author wangqingyuan
 * 
 */
public class ProvinceConfigHelper {

	private static Properties instance = null;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static Properties getInstance() {
		if (instance == null) {

			instance = new Properties();
			try {
				instance.load(new InputStreamReader(ProvinceConfigHelper.class
						.getClassLoader().getResourceAsStream(
								"province.properties"), "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return instance;
	}

	/**
	 * 构造方法 缺省参数
	 */
	public ProvinceConfigHelper() {

	}

	/**
	 * 将properties属性文件转换成list类型数据
	 * 
	 * @param fileName
	 *            properties属性文件名
	 * @return List集合
	 */
	public static List<Entry<Object, Object>> propToList(String fileName) {
		getInstance();
		Iterator<Entry<Object, Object>> it = instance.entrySet().iterator();
		List<Entry<Object, Object>> list = new ArrayList<Entry<Object, Object>>();
		while (it.hasNext()) {
			Entry<Object, Object> entry = (Entry<Object, Object>) it.next();
			list.add(entry);
		}
		return list;
	}

	public static void main(String[] args) {
		getInstance();
		Iterator itr = instance.entrySet().iterator();
		while (itr.hasNext()) {
			Entry e = (Entry) itr.next();
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}
}