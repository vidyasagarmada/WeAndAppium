package com.dxc.scripts;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.dxc.BusinessComponents.BusinessComponents;
import com.dxc.misc.ReporterClass;
import com.dxc.util.BaseClass;
import com.dxc.util.CustomReporter;
import com.dxc.util.Keywords;
import com.dxc.util.PropUtil;

/**
 * This class is sample test class which can be called via testNG xml to be
 * executed
 * 
 * @author Vidyasagar Mada
 *
 */
public class SampleTest extends BaseClass {
	String methodName;
	BusinessComponents businessComponents = new BusinessComponents();
	String resNum = null;
	ReporterClass r = new ReporterClass();

	/**
	 * This is the actual test case that need to be called to execute
	 * 
	 * @author Vidyasagar Mada
	 * @throws Exception
	 */
	@Test
	public void sampleTest1() throws Exception {
		this.methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing test script " + methodName);
		Keywords.launchBrowser(getTestData("SampleURL"));
		r.logWithScreenshot("Test report log");
		Keywords.wait(5);
		Keywords.highlightElement(driver.findElement(By.cssSelector("a")));
		Keywords.wait(5);
		driver.findElement(By.cssSelector("a")).click();
		Keywords.wait(2);
		Keywords.verifyText(getTestObject("gmailSignInHead"), "Sign in");
	}

	/**
	 * This method will be executed after each test case
	 * 
	 * @author Vidyasagar Mada
	 */
	@AfterMethod
	public void endOfMethod() {
		System.out.println("End of executing test case: " + methodName);
		CustomReporter.LogEvent(TestStatus.EOM, "", "", "");
	}
}