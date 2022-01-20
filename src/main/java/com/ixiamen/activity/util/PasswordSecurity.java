package com.ixiamen.activity.util;

/**
 * 密码加密解密接口
 * 
 * @author luoyongbin
 * 
 */
public interface PasswordSecurity {
	/**
	 * 加密算法
	 * 
	 * @return String
	 */
	String encode(String password);

}
