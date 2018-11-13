package com.dxc.misc;

import org.testng.ITestResult;
import org.testng.Reporter;

public class HtmlReportService_RD {
	public static final String SCREENSHOT_DIR = "../../../";

	public static String formatFailMessage(String message) {
		return "<span style=\"color:red\">F</span> " + message;
	}

	public static String formatSuccessMessage(String message) {
		return "<span style=\"color:green\">P</span> " + message;
	}

	public void attachScreenShotLink(ITestResult tr, String file) {
		Reporter.setCurrentTestResult(tr);

		Reporter.log("<a href=\"" + file
				+ "\" target=\"_blank\">View Screenshot</a><br>");
	}

	public static void setSeleniumLog(ITestResult tr, String log) {
		String id = StringUtil_RD.createRandomString("sLog");
		Reporter.log("<a href=\"javascript:toggleElement('" + id
				+ "', 'block')\">View SeleniumLog</a>" + "\n");

		Reporter.log("<div id=\""
				+ id
				+ "\" style=\"display:none;position:absolute;overflow: auto; opacity: 0.95;filter:alpha(opacity=95);background-color:#eeeeee;height:300px;z-lineNo: 9002;border:1px outset;\">"
				+ log + "</div><br>");
	}

	public String toggle(String div) {
		String fld = "document.getElementById('" + div + "').style.display";
		return fld + "=='none'?" + fld + "='':" + fld + "='none'";
	}
}
