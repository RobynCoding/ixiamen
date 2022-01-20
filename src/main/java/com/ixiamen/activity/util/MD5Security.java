package com.ixiamen.activity.util;

/**
 * 
 * @Author : luoyongbin
 * @CreateTime : 2020年3月3日
 * @Describtion : MD5解密实现类
 */
public class MD5Security implements PasswordSecurity {

	@Override
	public String encode(String password) {

		return MD5Utils.toMD5(password);
	}

	public static void main(String [] args){
		PasswordSecurity passwordSercurity = new MD5Security();
		String pass = passwordSercurity.encode("123456");
		System.out.println(pass);
	}

}
