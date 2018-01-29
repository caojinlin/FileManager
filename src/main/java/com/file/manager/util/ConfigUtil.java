package com.file.manager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 读取配置文件的工具类 根本上解决了PropertyResourceBundle只能支援ISO-8859-1的问题，增加了对其他编码的支援
 * 配置文件若非UTF-8编码，需要明确指定。
 * 
 * @author lin.yan@trs.com.cn
 * 
 */
public class ConfigUtil {

	/**
	 * 加载类路径下的配置文件,存储至Map中
	 * 
	 * 配置文件的每一行形如：key=value
	 * 
	 * @param path
	 *            属性文件名称
	 * @param fileCharset
	 *            属性文件的编码
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> loadClassPathResource(String fileName,
			String characterEncoding) throws Exception {
		Hashtable<String, String> hashtableConfig = null;
		if (StringUtils.isEmpty(characterEncoding)) {
			characterEncoding = "UTF-8";
		}
		BufferedReader bufferReader = null;
		try {

			bufferReader = new BufferedReader(new InputStreamReader(
					ConfigUtil.class.getClassLoader().getResourceAsStream(
							fileName), characterEncoding), 8 * 1024);
			PropertyResourceBundle prb = new PropertyResourceBundle(
					bufferReader);
			// 注意此处PropertyResourceBundle.keySet()或PropertyResourceBundle.getKeys()
			// 返回Set或Enumeration会自动排重
			Set<String> keys = prb.keySet();

			hashtableConfig = new Hashtable<String, String>();
			for (String key : keys) {
				key = StringUtils.trimToEmpty(key);
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				String value = StringUtils.trimToEmpty(prb.getString(key));
				hashtableConfig.put(key, value);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
				}
				bufferReader = null;
			}
		}
		return hashtableConfig;
	}

	/**
	 * 加载类路径下的配置文件,存储至Map中
	 * 
	 * 配置文件的每一行形如：key=value
	 * 
	 * 将key存放至Map的value中 将value存放至Map的key中
	 * 
	 * @param path
	 *            属性文件名称
	 * @param fileCharset
	 *            属性文件的编码
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> loadClassPathResourceReverse(
			String fileName, String characterEncoding) throws Exception {
		Hashtable<String, String> hashtableConfig = null;
		if (StringUtils.isEmpty(characterEncoding)) {
			characterEncoding = "UTF-8";
		}
		BufferedReader bufferReader = null;
		try {

			bufferReader = new BufferedReader(new InputStreamReader(
					ConfigUtil.class.getClassLoader().getResourceAsStream(
							fileName), characterEncoding), 8 * 1024);
			PropertyResourceBundle prb = new PropertyResourceBundle(
					bufferReader);
			// 注意此处PropertyResourceBundle.keySet()或PropertyResourceBundle.getKeys()
			// 返回Set或Enumeration会自动排重
			Set<String> keys = prb.keySet();

			hashtableConfig = new Hashtable<String, String>();
			for (String key : keys) {
				key = StringUtils.trimToEmpty(key);
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				String value = StringUtils.trimToEmpty(prb.getString(key));
				hashtableConfig.put(value, key);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
				}
				bufferReader = null;
			}
		}
		return hashtableConfig;
	}

	/**
	 * 加载指定文件路径下的配置文件,存储至Map中
	 * 
	 * 配置文件的每一行形如：key=value
	 * 
	 * @param path
	 *            属性文件绝对路径
	 * @param fileCharset
	 *            属性文件的编码
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> loadFilePathResource(String pathName,
			String characterEncoding) throws Exception {
		Hashtable<String, String> hashtableConfig = null;
		if (StringUtils.isEmpty(characterEncoding)) {
			characterEncoding = "UTF-8";
		}
		File configFile = new File(pathName);
		if (!configFile.exists() || configFile.isDirectory()) {
			throw new Exception("根据属性文件绝对路径查找不到属性文件!");
		}
		BufferedReader bufferReader = null;
		try {

			bufferReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(configFile), characterEncoding),
					8 * 1024);
			PropertyResourceBundle prb = new PropertyResourceBundle(
					bufferReader);
			// 注意此处PropertyResourceBundle.keySet()或PropertyResourceBundle.getKeys()
			// 返回Set或Enumeration会自动排重
			Set<String> keys = prb.keySet();

			hashtableConfig = new Hashtable<String, String>();
			for (String key : keys) {
				key = StringUtils.trimToEmpty(key);
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				String value = StringUtils.trimToEmpty(prb.getString(key));
				hashtableConfig.put(key, value);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
				}
				bufferReader = null;
			}
		}
		return hashtableConfig;
	}

	/**
	 * 加载类路径下的配置文件信息,存储至Set中
	 * 
	 * 配置文件的每一行形如： property
	 * 
	 * @param path
	 *            属性文件名称
	 * @param fileCharset
	 *            属性文件的编码
	 * @return
	 * @throws Exception
	 */
	public static Set<String> loadClassPathInfo(String fileName,
			String characterEncoding) throws Exception {
		Set<String> configSet = new HashSet<String>();
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new InputStreamReader(ConfigUtil.class
					.getClassLoader().getResourceAsStream(fileName),
					characterEncoding), 8 * 1024);
			String readLine = null;
			while ((readLine = bReader.readLine()) != null) {
				readLine = readLine.trim();
				if (configSet.contains(readLine)) {
					// System.out.println("已经存在的配置项名称 : "+readLine);
				}
				configSet.add(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
				}
				bReader = null;
			}
		}
		return configSet;
	}

	/**
	 * 加载指定文件路径下的配置文件信息,存储至Set中
	 * 
	 * 配置文件的每一行形如： property
	 * 
	 * @param path
	 *            属性文件绝对路径
	 * @param fileCharset
	 *            属性文件的编码
	 * @return
	 * @throws Exception
	 */
	public static Set<String> loadFilePathInfo(String pathName,
			String characterEncoding) throws Exception {
		Set<String> configSet = new HashSet<String>();
		File configFile = new File(pathName);
		if (!configFile.exists() || configFile.isDirectory()) {
			throw new Exception("根据属性文件绝对路径查找不到属性文件!");
		}
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(configFile), characterEncoding),
					8 * 1024);
			String readLine = null;
			while ((readLine = bReader.readLine()) != null) {
				readLine = readLine.trim();
				if (configSet.contains(readLine)) {
					// System.out.println("已经存在的配置项名称 : "+readLine);
				}
				configSet.add(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
				}
				bReader = null;
			}
		}
		return configSet;
	}

}
