package com.dxc.setup;

import java.util.Calendar;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.dxc.util.BaseClass;
import com.dxc.util.CustomReporter;
import com.dxc.util.MailUtil;
import com.dxc.util.ZipUtil;

/**
 * This class is having all the set up for pre and post test execution setup
 * 
 * @author Vidyasagar Mada
 */
public class SetUpTearDown extends BaseClass {
	public static String scriptName = null;
	public long vScriptStartTime = Calendar.getInstance().getTimeInMillis();
	DriverClass d = new DriverClass();

	/**
	 * This method will be executed before every suite and will read test data and
	 * test objects from spreadsheet
	 * 
	 * @author Vidyasagar Mada
	 * @throws Exception
	 */
	@BeforeSuite
	public void AtBeforeSuite() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing method " + methodName);
		readExcelData(props.getProperty("excel.sheet.name"), "TO_PP", "TD_PP");
		System.out.println("Execution of " + methodName + " is completed.");
	}

	/**
	 * This method will be execute before every test and will <br>
	 * 1. Create a sample report and <br>
	 * 2. Call appropriate driver creator method based on parameters from dataconfig
	 * file
	 * 
	 * @author Vidyasagar Mada
	 * @param ITestContext
	 * @throws Exception
	 */
	@BeforeTest
	public void AtBeforeTest(ITestContext ic) throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing method " + methodName);
		scriptName = ic.getCurrentXmlTest().getName().toString();
		new CustomReporter(scriptName, CustomReporter.getDateFormat(dateType1));
		if (props.getProperty("platform").contains("desktop")) {
			if (props.getProperty("browser").equals("ff"))
				d.winFFDriver();
			else if (props.getProperty("browser").equals("ie"))
				d.winIEDriver();
			else if (props.getProperty("browser").equals("chrome"))
				d.winchromeDriver(ic);
		} else if (props.getProperty("platform").contains("andweb")) {
			d.andBrowserDriver();
		} else if (props.getProperty("platform").contains("andapp")) {
			d.andApp(ic);
		} else if (props.getProperty("platform").contains("iosweb")) {
			d.iOSWebDriver(ic);
		} else if (props.getProperty("platform").contains("iosapp")) {
			d.iOSAppDriver(ic);
		}
		System.out.println("Execution of " + methodName + " is completed.");
		methodName = null;
	}

	/**
	 * This method will be executed after every test and will<br>
	 * 1. Finalize the reports and<br>
	 * 2. Will zip the report and send e-mail report to specified person if mail
	 * reporting switch is enabled
	 * 
	 * @author Vidyasagar Mada
	 * @param itestcontext
	 * @throws Exception
	 */
	@AfterTest
	public void AtAfterTest(ITestContext itestcontext) throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing " + methodName);
		String finalScriptStatus;
		System.out.println("---------------------" + itestcontext.getFailedTests().toString());
		String sTemp = itestcontext.getFailedTests().toString();
		CustomReporter.updateReports(updateValue.tEndTime, "", "");
		CustomReporter.updateReports(updateValue.execTime,
				formatIntoHHMMSS(Calendar.getInstance().getTimeInMillis() - vScriptStartTime).toString(), "");
		if (!sTemp.contains("FAILURE")) {
			finalScriptStatus = "PASS";
			CustomReporter.updateReports(updateValue.execStatus, "", finalScriptStatus);
		} else {
			finalScriptStatus = "FAIL";
			CustomReporter.updateReports(updateValue.execStatus, "", finalScriptStatus);
		}
		CustomReporter.LogEvent(TestStatus.INFO, "", "", "-------->[ End Of Script Execution ]<--------");
		if (props.getProperty("send.mail").equals("1")) {
			MailUtil sm = new MailUtil();
			String mailTo = props.getProperty("mail.to");
			sm.sendEmail(mailTo, "", new ZipUtil().zipFile(CustomReporter.RenameResultLog(finalScriptStatus), true));
		} else
			System.out.println(
					"Not sending mail as e-mail Reporting switch is disabled. To enable it, update \"send.mail\" key to \"1\"");
		CustomReporter.stepCount = 1;
		if (!(props.getProperty("platform").contains("androidapp"))) {
			driver.quit();
		}
	}

	/**
	 * This method will be executed after every suite and will clear the java heap
	 * memory and hashmaps
	 * 
	 * @author Vidyasagar Mada
	 * @throws Exception
	 */
	@AfterSuite
	public void AtAfterSuite() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing method " + methodName);
		clearHashMap();
		System.gc();
	}
}