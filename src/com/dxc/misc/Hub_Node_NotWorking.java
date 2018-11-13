package com.dxc.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import com.dxc.util.Keywords;
public class Hub_Node_NotWorking {
	public static void main(String a[]) throws InterruptedException,
			IOException {
		String hubIP = "10.31.21.11";
		String nodeIP = "10.31.21.11";
		startHub();
		Keywords.wait(3);
		// registerNode(nodeAddress, nodePort, UDID, bootstrapport,
		// pathTonodeJsonFile)
		// createJson(browser, version, maxInstances, PlatformInCaps,
		// deviceName, port, nodeIP, hubIP)
		int port = 4727;
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				registerNode(
						nodeIP,
						port,
						"192.168.113.113:5555",
						"8080",
						createJson("chrome", "5.0.1", 1, "ANDROID",
								"192.168.113.113:5555", port, nodeIP, hubIP));
			} else {
				registerNode(
						nodeIP,
						port,
						"192.168.113.114:5555",
						"8082",
						createJson("chrome", "4.4.2", 1, "ANDROID",
								"192.168.113.114:5555", port, nodeIP, hubIP));
			}
			port = port + 2;
		}
		 //
		//stopHub();	
		}

	public static String createJson(String browser, String version,
			int maxInstances, String PlatformInCaps, String deviceName,
			int port, String nodeIP, String hubIP) throws IOException {
		File foldernotepad = new File(System.getProperty("user.dir") + "\\json");
		if (!foldernotepad.exists()) {
			foldernotepad.mkdir();
		}
		String jsonfile1 = System.getProperty("user.dir") + "\\json\\node"
				+ port + ".json";
		File RF = new File(jsonfile1);
		BufferedWriter wRF = new BufferedWriter(new FileWriter(RF));
		wRF.write("{\n\"capabilities\":\n[\n{\n\"browserName\": \"" + browser
				+ "\",");// need to
							// parameterize
		wRF.write("\n\"version\": \"" + version + "\",");// need to parameterize
		wRF.write("\n\"maxInstances\": " + maxInstances + ",");// need to
																// parameterize
		wRF.write("\n\"platform\": \"" + PlatformInCaps + "\",");// need to
																	// parameterize
		wRF.write("\n\"deviceName\": \"" + deviceName + "\"");// need to
																// parameterize
		wRF.write("\n}\n],\n\"configuration\":\n{\n\"nodeTimeout\":120,\n\"port\":"
				+ port + ", ");// need
								// to
								// parameterize
		wRF.write("\n\"hubPort\":4444,\n\"proxy\": \"org.openqa.grid.selenium.proxy.DefaultRemoteProxy\",\n\"url\":\"http://"
				+ nodeIP + ":" + port + "/wd/hub\",");// need
														// to
														// parameterize
		wRF.write("\n\"hub\": \"" + hubIP + ":4444/grid/register\",");// need to
																		// parameterize
		wRF.write("\n\"hubHost\":\""
				+ hubIP
				+ "\",\n\"nodePolling\":2000,\n\"registerCycle\":10000,\n\"register\":true,\n\"cleanUpCycle\":2000,\n\"timeout\":30000,\n\"maxSession\":1");
		wRF.write("\n}\n}");
		wRF.close();
		return jsonfile1;
	}

	static void startHub() throws InterruptedException {
		try {
			String cmd = "java -jar selenium-server-standalone-2.47.1.jar -role hub -hubConfig hubConfig.json";
		Process process=Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", cmd });
			 BufferedReader reader = new BufferedReader(
			            new InputStreamReader(process.getInputStream()));
			    String line=reader.readLine();
			   // while ((line = reader.readLine()) != null) {
			        System.out.println("Hub cmd "+line);
			    //}
			    reader.close();
			System.out.println("Grid Hub is started Successfully.....!!!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void registerNode(String nodeAddress, int nodePort, String UDID,
			String bootstrapport, String pathTonodeJsonFile) throws IOException {
		// node.exe "c:\Program
		// Files\Appium\node_modules\appium\bin\appium.js" --address 192.168.113.113 --port 8080 -U "192.168.113.113:5555"
		// --bootstrap-port 8081 --chromedriver-port 8082 --nodeconfig
		// node1_Config.json
		String cmd = "node.exe \"C:\\Users\\vmada2.ASIAPAC\\Desktop\\Appium\\node_modules\\appium\\bin\\appium.js\""
				+ " -a "
				+ nodeAddress
				+ " -p "
				+ nodePort
				+ " -U "
				+ "\""
				+ UDID
				+ "\""
				+ " -bp "
				+ bootstrapport
				+ " --chromedriver-port 8082 --nodeconfig "
				+ pathTonodeJsonFile;
		System.out.println("CMD = " + cmd);
		Process process =Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", cmd });
		  BufferedReader reader = new BufferedReader(
		            new InputStreamReader(process.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		    reader.close();
		System.out.println("Node is registered successfully....!!!!!!!!!!");
	}

	static void stopHub() throws IOException {
		// Command to stop hub
		Runtime.getRuntime().exec("taskkill /F /IM java.exe");
		System.out.println("Hub is stopped......");
	}
}
