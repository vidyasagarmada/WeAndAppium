package com.dxc.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.Reporter;

import com.dxc.setup.SetUpTearDown;

/**
 * This is the base class for generating customized reports
 * 
 * @author Vidyasagar Mada
 * 
 */
public class CustomReporter extends BaseClass {

	public static String NotePadResultFile;
	public static String NotePadResultFile1;
	public static String NotepadStepsFile;
	public static String NotepadLogFile;
	public static String HTMLResultFile;
	public static String ScreenShotFile;
	public static String printscreen;
	public static int stepCount = 1;
	protected static int LogCount = 1;
	public static String ScriptStartTime = Calendar.getInstance().getTime().toString();
	public static String ScriptEndTime;
	public static String ScriptexecTime;
	public static String FinalNotePadName;
	public static String FinalHTMLName;
	public static String FinalFolderpath;
	public String className = this.getClass().getSimpleName().toString().toUpperCase();

	/**
	 * This constructor will create HTML and notepad report templates
	 * 
	 * @param FileName
	 * @param vDateForReports
	 * @author Vidyasagar Mada
	 */
	public CustomReporter(String FileName, String vDateForReports) {
		ScreenShotFile = SetUpTearDown.scriptName.toUpperCase();
		try {
			createHTMLResultTemplate(FileName, vDateForReports);
			createNotepadResultTemplate(FileName, vDateForReports);
		} catch (Exception e) {
			System.out.println("ERROR OCCURED IN CLASS - " + className);
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " CONSTRUTOR Error Caused By - " + e.getMessage());
		}
	}

	/**
	 * This method will create HTML result template
	 * 
	 * @param fileName
	 * @param vGetDateForHTML
	 * @throws IOException
	 * @author Vidyasagar Mada
	 */
	public void createHTMLResultTemplate(String fileName, String vGetDateForHTML) throws IOException {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String computername = InetAddress.getLocalHost().getHostName();

		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			File folder = new File(System.getProperty("user.dir") + "/Reports/");
			if (!folder.exists()) {
				folder.mkdir();
			}
		} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			File folder = new File(System.getProperty("user.dir") + "/Reports/");
			if (!folder.exists()) {
				folder.mkdir();
			}
		}

		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			File folder = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/");
			if (!folder.exists()) {
				folder.mkdir();
			}
		} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			File folder = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/");
			if (!folder.exists()) {
				folder.mkdir();
			}
		}

		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			File folderhtml = new File(
					System.getProperty("user.dir") + "/Reports/" + filenameappender + "/HTMLReport/");
			if (!folderhtml.exists()) {
				folderhtml.mkdir();
			}
		} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			File folderhtml = new File(
					System.getProperty("user.dir") + "/Reports/" + filenameappender + "/HTMLReport/");
			if (!folderhtml.exists()) {
				folderhtml.mkdir();
			}
		}
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			File ScreenShotFolder = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/");
			if (!ScreenShotFolder.exists()) {
				ScreenShotFolder.mkdir();
			}
		} else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			File ScreenShotFolder = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/");
			if (!ScreenShotFolder.exists()) {
				ScreenShotFolder.mkdir();
			}
		}
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			HTMLResultFile = System.getProperty("user.dir") + "/Reports/" + filenameappender + "/HTMLReport/"
					+ fileName.toUpperCase() + "_" + vGetDateForHTML + ".html";
		else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
			HTMLResultFile = System.getProperty("user.dir") + "/Reports/" + filenameappender + "/HTMLReport/"
					+ fileName.toUpperCase() + "_" + vGetDateForHTML + ".html";
		}
		File fHTML = new File(HTMLResultFile);
		BufferedWriter wHTML = new BufferedWriter(new FileWriter(fHTML));
		if (fHTML.exists()) {
			fHTML.delete();
		}
		GatePassAuthenticator gpa = new GatePassAuthenticator();
		wHTML.write("<!DOCTYPE html><html><head><style>"
				+ "div{font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:800px;padding:10px;background-color:#EAF2D3;border:5px solid #98bf21;margin: auto;font-size:20px;text-align:justify;line-height:40%;}"
				+ "#Log {font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:100%;border-collapse:collapse;}"
				+ "#Log th {font-size:1.1em;border:1px solid #98bf21;font-weight:bold;text-align:center;padding-top:5px;padding-bottom:4px;background-color:#A7C942;color:#ffffff;}"
				+ "</style><title>" + props.getProperty("report.title") + "</title></head>"
				+ "<body><div align=\"center\">" + "<pre>Test Script Name 	:	" + fileName.toUpperCase() + "</pre>"
				+ "<pre>Test Environment	: 	</pre>" + "<pre>Test Executed By	:	"
				+ (System.getProperty("user.name")).toUpperCase() + "</pre>" + "<pre>Java Version		: 	"
				+ (System.getProperty("java.version")).toUpperCase() + "</pre>" + "<pre>OS Name / Version	: 	"
				+ (System.getProperty("os.name")).toUpperCase() + " / "
				+ (System.getProperty("os.version")).toUpperCase() + "</pre>" + "<pre>Computer Name		: 	"
				+ computername.toUpperCase() + "</pre>");
		int authenticationResult = gpa.auth();
		if (authenticationResult == 0)
			wHTML.write("<pre>Authentication		: <b><font color=\"green\">	SUCCESS</font></b>");
		else if (authenticationResult == 1)
			wHTML.write(
					"<pre>Authentication		: <b><font color=\"red\">	FAIL - License Key not found</font></b>");
		else if (authenticationResult == -1)
			wHTML.write(
					"<pre>Authentication		: <b><font color=\"red\">	FAIL - License Expired/Mismatched</font></b>");
		if (authenticationResult != 0) {
			wHTML.close();
			System.out.println("License Authentication Error occured. \nTerminating execution.....");
			System.exit(0);
		}
		wHTML.write("<pre>Test Start Time		: 	" + ScriptStartTime + "</pre>"
				+ "<pre>Browser Name / Version	: 	</pre>" + "<pre>Test End Time		: 	</pre>"
				+ "<pre>Script Execution Time	: 	</pre>" + "<pre>Script Execution Status	: 	</pre>" + "</div><br>"
				+ "<table id=\"Log\">\"<tr><th width=\"4%\">Step</th>" + "<th width=\"16%\">Execution Date-Time</th>"
				+ "<th width=\"35%\">Step Description</th>" + "<th width=\"38%\">Actual Result</th>"
				+ "<th width=\"7%\">Status</th>" + "</tr>" + "</table>" + "</body></html>");
		wHTML.close();
	}

	/**
	 * This method will create notepad result template
	 * 
	 * @param fileName
	 * @param vGetDateForNotepad
	 * @throws IOException
	 * @author Vidyasagar Mada
	 */
	public void createNotepadResultTemplate(String fileName, String vGetDateForNotepad) throws IOException {
		fileName = fileName.toUpperCase();
		vGetDateForNotepad = vGetDateForNotepad.toUpperCase();
		String txtReportPath = System.getProperty("user.dir") + "/Reports/" + filenameappender + "/NotepadReport/";
		File folderTxtReport = new File(txtReportPath);
		if (!folderTxtReport.exists())
			folderTxtReport.mkdir();
		NotePadResultFile = txtReportPath + fileName + "_" + vGetDateForNotepad + ".txt";
		NotePadResultFile1 = fileName + "_" + vGetDateForNotepad;
		File folderTestcases = new File(System.getProperty("user.dir") + "/TestCases/");
		if (!folderTestcases.exists())
			folderTestcases.mkdir();
		NotepadStepsFile = System.getProperty("user.dir") + "/TestCases/" + fileName + ".txt";
		NotepadLogFile = System.getProperty("user.dir") + "/Reports/" + filenameappender + "/NotepadReport/" + fileName
				+ "_" + "EXECUTIONLOG" + "_" + vGetDateForNotepad + ".txt";

		File RF = new File(NotePadResultFile);
		File SF = new File(NotepadStepsFile);
		File LF = new File(NotepadLogFile);
		BufferedWriter wRF = new BufferedWriter(new FileWriter(RF));
		BufferedWriter wSF = new BufferedWriter(new FileWriter(SF));
		BufferedWriter wLF = new BufferedWriter(new FileWriter(LF));
		if (RF.exists()) {
			RF.delete();
		}
		if (SF.exists()) {
			SF.delete();
		}
		if (LF.exists()) {
			LF.delete();
		}
		RF.createNewFile();
		SF.createNewFile();
		LF.createNewFile();
		wSF.write("************************** - " + fileName + " - " + "DETAILED STEPS - **************************");
		wSF.newLine();
		wSF.newLine();
		wLF.write("************************** - " + fileName + " - " + "EXECUTION LOG - **************************");
		wLF.newLine();
		wLF.newLine();
		String computername = InetAddress.getLocalHost().getHostName();
		ScriptStartTime = Calendar.getInstance().getTime().toString();
		wRF.write("\t\t\t" + "*******************************************************");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Script Name" + "\t" + ": " + fileName.toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Environment" + "\t" + ": ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Executed By" + "\t" + ": " + (System.getProperty("user.name")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Java Version" + "\t\t" + ": " + (System.getProperty("java.version")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "OS Name / Version" + "\t" + ": " + (System.getProperty("os.name")).toUpperCase() + " / "
				+ (System.getProperty("os.version")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Computer Name" + "\t\t" + ": " + computername.toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Browser Name / Version" + "\t" + ": ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Start Time" + "\t\t" + ": " + ScriptStartTime);
		wRF.newLine();
		wRF.write("\t\t\t" + "Test End Time: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Script Execution Time: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Total Number Of Steps: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Script Execution Status: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "  						: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "*******************************************************");
		wRF.newLine();
		wRF.newLine();
		wRF.close();
		wSF.close();
		wLF.close();
	}

	/**
	 * This method will update the reports with the result
	 * 
	 * @param value
	 * @param value1
	 * @param Result
	 * @throws IOException
	 * @author Vidyasagar Mada
	 */
	public static void updateReports(updateValue value, String value1, String Result) throws IOException {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String newNotepadText = null;
		String newHTMLtext = null;
		String old_NotepadValue = null;
		String new_NotepadValue = null;
		String old_HTMLValue = null;
		String new_HTMLValue = null;
		File NotePadFile = new File(NotePadResultFile);
		File HTMLFile = new File(HTMLResultFile);
		BufferedReader NotePadFilereader = new BufferedReader(new FileReader(NotePadFile));
		BufferedReader HTMLFilereader = new BufferedReader(new FileReader(HTMLFile));
		String line = "", oldtext = "";
		while ((line = NotePadFilereader.readLine()) != null) {
			oldtext += line + "\r\n";
		}
		NotePadFilereader.close();
		String line1 = "", oldtext1 = "";
		while ((line1 = HTMLFilereader.readLine()) != null) {
			oldtext1 += line1 + "\r\n";
		}
		HTMLFilereader.close();
		FileWriter NotePadFilewriter = new FileWriter(NotePadResultFile);
		FileWriter HTMLFilewriter = new FileWriter(HTMLResultFile);
		switch (value) {
		case bName: {
			old_NotepadValue = "\t\t\t" + "Browser Name / Version" + "\t" + ": ";
			new_NotepadValue = "\t\t\t" + "Browser Name / Version" + "\t" + ": " + value1;
			old_HTMLValue = "<pre>Browser Name / Version	: 	</pre>";
			new_HTMLValue = "<pre>Browser Name / Version	: 	" + value1 + "</pre>";
			break;
		}
		case Env: {
			old_NotepadValue = "\t\t\t" + "Test Environment" + "\t" + ": ";
			new_NotepadValue = "\t\t\t" + "Test Environment" + "\t" + ": " + value1;
			old_HTMLValue = "<pre>Test Environment	: 	</pre>";
			new_HTMLValue = "<pre>Test Environment	: 	" + value1 + "</pre>";
			break;
		}
		case tEndTime: {
			ScriptEndTime = Calendar.getInstance().getTime().toString();
			old_NotepadValue = "\t\t\t" + "Test End Time: ";
			new_NotepadValue = "\t\t\t" + "Test End Time" + "\t\t" + ": " + ScriptEndTime;
			old_HTMLValue = "<pre>Test End Time		: 	</pre>";
			new_HTMLValue = "<pre>Test End Time		: 	" + ScriptEndTime + "</pre>";
			break;
		}
		case execTime: {
			ScriptexecTime = value1;
			old_NotepadValue = "\t\t\t" + "Script Execution Time: ";
			new_NotepadValue = "\t\t\t" + "Script Execution Time" + "\t" + ": " + value1;
			old_HTMLValue = "<pre>Script Execution Time	: 	</pre>";
			new_HTMLValue = "<pre>Script Execution Time	: 	" + value1 + "</pre>";
			break;
		}

		case execStatus: {
			old_NotepadValue = "\t\t\t" + "Script Execution Status: ";
			new_NotepadValue = "\t\t\t" + "Script Execution Status	: " + Result;
			old_HTMLValue = "<pre>Script Execution Status	: 	</pre>";
			new_HTMLValue = null;
			if (Result.equalsIgnoreCase("pass")) {
				new_HTMLValue = "<pre>Script Execution Status	: 	" + "<FONT COLOR=\"#006400\"><b>"
						+ Result.toUpperCase() + "</b></FONT>" + "</pre>";
			} else {
				new_HTMLValue = "<pre>Script Execution Status : 	" + "<FONT COLOR=\"#B22222\"><b>"
						+ Result.toUpperCase() + "</b></FONT>" + "</pre>";
			}
			break;
		}
		case failedStepNo: {
			old_NotepadValue = "\t\t\t" + "  						: ";
			new_NotepadValue = null;
			old_HTMLValue = "<pre>						: 	</pre>";
			new_HTMLValue = null;
			if (Result.equalsIgnoreCase("pass")) {
				new_HTMLValue = "";
				new_NotepadValue = "";
			}
			break;
		}
		case ScreenPrint: {
			boolean append = true;
			FileWriter fout1 = new FileWriter(HTMLResultFile, append);
			BufferedWriter fbw1 = new BufferedWriter(fout1);
			fbw1.write("<!DOCTYPE html><html><head><style>"
					+ "img.one{display: block;border-style:solid;border-color:#FF0000;max-width:90%;margin-left: auto;margin-right: auto}"
					+ "</style></head><body>" + "<font color=\"#FF0000\">" + "<h2>Screen Print</h2>" + "</font>"
					+ "<img class=\"one\" border=\"10\" align=\"middle\" src=\"C:/Selenium_Pursuit_WD_232/Sanity_Suite/Screen_Prints/ITV1_SMB_Sanity_WED_JUN_26_2013_15_26_13_PM_CDT_PASS.PNG\">"
					+ "</body></html>");
			fbw1.close();
			break;
		}
		}
		newNotepadText = oldtext.replaceAll(old_NotepadValue, new_NotepadValue);
		newHTMLtext = oldtext1.replaceAll(old_HTMLValue, new_HTMLValue);
		NotePadFilewriter.write(newNotepadText);
		HTMLFilewriter.write(newHTMLtext);
		NotePadFilewriter.close();
		HTMLFilewriter.close();
		newNotepadText = null;
		newHTMLtext = null;
		old_NotepadValue = null;
		new_NotepadValue = null;
		old_HTMLValue = null;
		new_HTMLValue = null;
	}

	/**
	 * This method will update HTML report with status, expected, actual and
	 * additional info
	 * 
	 * @param status
	 * @param expected
	 * @param actual
	 * @throws IOException
	 * @author Vidyasagar Mada
	 */
	public static void writeToHTML(String status, String expected, String actual, String addInfo) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		FileWriter fout1 = new FileWriter(HTMLResultFile, true);
		BufferedWriter fbw1 = new BufferedWriter(fout1);
		String gstatus = null;
		String gactual = null;
		try {
			if (status.equalsIgnoreCase("pass")) {
				gstatus = "<FONT COLOR=\"#006400\"><b>" + status.toUpperCase() + "</b></FONT>";
				gactual = "<FONT COLOR=\"#006400\">" + actual + "</FONT>";
				fbw1.write("<!DOCTYPE html><html><head><style>"
						+ "#Log {font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:100%;table-layout:fixed;border-collapse:collapse;}"
						+ "#Log td {font-size:1em;border:1px solid #98bf21;padding:3px 7px 2px 7px;word-wrap:break-word;}"
						+ "#Log tr.alt1 td {border:3px solid #B22222;background-color:#EA9A9A;}"
						+ "#Log tr.alt2 td {border:3px solid #FF9900}" + "#Log tr.alt3 td {background-color:#EAF2D3;}"
						+ "</style></head><body>" + "<table id=\"Log\">" + "<tr>"
						+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
						+ "<td align=\"center\" width=\"16%\">" + getDateFormat(dateType2) + "</td>"
						+ "<td align=\"left\" width=\"35%\">" + expected + "</td>" + "<td align=\"left\" width=\"38%\">"
						+ gactual + "</td>" + "<td align=\"center\" width=\"7%\">" + gstatus + "</td>" + "</tr>"
						+ "</table></body></html>");
			} else if (status.equalsIgnoreCase("fail")) {
				gstatus = "<FONT COLOR=\"#B22222\"><b>" + status.toUpperCase() + "</b></FONT>";
				gactual = "<FONT COLOR=\"#B22222\"><b>" + actual + "</b></FONT>";
				fbw1.write("<!DOCTYPE html><html><body>" + "<table id=\"Log\">" + "<tr class=\"alt1\">"
						+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
						+ "<td align=\"center\" width=\"16%\">" + getDateFormat(dateType2) + "</td>"
						+ "<td align=\"left\" width=\"35%\">" + expected + "</td>" + "<td align=\"left\" width=\"38%\">"
						+ gactual + ""
						+ "<span class ='embed'><a href=''onclick=\"img=document.getElementById('img_2'); img.style.display = (img.style.display == 'none' ? 'block' : 'none');return false\">screenshot_2.png</a><br>&nbsp;<img id=\"img_2\" style=\"display: none;\" src="
						+ takeScreenShot("FAIL") + "></span>" + "" + "" + "</FONT></td>"
						+ "<td align=\"center\" width=\"7%\">" + gstatus + "</td>" + "</tr>"
						+ "</table></body></html>");
			} else if (status.equalsIgnoreCase("warning")) {
				gstatus = "<FONT COLOR=\"#FF9900\"><b>" + status.toUpperCase() + "</b></FONT>";
				gactual = "<FONT COLOR=\"#FF9900\"><b>" + actual + "</b></FONT>";
				fbw1.write("<!DOCTYPE html><html><body>" + "<table id=\"Log\">" + "<tr class=\"alt2\">"
						+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
						+ "<td align=\"center\" width=\"16%\">" + getDateFormat(dateType2) + "</td>"
						+ "<td align=\"left\" width=\"35%\">" + expected + "</td>" + "<td align=\"left\" width=\"38%\">"
						+ gactual + "</td>" + "<td align=\"center\" width=\"7%\">" + gstatus + "</td>" + "</tr>"
						+ "</table></body></html>");
			} else if (status.equalsIgnoreCase("msg")) {
				fbw1.write("<!DOCTYPE html><html><body>" + "<table id=\"Log\">" + "<tr class=\"alt3\">"
						+ "<td align=\"center\" width=\"4%\"><b></b></td>" + "<td align=\"center\" width=\"16%\">"
						+ getDateFormat(dateType2) + "</td>" + "<td width=\"73%\"><b>" + expected.toUpperCase()
						+ "</b></td>" + "<td align=\"center\" width=\"7%\"></td>" + "</tr>" + "</table></body></html>");
			} else if (status.equalsIgnoreCase("info")) {
				fbw1.write("<!DOCTYPE html><html><body>" + "<table id=\"Log\">" + "<tr class=\"alt3\">"
						+ "<td align=\"center\" width=\"4%\"><b></b></td>" + "<td align=\"center\" width=\"16%\">"
						+ getDateFormat(dateType2) + "</td>" + "<td align=\"center\" width=\"73%\"><b>"
						+ addInfo.toUpperCase() + "</b></td>" + "<td align=\"center\" width=\"7%\"></td>" + "</tr>"
						+ "</table></body></html>");
			} else if (status.equalsIgnoreCase("eom")) {
				fbw1.write("<!DOCTYPE html><html><body>" + "<table id=\"Log\">"
						+ "<tr class=\"alt3\"><td align=\"center\">END OF TEST CASE</td></tr>"
						+ "</table></body></html>");
			}
			fbw1.close();
		} catch (Exception e) {
			System.out.println("ERROR OCCURED IN METHOD - " + methodName);
			System.out.println(methodName + " Method Error Caused By - " + e.getMessage());
		}
	}

	/**
	 * This method will update notepad report with status, expected, actual and
	 * additional info
	 * 
	 * @param status
	 * @param expected
	 * @param actual
	 * @param addInfo
	 * @author Vidyasagar Mada
	 */
	public static void writeToNotepad(String status, String expected, String actual, String addInfo) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			FileWriter fout = new FileWriter(NotePadResultFile, true);
			FileWriter fout2 = new FileWriter(NotepadStepsFile, true);
			BufferedWriter fbw = new BufferedWriter(fout);
			BufferedWriter fbw2 = new BufferedWriter(fout2);
			fbw2.write(expected);
			fbw2.newLine();
			if (status.equalsIgnoreCase("pass") | status.equalsIgnoreCase("fail")
					| status.equalsIgnoreCase("warning")) {
				fbw.write("Step ");
				fbw.write(stepCount + "");
				fbw.write("\t\t");
				fbw.write(getDateFormat(dateType2));
				fbw.write("\t\t");
				fbw.write(status.toUpperCase());
				fbw.newLine();
				fbw.write("------------------------------------------------------------");
				fbw.newLine();
				fbw.write("Description	: " + expected);
				fbw.newLine();
				fbw.write("Actual Result	: " + actual);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
			} else if (status.equalsIgnoreCase("msg")) {
				fbw.write("************************************************************");
				fbw.newLine();
				fbw.write("Description : " + expected);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
			} else if (status.equalsIgnoreCase("info")) {
				fbw.newLine();
				fbw.write("\t\t" + addInfo);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
			}
			fbw.close();
			fbw2.close();
		} catch (Exception e) {
			System.out.println("ERROR OCCURED IN METHOD - " + methodName);
			System.out.println(methodName + " Method Error Caused By - " + e.getMessage());
		}
	}

	/**
	 * This method will log events to HTML and notepad reports
	 * 
	 * @param status
	 * @param expected
	 * @param actual
	 * @param addInfo
	 * @author Vidyasagar Mada
	 */
	public static void LogEvent(TestStatus status, String expected, String actual, String addInfo) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		switch (status) {
		case PASS: {
			try {
				Reporter.log("<span class='pass-label'>P </span>" + expected + actual + "<br>");
				writeToNotepad("PASS", expected, actual, addInfo);
				writeToHTML("PASS", expected, actual, addInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case FAIL: {
			try {
				Reporter.log("<span class='fail-label'>F </span>" + expected + "<br>");
				reportLogScreenshot(takeScreenShot("FAIL"));
				writeToNotepad("FAIL", expected, actual, addInfo);
				writeToHTML("FAIL", expected, actual, "");
				failurepopUp(expected, actual);
				writeToNotepad("INFO", "", "", "-------->[ Exiting Script Execution".toUpperCase() + " ]<--------");
				writeToHTML("INFO", "", "", "-------->[ Exiting Script Execution".toUpperCase() + " ]<--------");
				Assert.fail();
			} catch (Exception e) {
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case WARNING: {
			try {
				Reporter.log("<span class='fail-label'>W </span>" + expected + "<br>");
				writeToNotepad("WARNING", expected, actual, addInfo);
				writeToHTML("WARNING", expected, actual, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case MSG: {
			try {
				Reporter.log("<span class='info-label'>M </span>" + expected + "<br>");
				writeToNotepad("MSG", expected, actual, addInfo);
				writeToHTML("MSG", expected, actual, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case INFO: {
			try {
				Reporter.log("<span class='info-label'>I </span>" + expected + "<br>");
				if (addInfo.equalsIgnoreCase("-------->[ End Of Script Execution ]<--------")) {
					stepCount--;
				}
				writeToNotepad("INFO", expected, actual, addInfo);
				writeToHTML("INFO", expected, actual, addInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case EOM: {
			try {
				Reporter.log(expected + "<br>");
				if (addInfo.equalsIgnoreCase("-------->[ End Of TestCase ]<--------")) {
					stepCount--;
				}
				writeToNotepad("INFO", expected, actual, addInfo);
				writeToHTML("EOM", expected, actual, addInfo);
				stepCount = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		}
	}

	/**
	 * This method will invoke a pop-up displaying information regarding failure
	 * step
	 * 
	 * @param expected
	 * @param actual
	 * @author Vidyasagar Mada
	 * @throws InterruptedException 
	 */
	protected static void failurepopUp(String expected, String actual) throws InterruptedException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing method " + methodName);
		JFrame jframe = new JFrame();
		jframe.setTitle("Automation Pursuit".toUpperCase());
		jframe.setSize(600, 150);
		java.awt.Toolkit kit = jframe.getToolkit();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		Insets in = kit.getScreenInsets(gs[0].getDefaultConfiguration());
		Dimension d = kit.getScreenSize();
		int max_width = (d.width - in.left - in.right);
		int max_height = (d.height - in.top - in.bottom);
		jframe.setLocation((int) (max_width - jframe.getWidth()) / 2, (int) (max_height - jframe.getHeight()) / 2);
		Container pane = jframe.getContentPane();
		pane.setLayout(new GridLayout(4, 1));
		JLabel L0 = new JLabel(("Test Execution Failure Notification").toString().toUpperCase());
		L0.setBackground(Color.YELLOW);
		JLabel L1 = new JLabel(" STEP NO: " + stepCount);
		L1.setForeground(Color.RED);
		JLabel L2 = new JLabel(" Description: " + expected);
		JLabel L3 = new JLabel(" Actual Result: " + actual.toUpperCase());
		L3.setForeground(Color.RED);
		pane.add(L0);
		pane.add(L1);
		pane.add(L2);
		pane.add(L3);
		jframe.setVisible(true);
		jframe.setAlwaysOnTop(true);
		Thread.sleep(10000);
		jframe.dispose();
		System.out.println("EXITING - " + methodName);
	}

	/**
	 * This method will write to notepad log file
	 * 
	 * @param methodName
	 * @throws IOException
	 * @author Vidyasagar Mada
	 */
	public static void writeLog(String methodName) {
		boolean append = true;
		FileWriter fout;
		try {
			fout = new FileWriter(NotepadLogFile, append);

			BufferedWriter fbw = new BufferedWriter(fout);
			fbw.write("Step ");
			fbw.write(LogCount + "" + "\t");
			fbw.write(getDateFormat(dateType2) + "\t");
			fbw.write(methodName);
			fbw.newLine();
			fbw.close();
		} catch (IOException e) {
			System.out.println("Error occured while writing into notepad log file");
			e.printStackTrace();
		}
		LogCount++;
	}

	/**
	 * This method will return specified formatted date
	 * 
	 * @param vDateFormat
	 * @return formattedDate
	 * @author Vidyasagar Mada
	 */
	public static String getDateFormat(String vDateFormat) {
		Date vDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(vDateFormat);
		return sdf.format(vDate);
	}

	/**
	 * This method will format time into HH:MM:SS format
	 * 
	 * @param diffSeconds
	 * @return
	 * @author Vidyasagar Mada
	 */
	public static String formatIntoHHMMSS(long milliSeconds) {
		DateFormat f1 = new SimpleDateFormat("hh:mm:ss");
		return f1.format(milliSeconds);
	}

	/**
	 * This method will capture the screenshot and will return the path where it
	 * saved to Custom report
	 * 
	 * @param status
	 * @return pathToScreenshot
	 * @throws Exception
	 * @author Vidyasagar Mada
	 */
	private static String takeScreenShot(String status) {

		String screenshotfolderpath = System.getProperty("user.dir") + "/Reports/" + filenameappender + "/SCREENSHOTS/";

		File ScreenShotFolder = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/");
		if (!ScreenShotFolder.exists()) {
			ScreenShotFolder.mkdir();
		}
		File screenshotsfolder = new File(screenshotfolderpath);
		if (!screenshotsfolder.exists()) {
			screenshotsfolder.mkdir();
		}
		printscreen = screenshotfolderpath + ScreenShotFile + "_" + getDateFormat(dateType1).toUpperCase() + "_"
				+ status + ".PNG";
		try {
			File TakeScreenPrint = ((TakesScreenshot) Keywords.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(TakeScreenPrint, new File(printscreen));
		} catch (IOException e) {
			LogEvent(TestStatus.FAIL, "Unable to save captured screenshot", e.getMessage().toString(), "");
			e.printStackTrace();
		}
		ScreenShotFile = null;
		Runtime.getRuntime().gc();
		return printscreen;
	}

	/**
	 * This method will capture the exception as soon as it occurs and update the
	 * reports accordingly
	 * 
	 * @param exception
	 * @param expected
	 * @author Vidyasagar Mada
	 */
	public static void catchException(Exception exception, String expected) {
		if (Keywords.driver.getTitle().equalsIgnoreCase("Internet Explorer cannot display the webpage")
				| Keywords.driver.getTitle().equalsIgnoreCase("Problem loading page")) {
			try {
				LogEvent(TestStatus.FAIL, expected, "CHECK THE INTERNET CONNECTION", "");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			String sTemp1 = exception.getMessage();
			String[] sTemp2 = sTemp1.split("WARN");
			sTemp2[0] = new String(sTemp2[0].substring(0, (sTemp2[0].length()) - 1));
			try {
				LogEvent(TestStatus.FAIL, expected, sTemp2[0], "");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			sTemp1 = null;
		}
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will finilize the report file names
	 * 
	 * @param finalScriptStatus
	 * @return finalReportFolderPath
	 * @author Vidyasagar Mada
	 */
	public static String RenameResultLog(String finalScriptStatus) {
		System.out.println("Executing method " + Thread.currentThread().getStackTrace()[1].getMethodName());
		File oldNotepadfile = new File(NotePadResultFile);
		File newNotepadfile = new File(
				System.getProperty("user.dir") + "/Reports/" + filenameappender + "/NotepadReport/"
						+ NotePadResultFile1.toUpperCase() + "_" + finalScriptStatus + ".txt".toUpperCase());
		FinalNotePadName = newNotepadfile.toString();
		File oldHTMLfile = new File(HTMLResultFile);
		File newHTMLfile = new File(System.getProperty("user.dir") + "/Reports/" + filenameappender + "/HTMLReport/"
				+ NotePadResultFile1.toUpperCase() + "_" + finalScriptStatus + ".html".toUpperCase());
		FinalHTMLName = newHTMLfile.toString();
		FinalFolderpath = System.getProperty("user.dir") + "/Reports/" + filenameappender;
		try {
			oldNotepadfile.renameTo(newNotepadfile);
			oldHTMLfile.renameTo(newHTMLfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FinalFolderpath;
	}

	/**
	 * This method will update the reportNG report with screenshot
	 * 
	 * @param pathToFile
	 * @author Vidyasagar Mada
	 */
	public static void reportLogScreenshot(String pathToFile) {
		String src = "../img/";
		try {
			FileUtils.copyFile(new File(pathToFile), new File(src));
		} catch (IOException e) {
			System.out.println("File not find to log screenshot");
			e.printStackTrace();
		}
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<a target='_blank' href='" + src + "'>[view screenshot]</a>");
	}
}