package com.file.manager.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * md5加密方法
 * @author Administrator
 *
 */
public class MD5Utils {
	public static String md5(String plainText) {
		if(plainText==null){
			return null;
		}
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("加密失败");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
	public static void main(String[] args) {
		String str = "bkyadmin";
		System.err.println(md5(str));
	}
}
