package cn.jiuling.vehicleinfosys2.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author Administrator
 *
 */
public class MD5Utils {
	
	public String encrypt(String string) {
		MessageDigest md5 = null;
		byte[] cipherData = null;
		StringBuilder builder = new StringBuilder();
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			cipherData = md5.digest(string.getBytes("utf-8"));
			
			for (byte cipher : cipherData) {
				String toHexStr = Integer.toHexString(cipher & 0xff);
				builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
        }
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
	    MD5Utils md5 = new MD5Utils();
	    md5.encrypt("123456");
    }
}
