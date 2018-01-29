package com.file.manager.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * IP常用类
 * 
 * @author ouyang.chucai@trs.com.cn
 */
public class IPUtil {
	
	/**
	 * 获取上下文ip
	 * @param request 请求对象
	 * @return ip地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = null;
		try {
			//负载均衡服务器IP信息获取X-ClientIP
			ip = request.getHeader("X-ClientIP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("x-forwarded-for");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;

	}

	/**
	 * ip地址转成整数.
	 * 
	 * @param ip
	 * @return
	 */
	public static long ip2long(String ip) {
		String[] ips = ip.split("[.]");
		long num = Long.parseLong(ips[0]) * 256 * 256 * 256
				+ Long.parseLong(ips[1]) * 256 * 256 + Long.parseLong(ips[2])
				* 256 + Long.parseLong(ips[3]);
		return num;
	}

	/**
	 * 整数转成ip地址.
	 * 
	 * @param ipLong
	 * @return
	 */
	public static String long2ip(long ipLong) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}

	/**
	 * 判斷是否有效的ip地址
	 * 
	 * @param ip
	 *            要判斷的ip
	 * @param starEnabled
	 *            ip是否可以用*匹配
	 * @return 匹配成功返回true
	 */
	public static boolean isValidIPv4(String ip, boolean starEnabled) {
		if (ip == null || ip.length() == 0)
			return false;
		String REGX_IP = null;
		if (starEnabled) {
			REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d|\\*)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d|\\*)";
		} else {
			REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
		}
		return ip.matches(REGX_IP);
	}

	/**
	 * 判断ip是否在指定范围
	 * 
	 * @param ip
	 *            要判断的ip
	 * @param sections
	 *            ip范围，可以是ip段10.2.3.*-10.4.5.*，10.2.3.*~10.4.5.*，也可以是12.34.56.*-12.34.78.*，中间用;隔开（多个IP段之间用;分隔）
	 * @return 如果在范围内则返回true
	 */
	public static boolean isIpInSection(String ip, String sections) {
		if (ip == null || ip.length() == 0)
			return false;
		if (!isValidIPv4(ip, false))
			return false;
		if (sections == null || sections.length() == 0)
			return false;
		sections = sections.replaceAll("~", "-");

		boolean bResult = false;
		String[] ipSection = sections.split(";");
		String[] ipAddress = ip.split("\\.");
		String[] ipStart = null;
		String[] ipEnd = null;
		long lStart = 0L, lEnd = 0L, lIp = 0L;

		for (int i = 0; i < ipSection.length; i++) {
			lStart = 0L;
			lEnd = 0L;
			lIp = 0L;
			String[] ips = ipSection[i].split("-");

			// 如果只有一个ip
			if (ips.length == 1) {
				if (!isValidIPv4(ips[0], true)) {
					continue;
				}
				ipStart = ips[0].split("\\.");
				ipEnd = ips[0].split("\\.");
			} else {
				// 如果是一个ip段，如10.23.45.*-12.34.56.*
				if (ips.length == 2) {
					if (!isValidIPv4(ips[0], true)
							|| !isValidIPv4(ips[1], true)) {
						continue;
					}
					ipStart = ips[0].split("\\.");
					ipEnd = ips[1].split("\\.");
				}
			}

			// 转换ip为长整型进行判断
			for (int j = 0; j < 4; j++) {
				lIp = ("*".equals(ipAddress[j])) ? lIp << 8 : lIp << 8
						| Integer.parseInt(ipAddress[j]);
				lStart = ("*".equals(ipStart[j])) ? lStart << 8 : lStart << 8
						| Integer.parseInt(ipStart[j]);
				lEnd = ("*".equals(ipEnd[j])) ? lEnd << 8 | 255 : lEnd << 8
						| Integer.parseInt(ipEnd[j]);
			}
			if (lStart > lEnd) {
				long t = lStart;
				lStart = lEnd;
				lEnd = t;
			}
			if (lStart <= lIp && lIp <= lEnd) {
				bResult = true;
				break;
			}
		}
		return bResult;
	}
	
	/**
	 * 根据IP获取所在地区
	 * 
	 * @param ip 目标IP
	 * @param ipAddressMap 地区与IP范围对应信息，key为地区，value为IP范围
	 * @return 目标IP获取所在地区
	 */
	public static String getAreaForIP(String ip,Map<String,String> ipAddressMap){
		if(StringUtils.isEmpty(ip) || ipAddressMap==null || ipAddressMap.size()<=0){
			return "";
		}
		String desarea = "";
		for(String address:ipAddressMap.keySet()){
			String ipsetion = ipAddressMap.get(address);
			if(IPUtil.isIpInSection(ip, ipsetion)){
				desarea = address;
				break;
			}
		}
		return desarea;
	}
	
	public static void main(String args[])
	{
		System.out.println(ip2long("27.0.12.34"));
		System.out.println(ip2long("223.25.0.0"));
		System.out.println(ip2long("223.25.63.255"));
		boolean t=isIpInSection("223.25.252.56", "223.25.252.0~223.25.252.50;223.26.252.50~223.25.255.255");
		System.out.println(t);
	}
}
