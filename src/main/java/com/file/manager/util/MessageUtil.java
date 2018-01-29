package com.file.manager.util;

import java.util.UUID;

/**
 * 
 * @author wangqingyuan
 *
 */
public class MessageUtil {
	
	/**
	 * 生成36位UUID,格式:43e3f105-acc1-4b29-b787-a342e65aa9bc
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成32位UUID,去掉中间的"-"
	 * @return
	 */
	public static String getShortUUID() {
		return getUUID().replaceAll("-", "");
	}
}
