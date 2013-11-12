package com.deppon.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该类的功能是对密码进行MD5算法摘要
 * @author Administrator：赵本兵
 * @创建时间：2011-7-29
 */
public class MD5Util {
	public static final int LENGTH = 16;
	/**
	 * 该方法将指定的字符串用MD5算法加密后返回。
	 * @param str
	 * @return
	 */
	public static  String getMD5Encoding(String str){
		byte[] input = str.getBytes();
		String output = null;
		//声明16进制字母
		char[] hexChar={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 	
		try {
			//MD5摘要算法
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			//MD5算法的结果是128位一个整数,在这里javaAPI已经把结果转换成字节数组了 
			byte[] temp = md.digest();
			char[] ch = new char[32];
			byte b = 0;
			for (int i = 0; i < LENGTH; i++) {
				b = temp[i];
				//取每一个字节的低四位换成16进制字母 
				ch[2*i] = hexChar[b>>>4&0xf];
				//取每一个字节的高四位换成16进制字母 
				ch[2*i+1] = hexChar[b&0xf];
			}
			output = new String(ch);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return output;
	}
	public static  String convertToChar(String str){
		byte[] input = str.getBytes();
		String output = null;
		//声明16进制字母
		char[] hexChar={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 	
		try {
			//MD5摘要算法
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			//MD5算法的结果是128位一个整数,在这里javaAPI已经把结果转换成字节数组了 
			byte[] temp = md.digest();
			char[] ch = new char[32];
			byte b = 0;
			for (int i = 0; i < LENGTH; i++) {
				b = temp[i];
				//取每一个字节的低四位换成16进制字母 
				ch[2*i] = hexChar[b>>>4&0xf];
				//取每一个字节的高四位换成16进制字母 
				ch[2*i+1] = hexChar[b&0xf];
			}
			output = new String(ch);
			output = output.substring(0, output.length()/2);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return output;
	}
}
