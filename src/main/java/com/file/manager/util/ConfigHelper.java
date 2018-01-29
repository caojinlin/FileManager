package com.file.manager.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 读取src/config.properties文件的单例类
 * @author wangqingyuan
 *
 */
public class ConfigHelper {
	
	private static Properties instance = null;
	
	/**
	 * 获取实例
	 * @return
	 */
	public static Properties getInstance() {
		if (instance == null) {
			String path;
			try {
				path = ConfigHelper.class.getClassLoader().getResource("config.properties").toURI().getPath();
			} catch (URISyntaxException e2) {
				path = ConfigHelper.class.getClassLoader().getResource("config.properties").getPath();
			}
			
			FileInputStream inStream = null;
			try {
				inStream = new FileInputStream(path);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			instance = new Properties();
			try {
				instance.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return instance;
	}
	
	/**
	 * 构造方法 缺省参数
	 */
	public ConfigHelper() {

	}
	
	/**
	 * 根据key获取value
	 * @return
	 */
	public static String getValue(String key){
		getInstance();
		String value = instance.getProperty(key);
		return value;
	}
	
	public static void main(String[] args) {
		Properties prop = getInstance();
//		System.out.println(prop);
	}
}