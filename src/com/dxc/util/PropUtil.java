package com.dxc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * This class is used to read/write data from/to property file
 * 
 * @author Vidyasagar mada
 *
 */
public class PropUtil {

	/**
	 * This static block will load the property file
	 * 
	 * @param args
	 * @author Vidyasagar Mada
	 */
	public static Properties props;
	static {
		try {
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "/resources/dataconfig.properties");
			props = new Properties();
			props.load(fs);
		} catch (Exception e) {
			System.out.println("Unable to load property file --> dataconfig.properties.");
		}
	}

	/**
	 * This method will update property file with key and value pair
	 * 
	 * @param key
	 * @param value
	 * @author Vidyasagar Mada
	 */
	public static void writeDyanamicData(String key, String value) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(System.getProperty("user.dir") + "/resources/dataconfig.properties");
			props.setProperty(key, value);
			props.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method will update property file with set of key and value pairs
	 * 
	 * @param key
	 * @param value
	 * @author Vidyasagar Mada
	 */
	public static void writeDyanamicData(String key[], String value[]) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(System.getProperty("user.dir") + "/resources/dataconfig.properties");
			for (int i = 0; i < key.length; i++)
				props.setProperty(key[i], value[i]);
			props.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
