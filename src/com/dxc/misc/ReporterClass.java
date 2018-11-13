package com.dxc.misc;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.dxc.util.BaseClass;

public class ReporterClass extends org.testng.Reporter {

	public String logWithScreenshot(String s) {
		String str=System.getProperty("outputDir");		
		String reportdir="";
		if (null != str && str.length() > 0 )
		{
		    int endIndex = str.lastIndexOf("/");
		    if (endIndex != -1)  
		    {
		    	reportdir = str.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
		    }
		} 
		String screenshotdir=reportdir+"/img";
		File imgfolder = new File(screenshotdir);
		if (!imgfolder.exists()) {
			imgfolder.mkdir();
		}
		File srcFile = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.FILE);
		String filename =imgfolder+"/"+ getCurrentTestResult().getName() + createRandomString() + ".png";
		File destfile=new File(filename);
		try {
			FileUtils.copyFile(srcFile,destfile);
		} catch (IOException e) {
			System.out.println("Excp" + e);
			e.printStackTrace();
		}
		log(s +" <a href=\""+ destfile.getAbsolutePath()
				+ "\" target=\"_blank\">View Screenshot</a><br>");
		return null;
	}

	public static String createRandomString() {
		Random r = new Random();

		String token = Long.toString(Math.abs(r.nextLong()), 36);

		return token;
	}
}
