package com.qfedu.ssh.utils;

import java.util.regex.Pattern;

public class PatternUtils {
	public static void main(String [] args) {
		boolean bool=Pattern.matches(uPattern, "zhangsan");
		System.out.println(bool);
	}

	//用户名正则，字母开头，6到16位
	public static String uPattern ="^[a-zA-Z]\\w{6,16}$";
	//密码强度正则，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
	public static String pPattern ="^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
	//正数正则
	public static String posPattern ="^\\d*\\.?\\d+$";
	//负数正则
	public static String negPattern ="^-\\d*\\.?\\d+$";
	//数字正则
	public static String numPattern ="^-?\\d*\\.?\\d+$";
	//Email正则
	public static String ePattern ="^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
	//手机号正则
	public static String mPattern ="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
	//身份证号（18位）正则
	public static String cP ="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
	//ipv4地址正则
	public static String ipP ="^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	//RGB Hex颜色正则
	public static String colorPattern ="^#?([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
	//日期正则，简单判定,未做月份及日期的判定
	public static String dP1 ="^\\d{4}(\\-)\\d{1,2}\\1\\d{1,2}$";
	//日期正则，复杂判定
	public static String dP2 ="^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
	//QQ号正则，5至11位
	public static String qqPattern ="^[1-9][0-9]{4,10}$";
	//微信号正则，6至20位，以字母开头，字母，数字，减号，下划线
	public static String wxPattern ="^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$";
	//车牌号正则
	public static String cPattern ="^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
	//包含中文正则
	public static String cnPattern ="[\\u4E00-\\u9FA5]";
	//个人介绍的正则
	public static String selfPattern="\\w{0,255}";
}
