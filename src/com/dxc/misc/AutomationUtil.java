package com.dxc.misc;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AutomationUtil {
	WebDriver driver=new FirefoxDriver();	
	
	
	public void startSession() throws InterruptedException {
		driver.get("http://localhost:5555/wd/hub/static/resource/hub.html");
		driver.findElement(By.cssSelector(".control-block>button:nth-child(1)"))
				.click();
		Select selectBrowser =
				new Select(driver.findElement(
						By.cssSelector(".modal-dialog-content>label>select")));
		selectBrowser.selectByVisibleText("firefox");
		driver.findElement(By.cssSelector(".goog-buttonset-default")).click();
		WebElement sessionId =
				driver.findElement(
						By.cssSelector(".control-block>span:nth-child(1)"));
		driver.wait();
		StringSelection selection =
				new StringSelection("webdriver.remote.session=" + sessionId.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		driver.close();
	}
}
