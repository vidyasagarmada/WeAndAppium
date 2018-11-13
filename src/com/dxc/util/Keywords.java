package com.dxc.util;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

/**
 * This class have all the generic reusable keywords and actions
 * 
 * @author Vidyasagar Mada
 *
 */
public class Keywords extends BaseClass {
	private static int Index = 0;
	@SuppressWarnings("unused")
	private String mainpage;
	public WebDriver popup;

	public enum Sel {
		id, name
	};

	private static Actions actions;

	@SuppressWarnings("unused")
	private String className = this.getClass().getSimpleName().toString().toUpperCase();
	public static String sHandleBefore = "";
	public static String highlightObj;

	public enum SeleniumIdentifiers {
		className, cssSelector, id, linkText, name, partialLinkText, tagName, xpath, byIndex, byValue, byVisibleText
	}

	public static String CurrentWindowHandle;

	/**
	 * This method will pass the instance of driver to all keywords that needs
	 * actions to perform on specific browser/device
	 * 
	 * @param driver
	 * @param elementHighlightSwitch
	 * @author Vidyasagar Mada
	 */
	public Keywords(WebDriver driver, String elementHighlightSwitch) {
		highlightObj = elementHighlightSwitch;
		if (props.getProperty("platform").contains("andapp")
				|| props.getProperty("platform").contains("ios")) {
			this.driver = (AppiumDriver) driver;
		}
		if (!(props.getProperty("platform").contains("andapp"))) {
			this.driver = driver;
		}
		actions = new Actions(driver);
		if (props.getProperty("platform").contains("desktop")) {
			mainpage = driver.getWindowHandle();
		}
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
	}

	/**
	 * This method will call openbrowser method based on the platform defined in
	 * properties file
	 * 
	 * @author LavaKumar
	 * @param url
	 */
	public static void launchBrowser(String url) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Executing method " + methodName);
		if (props.getProperty("platform").contains("desktop")) {
			Keywords.openBrowser(url);
			Keywords.wait(5);
		} else if (props.getProperty("platform").contains("andweb")
				|| props.getProperty("platform").contains("iosweb")) {
			Keywords.openBrowser(url);
			Keywords.wait(5);
		}
	}

	/**
	 * This method will split the row data of test object fetched from
	 * spreadsheet
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @return stringArray
	 */
	public static String[] splitTestObject(String TestObject) {
		String[] sTemp = (TestObject.split(",", 4));
		sTemp[1] = new String(sTemp[1].trim());
		sTemp[2] = new String(sTemp[2].trim());
		sTemp[3] = new String(sTemp[3].trim());
		return sTemp;
	}

	/**
	 * This method will identify the object identification type and will return
	 * appropriate WebElement
	 * 
	 * @param objName
	 * @param objIdentifier
	 * @param LogEvent
	 * @param Locator
	 * @author LavaKumar
	 * @return WebElement
	 */
	public static WebElement checkElement(String objName, String objIdentifier, String LogEvent, String Locator) {
		SeleniumIdentifiers Identifier = SeleniumIdentifiers.valueOf(objIdentifier);
		WebElement Element = null;

		switch (Identifier) {
		case className: {
			Element = driver.findElement(By.className(Locator));
			break;
		}
		case cssSelector: {
			Element = driver.findElement(By.cssSelector(Locator));
			break;
		}
		case id: {
			Element = driver.findElement(By.id(Locator));
			break;
		}
		case linkText: {
			Element = driver.findElement(By.linkText(Locator));
			break;
		}
		case name: {
			Element = driver.findElement(By.name(Locator));
			break;
		}
		case partialLinkText: {
			Element = driver.findElement(By.partialLinkText(Locator));
			break;
		}
		case tagName: {
			Element = driver.findElement(By.tagName(Locator));
			break;
		}
		case xpath: {
			Element = driver.findElement(By.xpath(Locator));
			break;
		}
		default:
			System.out.println("************************Provide valid WebElement Identifier**********************");
			break;
		}
		return Element;
	}

	/**
	 * This method will be useful in navigating to a specified URL in browser
	 * 
	 * @author LavaKumar
	 * @param URL
	 */
	private static void openBrowser(String URL) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			driver.navigate().to(URL);
			CustomReporter.LogEvent(TestStatus.PASS, "Navigate to URL - " + URL,
					"Navigate to URL - " + URL + " - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			CustomReporter.catchException(e, "Navigate to the URL '" + URL + "'");
		}
		URL = null;
	}

	/**
	 * This method will enter text into text field/text box
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @param value
	 */
	public static void editAField(String TestObject, String value) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (highlightObj.equalsIgnoreCase("on")) {
				if ((props.getProperty("platform").contains("desktop"))) {
					highlightElement(we);
				}
			}
			if (!(props.getProperty("platform").contains("andapp")))
				we.clear();
			we.sendKeys(value);
			CustomReporter.LogEvent(TestStatus.PASS, "Enter " + sTemp[2] + " '" + value + "'",
					"Enter " + sTemp[2] + " '" + value + "'" + " - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			CustomReporter.catchException(e, "Enter " + sTemp[2] + " '" + value + "'");
		}
		sTemp = null;
		value = null;
	}

	/**
	 * This method will click on WebElement passed to it
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 */
	public static void clickElement(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (highlightObj.equalsIgnoreCase("on")
					&& ((props.getProperty("platform").contains("desktop")))) {
				highlightElement(we);
			}
			we.click();
			CustomReporter.LogEvent(TestStatus.PASS, "Click '" + sTemp[2] + "'",
					"Click '" + sTemp[2] + "'" + " - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			CustomReporter.catchException(e, "Click '" + sTemp[2] + "'");
		}
		sTemp = null;
	}

	/**
	 * This method will pass TAB key to the field passed as parameter
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 */
	public static void Tab(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (highlightObj.equalsIgnoreCase("on")) {
				if ((props.getProperty("platform").contains("desktop")))
					highlightElement(we);
			}
			we.sendKeys(Keys.TAB);
			CustomReporter.LogEvent(TestStatus.PASS, "Click '" + sTemp[2] + "'",
					"Click '" + sTemp[2] + "'" + " - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			CustomReporter.catchException(e, "Click '" + sTemp[2] + "'");
		}
		sTemp = null;
	}

	/**
	 * This method will check/uncheck checkbox
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @param status
	 */
	public static void checkBox(String TestObject, String status) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		WebElement we = null;
		try {
			we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
		} catch (Exception e) {
			CustomReporter.catchException(e, "Click '" + sTemp[2] + "'");
		}
		if (status.equalsIgnoreCase("check")) {
			try {
				if (!we.isSelected()) {
					if (highlightObj.equalsIgnoreCase("on")) {
						if ((props.getProperty("platform").contains("desktop")))
							highlightElement(we);
					}
					we.click();
					CustomReporter.LogEvent(TestStatus.PASS, "Check Checkbox '" + sTemp[2] + "'",
							"Check checkbox '" + sTemp[2] + "'" + "' - Successfull".toUpperCase(), "");
				} else
					CustomReporter.LogEvent(TestStatus.WARNING, "Check Checkbox '" + sTemp[2] + "'",
							"Checkbox '" + sTemp[2] + "' has already been checked", "");
			} catch (Exception e) {
				CustomReporter.catchException(e, "Check Checkbox '" + sTemp[2] + "'");
			}
		} else if (status.equalsIgnoreCase("uncheck")) {
			try {
				if (we.isSelected()) {
					if (highlightObj.equalsIgnoreCase("on")) {
						if ((props.getProperty("platform").contains("desktop")))
							highlightElement(we);
					}
					we.click();
					CustomReporter.LogEvent(TestStatus.PASS, "Uncheck Checkbox '" + sTemp[2] + "'",
							"Uncheck Checkbox '" + sTemp[2] + "'" + "' - Successfull".toUpperCase(), "");
				} else
					CustomReporter.LogEvent(TestStatus.WARNING, "Uncheck Checkbox '" + sTemp[2] + "'",
							"Checkbox '" + sTemp[2] + "' has already been unchecked", "");
			} catch (Exception e) {
				CustomReporter.catchException(e, "Uncheck the checkbox '" + sTemp[2] + "'");
			}
		} else {
			CustomReporter.LogEvent(TestStatus.FAIL, "Check / Uncheck - Check Box", "Wrong Value Passed As Parameter",
					"");
		}
		sTemp = null;
		status = null;
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will select the radio button passed to it
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 */
	public static void radioButton(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (!we.isSelected()) {
				if (highlightObj.equalsIgnoreCase("on")) {
					if ((props.getProperty("platform").contains("desktop")))
						highlightElement(we);

				}
				we.click();
				CustomReporter.LogEvent(TestStatus.PASS, "Select Radio Button '" + sTemp[2] + "'",
						"Select Radio Button '" + sTemp[2] + "'" + " - Successfull".toUpperCase(), "");
			} else {
				CustomReporter.LogEvent(TestStatus.WARNING, "Select the Radio Button '" + sTemp[2] + "'",
						"Radio Button '" + sTemp[2] + "' has already been selected", "");
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Select Radio Button '" + sTemp[2] + "'");
		}
		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will select an item from dropdown list<br>
	 * Selection can be done in 3 ways:<br>
	 * 1. by index, <br>
	 * 2. by value and <br>
	 * 3. by visible text
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @param Value
	 * @param Identifiers
	 */
	public static void listBoxSelect(String TestObject, String Value, String Identifiers) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Value.startsWith("#")) {
			String[] nValue = Value.split("#");
			Index = Integer.parseInt(nValue[1]);
		}
		SeleniumIdentifiers Identifier = SeleniumIdentifiers.valueOf(Identifiers);
		String[] sTemp = splitTestObject(TestObject);
		try {
			Select select = new Select(checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]));
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);

			switch (Identifier) {
			case byIndex: {
				String sTemp1 = null;
				try {
					select.selectByIndex(Index);
					if (highlightObj.equalsIgnoreCase("on")) {

						if (props.getProperty("platform").contains("desktop"))
							highlightElement(we);
					}
					sTemp1 = select.getOptions().get(Index).getText();
					CustomReporter.LogEvent(
							TestStatus.PASS, "Select '" + sTemp1 + "' from dropdown '" + sTemp[2] + "'", "Select '"
									+ sTemp1 + "' from dropdown '" + sTemp[2] + "'" + "' - Successfull".toUpperCase(),
							"");
				} catch (Exception e) {
					CustomReporter.catchException(e, "Select '" + sTemp1 + "' from dropdown '" + sTemp[2] + "'");
				}
				break;
			}
			case byValue: {
				try {
					select.selectByValue(Value);
					if (highlightObj.equalsIgnoreCase("on")) {
						if (props.getProperty("platform").contains("desktop"))
							highlightElement(we);
					}
					CustomReporter.LogEvent(TestStatus.PASS, "Select '" + Value + "' from dropdown '" + sTemp[2] + "'",
							"Select '" + Value + "' from dropdown '" + sTemp[2] + "'" + "' - Successfull".toUpperCase(),
							"");
				} catch (Exception e) {
					CustomReporter.catchException(e, "Select '" + Value + "' from dropdown '" + sTemp[2] + "'");
				}
				break;
			}
			case byVisibleText: {
				try {
					select.selectByVisibleText(Value);
					if (highlightObj.equalsIgnoreCase("on")) {
						if (props.getProperty("platform").contains("desktop"))
							highlightElement(we);
					}
					CustomReporter.LogEvent(TestStatus.PASS,
							"Select '" + Value.toUpperCase() + "' from dropdown List '" + sTemp[2] + "'",
							"Select '" + Value.toUpperCase() + "' from dropdown List '" + sTemp[2]
									+ "' - Successfull".toUpperCase(),
							"");
				} catch (Exception e) {
					CustomReporter.catchException(e, "Select '" + Value + "' from dropdown '" + sTemp[2] + "'");
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Select '" + Value + "' from dropdown '" + sTemp[2] + "'");
		}
		sTemp = null;
		Value = null;
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will handle(accept/dismiss) alert pop-up based on action
	 * passed as parameter
	 * 
	 * @author LavaKumar
	 * @param Action
	 */
	public static void Alert(String Action) {
		CustomReporter.writeLog(new Exception().getStackTrace()[0].getMethodName().toString());
		if (Action.equalsIgnoreCase("accept")) {
			try {
				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
						CustomReporter.LogEvent(TestStatus.FAIL, "Check Alert Present",
								"No Alert Message Displayed After 60 seconds", "");
					}
					try {
						if (isAlertPresent())
							break;
					} catch (Exception e) {
					}
					Keywords.wait(1);
				}
				String alertText = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();
				CustomReporter.LogEvent(TestStatus.PASS, "Accept Alert",
						"Accept Alert with Message '" + alertText + "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				CustomReporter.LogEvent(TestStatus.FAIL, "Accept Alert", "No Alert message present", "");
			}
		} else {
			try {
				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
						CustomReporter.LogEvent(TestStatus.FAIL, "Check Alert Present",
								"No Alert Message Displayed After 60 seconds", "");
					}
					try {
						if (isAlertPresent())
							break;
					} catch (Exception e) {
					}
					Keywords.wait(1);
				}
				String sTemp = driver.switchTo().alert().getText();
				driver.switchTo().alert().dismiss();
				CustomReporter.LogEvent(TestStatus.PASS, "Dismiss Alert",
						"Dismiss Alert with Message '" + sTemp + "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				CustomReporter.LogEvent(TestStatus.FAIL, "Dismiss Alert", "No Alert message present", "");
			}
		}
		Action = null;
	}

	/**
	 * This method will mouse hover on to the specified WebElement
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 */
	public static void mouseOver(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (highlightObj.equalsIgnoreCase("on")) {
				if (props.getProperty("platform").contains("desktop"))
					highlightElement(we);
			}
			actions.moveToElement(checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3])).build().perform();
			CustomReporter.LogEvent(TestStatus.PASS, "Mouse Over '" + sTemp[2] + "'",
					"Mouse Over '" + sTemp[2] + "' - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			CustomReporter.catchException(e, "Move Over '" + sTemp[2] + "'");
		}
		sTemp = null;
	}

	/**
	 * This method will highlight WebElement passed as parameter
	 * 
	 * @author LavaKumar
	 * @param element
	 */
	public static void highlightElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String bgcolor = "rgb(255,255,0)";
			js.executeScript("arguments[0].style.backgroundColor = '" + bgcolor + "'", element);
		}
	}

	/**
	 * This method will verify text present on specified WebElement
	 * 
	 * @author Vidyasagar Mada
	 * @param expectedText
	 * @param TestObject
	 */
	public static void verifyText(String TestObject, String expectedText) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(TestObject);
		WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
		String actualText = we.getText().trim();
		;
		try {
			if (actualText.equals(expectedText))
				CustomReporter.LogEvent(TestStatus.PASS, "Expected text: '" + expectedText + "'",
						"Actual text: '" + actualText + "'", "");
			else
				CustomReporter.LogEvent(TestStatus.FAIL, "Expected text: '" + expectedText + "'",
						"Actual text: '" + actualText + "'", "");
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Exception occured while verifying text" + expectedText, "",
					"");
		}
	}

	/**
	 * This method will wait for specified Object to appear/disappear
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @param iTimeout
	 * @param action
	 */
	public static void waitForObject(String TestObject, int iTimeout, String action) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		CustomReporter.writeLog(methodName);
		String[] sTemp = splitTestObject(TestObject);
		WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
		if (action.equalsIgnoreCase("appear")) {
			for (int second = 0;; second++) {
				if (second >= iTimeout)
					fail("Timeout - Progress Bar Still Exists After " + iTimeout + " seconds");
				CustomReporter.LogEvent(TestStatus.FAIL, "",
						"Timeout Error - Progress Bar Still Exists After " + iTimeout, "");
				try {
					if (we.isDisplayed())
						break;
				} catch (Exception e) {
				}
				Keywords.wait(1);
			}

		} else if (action.equalsIgnoreCase("disappear")) {
			for (int second = 0;; second++) {
				if (second >= iTimeout)
					fail("Timeout - Progress Bar Still Exists After " + iTimeout + " seconds");
				try {
					if (!we.isDisplayed())
						break;
				} catch (Exception e) {
				}
				Keywords.wait(1);
			}
		} else {
			CustomReporter.LogEvent(TestStatus.FAIL, "Wait For Object To - " + action,
					"Check The *Action* Value Passed As Parameter In The Method - " + methodName, "");
		}
	}

	/**
	 * This method will hand over window control to pop-up
	 * 
	 * @author LavaKumar
	 */
	public static void switchToPopup() {

		try {
			sHandleBefore = driver.getWindowHandle();
			for (String Handle : driver.getWindowHandles()) {
				driver.switchTo().window(Handle);
			}
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "No PopUp Window Exits '", "No PopUp Window Exits '", "");
		}
		CustomReporter.LogEvent(TestStatus.PASS, "Switch To PopUp Window is Passed '",
				"Switch To PopUp Window is passed'", "");
	}

	/**
	 * This method will get the window handle back to original window
	 * 
	 * @author LavaKumar
	 * 
	 */
	public static void switchToNormal() {
		try {
			driver.switchTo().window(sHandleBefore);
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Switch to Normal window is Failed '",
					"Switch to Normal window is Failed '", "");
		}
		CustomReporter.LogEvent(TestStatus.PASS, "Switch to Normal window is Passed '",
				"Switch to Normal window is Passed '", "");
	}

	/**
	 * This method returns a boolean value based on the alert existence
	 * 
	 * @author LavaKumar
	 * @return boolean
	 */
	private static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			String alertText = driver.switchTo().alert().getText();
			CustomReporter.LogEvent(TestStatus.MSG, "Alert - '" + alertText + "' is Displayed", "", "");
			return true;
		} catch (NoAlertPresentException e) {
			CustomReporter.LogEvent(TestStatus.MSG, "Alert is NOT Displayed", "", "");
			return false;
		}
	}

	/**
	 * This method will verify text presence based on attribute passed to it
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @param Text
	 * @param attribute
	 */
	public static void verifyTextPresent(String TestObject, String Text, String attribute) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String stemp = null;
		String[] sTemp = splitTestObject(TestObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			stemp = we.getAttribute(attribute).trim().toString();
			if (!(stemp == null) && sTemp.length > 0) {
				if (Text.equalsIgnoreCase(stemp)) {
					CustomReporter.LogEvent(TestStatus.PASS, "Verify Text '" + Text + "' Present",
							"Text '" + Text + "' is Displayed", "");
				} else
					CustomReporter.LogEvent(TestStatus.FAIL, "Verify Text '" + Text + "' Not Present",
							"Text '" + Text + "' is Not Displayed", "");
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Verify Text'" + Text + "' Present");
		}
		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will extract text from specified Test Object
	 * 
	 * @author LavaKumar
	 * @param testObject
	 * @return textAssociated
	 */
	public static String getText(String testObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String textAssociated = null;
		String[] sTemp = splitTestObject(testObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			textAssociated = we.getText().toString().trim();
		} catch (Exception e) {
			CustomReporter.catchException(e, "Unable to get text with objectlocator " + testObject);
		}
		sTemp = null;
		Runtime.getRuntime().gc();
		return textAssociated;
	}

	/**
	 * This method will extract text from specified Test Object based on
	 * attribute provided
	 * 
	 * @author LavaKumar
	 * @param testObject
	 * @param attribute
	 * @return textAssociated
	 */
	public static String getText(String testObject, String attribute) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String textAssociated = null;
		String[] sTemp = splitTestObject(testObject);
		try {
			WebElement we = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			textAssociated = we.getAttribute(attribute).trim().toString();
		} catch (Exception e) {
			CustomReporter.catchException(e, "Unable to get text with objectlocator " + testObject);
		}
		sTemp = null;
		Runtime.getRuntime().gc();
		return textAssociated;
	}

	/**
	 * This method will switch window handle to specified frame
	 * 
	 * @author LavaKumar
	 * @param testObject
	 */
	public static void switchToIframe(String testObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = splitTestObject(testObject);
		try {
			WebElement iframeLocator = checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			driver.switchTo().frame(iframeLocator);
		} catch (Exception e) {
			CustomReporter.catchException(e, "Unable to locate IFrame window " + testObject);
		}
		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/**
	 * This method will switch window handle back to main frame
	 * 
	 * @author LavaKumar
	 */
	public static void switchToDefaultFromIframe() {
		driver.switchTo().defaultContent();
	}

	/**
	 * This method will return true/false based on whether a WebElement is
	 * selected or not
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @return bStatus
	 */
	public static boolean isSelected(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = Keywords.splitTestObject(TestObject);
		boolean bStatus = false;
		try {
			WebElement we = Keywords.checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (we.isSelected()) {
				CustomReporter.LogEvent(TestStatus.PASS, "Element '" + sTemp[2] + "'",
						"Element '" + sTemp[2] + "'" + " is Enabled".toUpperCase(), "");
				bStatus = true;
			} else if (!we.isSelected()) {
				CustomReporter.LogEvent(TestStatus.FAIL, "Element '" + sTemp[2] + "'",
						"Element '" + sTemp[2] + "'" + " is Disabled".toUpperCase(), "");
				bStatus = false;
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Element '" + sTemp[2] + "'");
		}
		sTemp = null;
		return bStatus;
	}

	/**
	 * This method will return whether specified WebElement is displayed or not
	 * 
	 * @author LavaKumar
	 * @param TestObject
	 * @return status
	 */
	public static boolean verifyElementDisplayed(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = Keywords.splitTestObject(TestObject);
		try {
			WebElement we = Keywords.checkElement(sTemp[0], sTemp[1], sTemp[2], sTemp[3]);
			if (we.isDisplayed()) {

				CustomReporter.LogEvent(TestStatus.PASS, "Element '" + sTemp[2] + "'",
						"Element '" + sTemp[2] + "'" + " displayed Successfull".toUpperCase(), "");
				return true;
			} else {
				CustomReporter.LogEvent(TestStatus.FAIL, "Element '" + sTemp[2] + "'",
						"Element '" + sTemp[2] + "'" + " not displayed".toUpperCase(), "");
				return false;
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Element '" + sTemp[2] + "'");
		}
		sTemp = null;
		return false;
	}

	/**
	 * This method will verify specified text is present or not in entire web
	 * page
	 * 
	 * @author Lava Kumar
	 * @param text
	 */
	public static void isTextPresent(String text) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			boolean b = Keywords.driver.getPageSource().contains(text);
			if (b) {
				CustomReporter.LogEvent(TestStatus.PASS, "Verify Text '" + text + "' present",
						"Text '" + text + "' is displayed".toUpperCase(), "");
			}
		} catch (Exception e) {
			CustomReporter.catchException(e, "Text '" + text + "'");
		}
	}

	/**
	 * This method will perform upward swipe action in Android device
	 * 
	 * @author Vidyasagar Mada
	 */
	public static void andriodSwipeUp() {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		// int Y = driver.manage().window().getSize().getHeight(); // Y = 568
		// int X = driver.manage().window().getSize().getWidth(); // X = 320
		// System.out.println("Width" + X);//1080
		// System.out.println("Height" + Y);//1035
		int duration = 1200;
		//((AppiumDriver) driver).swipe(540, 1000, 540, 300, duration);
		try {
			CustomReporter.LogEvent(TestStatus.PASS, "Swiping up from (540,1000) to (540,300))",
					"Swiped up from (540,1000) to (540,300))", "");
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Application should be closed", "Application is closed", "");
		}
	}

	/**
	 * This method will perform upward swipe action until specified element
	 * displayed
	 * 
	 * @author Vidyasagar Mada
	 * @param TestObject
	 */
	public static void andriodSwipeUpTillVisible(String TestObject) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] sTemp = Keywords.splitTestObject(TestObject);
		WebElement we = driver.findElement(By.xpath(sTemp[3]));
		int duration = 1200;
		try {
			while (!we.isDisplayed())
				//((AppiumDriver) driver).swipe(540, 1000, 540, 300, duration);
			CustomReporter.LogEvent(TestStatus.PASS, "Swiping up from (540,1000) to (540,300))",
					"Swiped up from (540,1000) to (540,300))", "");
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Swipe up gesture need to be performed successfully",
					"Swipe up gesture is not successfully performed", "");
		}
	}

	/**
	 * This method will perform right to left swipe gesture on android device
	 * 
	 * @author Vidyasagar Mada
	 */
	public static void andriodSwipeRightToLeft() {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		int duration = 1200;
		//((AppiumDriver) driver).swipe(1000, 1000, 0, 1000, duration);
		try {
			CustomReporter.LogEvent(TestStatus.PASS, "Swiping Left to Right from (1000,1000) to (0,1000))",
					"Swiped Left to Right from (1000,1000) to (0,1000))", "");
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Application should be closed", "Application is closed", "");
		}
	}

	/**
	 * This method will perform left to right swipe gesture on android device
	 * 
	 * @author Vidyasagar Mada
	 */
	public static void andriodSwipeLefttoRight() {
		int duration = 1200;
		//((AppiumDriver) driver).swipe(85, 1000, 1000, 1000, duration);
		try {
			CustomReporter.LogEvent(TestStatus.PASS, "Swiping Left to Right from (0,1000) to (1000,1000))",
					"Swiped Left to Right from (0,1000) to (1000,1000))", "");
		} catch (Exception e) {
			CustomReporter.LogEvent(TestStatus.FAIL, "Application should be closed", "Application is closed", "");
		}
	}

	/**
	 * This method will perform swipe up gesture on iOS device
	 * 
	 * @author Vidyasagar Mada
	 */
	public static void iOSSwipeUp() {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			// This is important for TouchAction
			((AppiumDriver) driver).context("NATIVE_APP");
			TouchAction action = new TouchAction((AppiumDriver) driver);
		/*	action.press(250, 500);
			action.waitAction(500); // has to be >= 500 otherwise it will fail
			action.moveTo(250, 100);*/
			action.release();
			action.perform();
			// Now change back the context
			// ((AppiumDriver) driver).context("WEBVIEW_1");
			// It is important to sleep for some time
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			CustomReporter.catchException(e, "Catched Exception");
		}
	}

	/**
	 * This method will scroll down through webpage
	 * 
	 * @author Vidyasagar Mada
	 * 
	 */
	public static void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 150)");
	}

	/**
	 * 
	 * This method will scroll up through webpage
	 * @author Vidyasagar Mada
	 * 
	 */
	public static void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(150, 0)");
	}

	/*
	 * Post MIAF-0.1 release start public static void BrowserNavigateBack() {
	 * driver.navigate().back(); } Post MIAF-0.1 release end
	 */
	/**
	 * This method will validate expected URL with the current webpage URL
	 * 
	 * @author Vidyasagar Mada
	 * @param urlToBeVerified
	 */
	public static void verifyURL(String urlToBeVerified) {
		if (driver.getCurrentUrl().equals(urlToBeVerified))
			CustomReporter.LogEvent(TestStatus.PASS, urlToBeVerified + " need to be displayed",
					urlToBeVerified + " is displayed", "");
		else
			CustomReporter.LogEvent(TestStatus.FAIL, urlToBeVerified + " need to be displayed",
					urlToBeVerified + " is not displayed", "");
	}

	// Post MIAF-0.1 release start
	/**
	 * This method will return WebDriver instance
	 * 
	 * @author Vidyasagar Mada
	 * @return driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * This method will return date as per specified format
	 * 
	 * @param expectedformat
	 * @return formattedDate
	 */
	public static String formatDate(String expectedformat) {
		String formattedDate = new SimpleDateFormat(expectedformat).format(new Date());
		return formattedDate;
	}
	/**
	 * This method will wait for specified amount of time in Seconds
	 * 
	 * @param timeInSeconds
	 */
	public static void wait(int timeInSeconds) {
		CustomReporter.writeLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		@SuppressWarnings("unused")
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
	}
	// Post MIAF-0.1 release end
}