package com.dxc.misc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dxc.util.Keywords;

public class PTEX {
	DesiredCapabilities capabilities = new DesiredCapabilities();

	AppiumDriver driver;
	String MY_APPIUM_URL;

	@Parameters({ "PARAMETER_DEVICE_ID", "PARAMETER_APPIUM_URL",
			"PARAMETER_UDID", "PARAMETER_PLATFORM_NAME",
			"PARAMETER_BROWSER_NAME" })
	@BeforeTest
	public void setupCapabilities(String PARAMETER_DEVICE_ID,
			String PARAMETER_APPIUM_URL, String PARAMETER_UDID,
			String PARAMETER_PLATFORM_NAME, String PARAMETER_BROWSER_NAME)
			throws Exception {

		MY_APPIUM_URL = PARAMETER_APPIUM_URL + "/wd/hub";
		capabilities.setCapability(CapabilityType.BROWSER_NAME,
				PARAMETER_BROWSER_NAME);

		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", PARAMETER_PLATFORM_NAME);
		capabilities.setCapability("deviceName", PARAMETER_DEVICE_ID);
		// driver = new AndroidDriver(new URL(MY_APPIUM_URL), capabilities);

		if (PARAMETER_PLATFORM_NAME.contains("ANDROID")) {
			capabilities.setCapability("udid", PARAMETER_UDID);

			driver = new AndroidDriver(new URL(MY_APPIUM_URL), capabilities);
		}

		else
			driver = new IOSDriver(new URL(MY_APPIUM_URL), capabilities);

		// driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

	}

	@Parameters({ "PARAMETER_APPIUM_URL" })
	@Test
	public void dial(String PARAMETER_APPIUM_URL) throws Exception {
		System.out.println("I am in Test");
		if (PARAMETER_APPIUM_URL.contains("http://192.168.113.140:4729")) {
			driver.get("https://m.starwoodhotels.com/preferredguest/index.html");
			Reporter.log("Launching SPG URL in English on Android device1");
		} else if (PARAMETER_APPIUM_URL.contains("http://192.168.113.140:4725")) {
			driver.get("http://m.starwoodhotels.com/preferredguest/index.html?skinName=preferredguest&language=es_ES&localeCode=es_ES");
			Reporter.log("Launching SPG URL in Spanish on Android device2");
		} else {
			driver.get("http://m.starwoodhotels.com/preferredguest/index.html?skinName=preferredguest&language=it_IT&localeCode=it_IT");
			Reporter.log("Launching SPG URL in Italian on iOS device");
		}
		Keywords.wait(8);
		driver.findElement(By.name("complexSearchField")).clear();
		driver.findElement(By.name("complexSearchField")).sendKeys(
				"The Westin Hyderabad Mindspace");
		Reporter.log("Searching for hotel Westin Hyderabad");
		driver.findElement(
				By.xpath("//form[@id='regionSearchForm']/div/ul/li[2]/a"))
				.click();
		driver.findElement(By.xpath("//tr[5]/td[3]")).click();
		driver.findElement(By.xpath("//tr[5]/td[4]")).click();
		Keywords.wait(5);
		driver.findElement(By.cssSelector("button.active")).click();
		Keywords.wait(8);
		String HotelName = driver.findElement(
				By.cssSelector("h1.searchResultsPropertyName")).getText();
		System.out.println("HotelName : " + HotelName);
		Reporter.log("HotelName : " + HotelName);
		if (HotelName.equalsIgnoreCase("The Westin Hyderabad Mindspace")) {
			System.out.println("Execution is Pass");
			Reporter.log("Execution is Pass");
		} else {
			System.out.println("Execution is Fail");
			Reporter.log("Execution is Fail");
		}
	}

	public void googleTest() throws Exception {
		System.out.println("I am in Test");
		driver.get("https://m.google.com");
		Keywords.wait(8);
	}

	@AfterTest
	public void TearDown() {
		driver.quit();
	}
}