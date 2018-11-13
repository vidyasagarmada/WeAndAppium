package com.dxc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * This class will restrict usage of the framework by validating license key
 * 
 * @author VidyaSagar Mada
 * 
 */
public class GatePassAuthenticator {

	/*
	 * public static String decrypt(String key1, String key2, String encrypted) {
	 * try { IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));
	 * 
	 * SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), "AES");
	 * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	 * cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv); byte[] original =
	 * cipher.doFinal(Base64.decodeBase64(encrypted));
	 * System.out.println("Decrypted code" + original); return new String(original);
	 * } catch (Exception ex) { ex.printStackTrace(); } return null; }
	 */
	/**
	 * This method is used to return Authentication Status by validating the license
	 * key provided in dataconfig.properties file with key 'license.key'
	 * 
	 * @return authenticationStatus
	 * @author Vidyasagar Mada
	 */
	public int auth() {
		try {
			if (BaseClass.props.getProperty("license.key").equals(encrypt())) {
				return 0;
			} else
				return -1;
		} catch (NullPointerException e) {
			return 1;
		}
	}

	/**
	 * This method will return encrypted string which is used for license key
	 * validation
	 * 
	 * @return encryptedString
	 * @author Vidyasagar mada
	 */
	public static String encrypt() {
		String key1 = "ThisIsASecretKey";
		try {
			IvParameterSpec iv = new IvParameterSpec("DXCAUTOMATIONCSC".getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher
					.doFinal(("FinalPadding" + new SimpleDateFormat("MMMyyyy").format(new Date())).getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}