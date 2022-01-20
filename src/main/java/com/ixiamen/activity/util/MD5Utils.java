package com.ixiamen.activity.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @title: MD5Util
 * @description: 加密码工具类
 * @author: luoyongbin
 */
public final class MD5Utils
{
	/**
	 * 用于注册与登录
	 * @param s 需要加密的字符串
	 * @return 返回加密后的字符串
	 */
	public static String toMD5(String s)
	{
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		s = s + "dfdn";		//东方大脑key
		
		try
		{
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static String generateUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}

}
