package com.dxc.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * This class is core for reading test data and test object
 * 
 * @author Vidyasagar mada
 */
public class BaseClass implements Constants {
	public static Properties props=PropUtil.props;
	public enum updateValue {
		bName, Env, tEndTime, execTime, execStatus, failedStepNo, ScreenPrint
	};

	public enum TestStatus {
		PASS, FAIL, WARNING, MSG, INFO, EOM
	};

	public static WebDriver driver;
	private static Map<String, String> TDMap = new HashMap<String, String>();
	private static Map<String, String> TOMap = new HashMap<String, String>();
	public static Document document = null;

	/*
	 * static { try { DocumentBuilderFactory dbFactory = DocumentBuilderFactory
	 * .newInstance(); DocumentBuilder dBuilder =
	 * dbFactory.newDocumentBuilder(); if
	 * (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) { File
	 * fXmlFile = new File(System.getProperty("user.dir") +
	 * "\\TestData\\EP_TestData.xml"); document = dBuilder.parse(fXmlFile); }
	 * else if (System.getProperty("os.name").toUpperCase() .contains("MAC")) {
	 * File fXmlFile = new File(System.getProperty("user.dir") +
	 * "/TestData/EP_TestData.xml"); document = dBuilder.parse(fXmlFile); }
	 * document.getDocumentElement().normalize(); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * This method reads test data from excel sheet
	 * 
	 * @param TDKey
	 * @return testdataValue
	 * @author Vidyasagar Mada
	 */
	public static String getTestData(String TDKey) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String sTemp = null;
		try {
			System.out.println(TDKey+"TDKEYYYYYYYYYYYYYYYYYYYYYYYY");
			sTemp = TDMap.get(TDKey).toString();
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Read Test Data From Hash Map",
					"Check - '***KEYNAME***' Used As Parameter For Method - '" + methodName + "' In Main Script", "");
		}
		methodName = null;
		return sTemp;
	}

	/**
	 * This method reads test object from excel sheet
	 * 
	 * @param TOKey
	 * @return testobjectValue
	 * @author Vidyasagar Mada
	 */
	public static String getTestObject(String TOKey) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String sTemp = null;
		try {
			sTemp = TOMap.get(TOKey).toString();
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Read Test Object Value From Hash Map",
					"Check - '*KeyName*' Used As Parameter For Method - '" + methodName + "' In Main Script", "");
		}
		methodName = null;
		return (sTemp);
	}

	/**
	 * This method will read excel data sheets and put them to Testdata and Test
	 * object hashmaps
	 * 
	 * @author Vidyasagar Mada
	 * @param xlsheetname
	 * @param ObjSheetName
	 * @param TestDataSheetName
	 */
	public static void readExcelData(String xlsheetname, String ObjSheetName, String TestDataSheetName) {
		Workbook workbook = null;
		String excelDataSheet = System.getProperty("user.dir") + "/TestData/" + xlsheetname + ".xls";
		try {
			WorkbookSettings settings = new WorkbookSettings();
			settings.setSuppressWarnings(true);
			workbook = Workbook.getWorkbook(new File(excelDataSheet), settings);
		} catch (Exception e) {
			String vException = e.getMessage().toString();
			CustomReporter.LogEvent(TestStatus.FAIL, "Check Data File " + excelDataSheet,
					(vException.substring((excelDataSheet.length()) + 2, (vException.length()) - 1)).toUpperCase(), "");
		}
		Sheet objSheet = workbook.getSheet(ObjSheetName);
		for (int i = 0; i < objSheet.getRows(); i++) {
			Cell[] cell = objSheet.getRow(i);
			if (!(cell[0].getContents().toString() == null)) {
				TOMap.put(cell[0].getContents().toString(),
						cell[0].getContents().toString() + "," + cell[1].getContents().toString() + ","
								+ cell[2].getContents().toString() + "," + cell[3].getContents().toString());
			}
		}
		Sheet dataSheet = workbook.getSheet(TestDataSheetName);
		for (int i = 0; i < dataSheet.getRows(); i++) {
			Cell[] cell = dataSheet.getRow(i);
			if (cell[0].getContents() != null) {
				TDMap.put(cell[0].getContents().toString(), cell[1].getContents().toString());
			}
		}
		objSheet = null;
		dataSheet = null;
		workbook = null;
	}

	/**
	 * This method will reset test data and test object hashmaps
	 * 
	 * @author Vidyasagar Mada
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clearHashMap() {
		TOMap = new HashMap();
		TDMap = new HashMap();
	}

	/**
	 * This method will format long seconds into HHMMSS format
	 * 
	 * @author Vidyasagar Mada
	 * @param diffSeconds
	 * @return formattedTime
	 */
	protected static String formatIntoHHMMSS(long diffSeconds) {
		diffSeconds = diffSeconds / 1000;
		long hours = diffSeconds / 3600;
		long remainder = diffSeconds % 3600;
		long minutes = remainder / 60;
		long seconds = remainder % 60;
		return ((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
				+ (seconds < 10 ? "0" : "") + seconds);
	}

	/**
	 * This methos will read the data from XML
	 * 
	 * @author Vidyasagar Mada
	 * @param clientName
	 * @param columnName
	 * @return nodeValue
	 */
	public static String getXMLData(String clientName, String columnName) {
		String nodeValue = "";
		try {
			NodeList nList = document.getElementsByTagName(clientName);
			Element nNode = (Element) nList.item(0);
			nodeValue = nNode.getElementsByTagName(columnName).item(0).getTextContent();
			System.out.println("nodeValue :" + nodeValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeValue;
	}
}