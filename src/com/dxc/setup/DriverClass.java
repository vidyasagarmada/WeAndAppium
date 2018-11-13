package com.dxc.setup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import com.dxc.util.BaseClass;
import com.dxc.util.BaseClass.TestStatus;
import com.dxc.util.BaseClass.updateValue;
import com.dxc.util.CmdUtil;
import com.dxc.util.CustomReporter;
import com.dxc.util.Keywords;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * This class will create required driver instance
 * 
 * @author Vidyasagar Mada
 */
public class DriverClass {

	public static String scriptName = null;
	Keywords CM = null;
	String browserName = null;
	public static WebDriver driver;
	String Env = BaseClass.props.getProperty("environment");
	DesiredCapabilities capabilities = new DesiredCapabilities();

	/**
	 * This method will <br>
	 * 1. Launch firefox browser and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void winFFDriver() {
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir") + "\\driverservers\\geckodriver.exe");
		SetUpTearDown.driver = new FirefoxDriver();
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		Capabilities caps = ((RemoteWebDriver) SetUpTearDown.driver).getCapabilities();
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Kill existing IDDriverserver instance <br>
	 * 2.Launch Internet explorer browser and<br>
	 * 3. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void winIEDriver() {
		CmdUtil.executeCommand("taskkill /F /IM IEDriverServer.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		System.setProperty("webdriver.ie.driver",
				System.getProperty("user.dir") + "\\driverservers\\IEDriverServer.exe");
		SetUpTearDown.driver = new InternetExplorerDriver(caps);
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Launch chrome browser and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void winchromeDriver(ITestContext ic) {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\driverservers\\chromedriver.exe");
		SetUpTearDown.driver = new ChromeDriver();
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Launch Android browser on specified Android Device and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void andBrowserDriver() {
		capabilities.setCapability(CapabilityType.BROWSER_NAME, BaseClass.props.getProperty("browser.android"));
		capabilities.setCapability("deviceName", BaseClass.props.getProperty("deviceName.android"));
		capabilities.setCapability("platformVersion", BaseClass.props.getProperty("platformVersion.android"));
		capabilities.setCapability("platformName", BaseClass.props.getProperty("platformName.android"));
		try {
			SetUpTearDown.driver = new AndroidDriver(new URL(BaseClass.props.getProperty("appium.url") + "/wd/hub/"),
					capabilities);
		} catch (MalformedURLException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Appium is running",
					"Either appium is not running or Server port addresses are not matching.Check appium settings", "");
			e.printStackTrace();
		}
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		Capabilities caps = ((RemoteWebDriver) SetUpTearDown.driver).getCapabilities();
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Launch specified Android app on specified Android Device and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void andApp(ITestContext ic) {
		capabilities.setCapability("deviceName", BaseClass.props.getProperty("deviceName.android"));
		capabilities.setCapability("platformVersion", BaseClass.props.getProperty("platformVersion.android"));
		capabilities.setCapability("platformName", BaseClass.props.getProperty("platformName.android"));
		capabilities.setCapability("appPackage", BaseClass.props.getProperty("appPackage.android"));
		capabilities.setCapability("appActivity", BaseClass.props.getProperty("appActivity.android"));
		try {
			SetUpTearDown.driver = new AndroidDriver(new URL(BaseClass.props.getProperty("appium.url") + "/wd/hub/"),
					capabilities);
		} catch (MalformedURLException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Appium is running",
					"Either appium is not running or Server port addresses are not matching.Check appium settings", "");
			e.printStackTrace();
		}
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		Capabilities caps = ((RemoteWebDriver) SetUpTearDown.driver).getCapabilities();
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Launch safari browser on specified iOS Device and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void iOSWebDriver(ITestContext ic) {
		capabilities.setCapability("deviceName", BaseClass.props.getProperty("deviceName.ios"));
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", BaseClass.props.getProperty("platformVersion.ios"));
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
		try {
			SetUpTearDown.driver = new IOSDriver(new URL(BaseClass.props.getProperty("appium.url") + "/wd/hub"),
					capabilities);
		} catch (MalformedURLException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Appium is running",
					"Either appium is not running or Server port addresses are not matching.Check appium settings", "");
			e.printStackTrace();
		}
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		Capabilities caps = ((IOSDriver) driver).getCapabilities();
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}

	/**
	 * This method will <br>
	 * 1. Launch specified iOS on specified iOS Device and<br>
	 * 2. Update the Reports accordingly
	 * 
	 * @author Vidyasagar Mada
	 */
	void iOSAppDriver(ITestContext ic) {
		capabilities.setCapability("deviceName", BaseClass.props.getProperty("deviceName.ios"));
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", BaseClass.props.getProperty("platformVersion.ios"));
		// UDID is mandatory for real device
		// capabilities.setCapability("udid",
		// "2cba98c5be7c96c4af2157c248bfd5040aaea2f6");
		// bundle id is not mandatory for simulator
		// capabilities.setCapability("bundleId", "de.tui.meinetui.dev");
		// for Simulator, give absolute path for appbuild available in local
		// machine
		// capabilities.setCapability("ipa", "iosdephone.ipa");
		try {
			SetUpTearDown.driver = new IOSDriver(new URL(BaseClass.props.getProperty("appium.url") + "/wd/hub"),
					capabilities);
		} catch (MalformedURLException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Appium is running",
					"Either appium is not running or Server port addresses are not matching.Check appium settings", "");
			e.printStackTrace();
		}
		// CREATE AN INSTANCE OF Keywords
		CM = new Keywords(SetUpTearDown.driver, "off");
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		try {
			CustomReporter.updateReports(updateValue.bName,
					caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString(), "");
			browserName = caps.getBrowserName().toString().toUpperCase() + " / " + caps.getVersion().toString();
			CustomReporter.updateReports(updateValue.Env, Env, "");
		} catch (IOException e) {
			CustomReporter.LogEvent(TestStatus.WARNING, "Browser name or environment need to be provided",
					"Browser name or environment are not provided", "");
			e.printStackTrace();
		}
	}
}