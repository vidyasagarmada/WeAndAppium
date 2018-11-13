package com.dxc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class has the declaration related to actions need to be performed in command prompt
 * 
 * @author Vidyasagar Mada
 *
 */
public class CmdUtil {
	/**
	 * This method will execute specified command in command prompt
	 * 
	 * @author Vidyasagar Mada
	 * @param cmd
	 */
	public static void executeCommand(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("\n"+cmd + " is executed successfully");
			reader.close();
		} catch (IOException e) {
			System.out.println("Following error occured during "+cmd +" execution");
			e.printStackTrace();
		}
	}
}