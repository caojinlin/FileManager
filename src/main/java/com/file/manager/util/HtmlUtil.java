/**
 * Title:		TRS SMAS
 * Copyright:	Copyright(c) 2011,TRS. All rights reserved.
 * Company:		北京拓尔思信息技术股份有限公司(www.trs.com.cn)
 */
package com.file.manager.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 工具类
 * 
 * @author 北京拓尔思信息技术股份有限公司
 * @since zhangchengbing @ 2012-2-7
 */
public class HtmlUtil {

	public static final Pattern shortURLPattern = Pattern.compile("http://t.cn/[a-zA-Z0-9]{0,7}");
	
	public static String htmlEncode(String str) {
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace(" ", "&nbsp;");
		str = str.replace("\"", "&quot;");
		str = str.replace("\'", "&#39;");
		str = str.replace("\n", "<br/> ");
		return str;
	}

	public static String htmlDiscode(String str) {
		str = str.replace("&gt;", ">");
		str = str.replace("&lt;", "<");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&quot;", "\"");
		str = str.replace("&#39;", "\'");
		str = str.replace("<br/> ", "\n");
		return str;
	}

	public static String dealHtml(String str) {
		//str = dealHtmlHead(str);
		str = dealHtmlExcFont(str);
		str = str.replaceAll("\\<(font|i|u|b|h[1-9]|s)[^>]*>|<\\/(font|i|u|b|h[1-9]|s)>", "");
		return str;
	}
	
	public static String dealHtmlExcFont(String str) {
		str = str.replaceAll("\\<(img)[^>]*>|<\\/(img)>", "");
		str = str.replaceAll("\\<(table|tbody|tr|td|th)[^>]*>|<\\/(table|tbody|tr|td|th)>", "");
		str = str.replaceAll("\\<(div|blockquote|fieldset|legend|p)[^>]*>|<\\/(div|blockquote|fieldset|legend|p)>", "");
		str = str.replaceAll("\\<(style|strong)[^>]*>|<\\/(style|strong)>", "");
		str = str.replaceAll("\\<a[^>]*>|<\\/a>", "");
		str = str.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>", "");
		str = str.replaceAll("\\<a[^>]*", "");
		return str;
	}
	
	public static String dealHtmlBasic(String str) {
		str = str.replaceAll("\\<(img)[^>]*>|<\\/(img)>", "");
		str = str.replaceAll("\\<(table|tbody|tr|td|th)[^>]*>|<\\/(table|tbody|tr|td|th)>", "");
		str = str.replaceAll("\\<(div|blockquote|fieldset|legend)[^>]*>|<\\/(div|blockquote|fieldset|legend)>", "");
		str = str.replaceAll("\\<(style)[^>]*>|<\\/(style)>", "");
		str = str.replaceAll("\\<a[^>]*>|<\\/a>", "");
		str = str.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>", "");
		str = str.replaceAll("\\<a[^>]*", "");
		return str;
	}
	
	public static String dealHtmlHead(String str) {
		str = str.replaceAll("\\<html[^>]+>", "");
		str = str.replaceAll("<head[^>]*?>[\\s\\S]*?<\\/head>", "");
		str = str.replaceAll("\\<\\/[^>]+>", "");
		
		return str;
	}
	
	public static String dealHtmlExcImg(String str) {
		str = str.replaceAll("\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>", "");
		str = str.replaceAll("\\<(table|tbody|tr|td|th)[^>]*>|<\\/(table|tbody|tr|td|th)>", "");
		str = str.replaceAll("\\<(div|blockquote|fieldset|legend|p|P)[^>]*>|<\\/(div|blockquote|fieldset|legend|p|P)>", "");
		str = str.replaceAll("\\<(style|strong)[^>]*>|<\\/(style|strong)>", "");
		str = str.replaceAll("\\<a[^>]*>|<\\/a>", "");
		str = str.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>", "");
		str = str.replaceAll("\\<a[^>]*", "");
		return str;
	}
	
	public static String dealHtmlLable(String str) {
		String regEx_head = "<head[^>]*?>[\\s\\S]*?<\\/head>"; // 定义head的正则表达式
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
	    String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
	    String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	    //String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	    str = str.replaceAll(regEx_head, "");
	    str = str.replaceAll(regEx_script, "");
	    str = str.replaceAll(regEx_style, "");
	    str = str.replaceAll(regEx_html, "");
	    
		return str;
	}
	
	public static String dealAbstract(String str) {
		str = str.replaceAll("\\<(TABLE|IMG|IMAGE)[^>]*>", "");
		return str;
	}
	
	public static String dealBadHtml(String str){
		str = str.replaceAll(".*>\\s", "");
		str = str.replaceAll("<[^/f].*", "");
		return str;
	}
	
	public static boolean judgeAbstract(String str) {
		return str.matches("\\<(TABLE|IMG)[^>]*>");
	}

	public static String dealBriefContent(String str) {
		str = str.replaceAll("\\<(TABLE|IMG|IMAGE)[^>]*>", "");
		return str;
	}

	public static String dealUrlTitle(String str) {
		str = str.replaceAll("\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>", "");
		str = htmlEncode(str);
		return str;
	}
	
	public static String replaceBlank(String s) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(s);
		String after = m.replaceAll("");
		return after;
	}
	
	public static String dealBreadcrumbRubbish(String s) {
		String after = replaceBlank(s).replace(">", "").replace("＞", "")
				.replace("（", "(").replace("）", ")");
		return after;
	}

	public static String dealSearchString(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		} 
		str = replaceBlank(str)
				.replaceAll(
						"\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>",
						"");
		return str;
	}

	
	
	public static String parseShortURL(String content){
		Matcher match = shortURLPattern.matcher(content);
		if(match.find()){
			return match.group();
		}
		return "";
	}

	public static Map<String, String> getURISpecialCharacterReplaceMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(".", "-");
		return map;
	}

	public static String getURISpecialCharacterOriginValue(String value) {
		Map<String, String> map = getURISpecialCharacterReplaceMap();
		for (Map.Entry<String, String> m : map.entrySet()) {
			value = value.replace(m.getValue(), m.getKey());
		}
		return value;
	}
	
	public static void transDocumentsSID(List<Map<String, Object>> documents, String sidKey){
		for(Map<String, Object> document : documents){
			if(document.containsKey(sidKey)){
				document.put(sidKey, ((String)document.get(sidKey)).replace(".", getURISpecialCharacterReplaceMap().get(".")));
			}
		}
	}
	
	public static String handleInvalidText(String content) {
		content = content.replaceAll("\\<(font|i|u|h[1-9]|s)[^>]*>|<\\/(font|i|u|h[1-9]|s)>", "");
		content = content.replaceAll("\\.*http:[^\\u4E00-\\u9FFF]+\\.*", "");
		content = content.replaceAll("（.*?）", "");
		content = content.replaceAll("\\(.*?\\)", "");
		content = content.replaceAll("@.{0,10}?[：|:|\\s]", "");
		return content;
	}
	
	public static String handSid(String sid) {
		if (StringUtils.isEmpty(sid)) {
			return sid;
		}
		sid = sid.replace(".", "_");
		return sid;
	}
	
	public static String htmlShowSize(String str, int size){
		if(str != null && !str.equals("") && str.length() >= size){
			return str.substring(0, size)+"...";
		}
		return str;
	}
}
