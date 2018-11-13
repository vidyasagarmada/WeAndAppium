package com.dxc.scripts;

import org.testng.annotations.Test;

import com.dxc.BusinessComponents.BusinessComponents;
import com.dxc.util.BaseClass;
import com.dxc.util.CustomReporter;
import com.dxc.util.Keywords;

public class MyRTATest extends BaseClass {

	BusinessComponents businessComponents = new BusinessComponents();
	String resNum = null;

	@Test
	public void myRTANavigation() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		System.out.println("Executing test script " + methodName);
		// Launching MyRTA Home page URL
		Keywords.launchBrowser(getTestData("TD_ACC_URL"));

		Keywords.wait(3);
		// verifying home page
		Keywords.verifyText(getTestObject("HP_RNR_LABEL"),
				getTestData("TD_ACC_URL"));
		// Verifying presence and functionality of About Us link

		Keywords.clickElement(getTestObject("HP_ABOUT_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"),
				"About online services");
		Keywords.clickElement(getTestObject("HP_HOME_LINK"));
		Keywords.wait(3); // verifying home page
		Keywords.verifyText(getTestObject("HP_RNR_LABEL"),
				"Renew registration");
		// Verifying presence and functionality of Contact Us link
		Keywords.clickElement(getTestObject("HP_CONTACT_LINK"));
		Keywords.wait(3);
		Keywords.verifyURL(getTestData("CONTACT_URL"));
		Keywords.getDriver().navigate().back();
		Keywords.wait(5);
		// Verifying presence and functionality of Roads and Maritime link
		Keywords.clickElement(getTestObject("HP_RNM_LINK"));
		Keywords.wait(3);
		Keywords.verifyURL(getTestData("RNM_URL"));
		Keywords.getDriver().navigate().back();
		Keywords.wait(3);
		// Verifying presence and functionality of Service NSW link
		Keywords.clickElement(getTestObject("HP_SNSW_LINK"));
		Keywords.wait(3);
		Keywords.verifyURL(getTestData("SNSW_URL"));
		Keywords.getDriver().navigate().back();
		Keywords.wait(3);
		// Verifying Registration page
		Keywords.clickElement(getTestObject("HP_REGO_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"), "Registration");

		// Verifying License page
		Keywords.clickElement(getTestObject("HP_LIC_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"), "LICENCE");

		// Verifying Your Details page
		Keywords.clickElement(getTestObject("HP_YOURDETAILS_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"), "YOUR DETAILS");

		// Verifying Resources Tools and Forms page
		Keywords.clickElement(getTestObject("HP_RTF_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"),
				"RESOURCES, TOOLS & FORMS");

		// Verifying E-TOLL page
		Keywords.clickElement(getTestObject("HP_E-TOLL_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(getTestObject("TOLL_PAGE_VERIFY"),
				getTestData("TOLL_VERIFY_TXT"));

		// Verifying Heavy Vehicles page
		Keywords.clickElement(getTestObject("HP_HV_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"), "HEAVY VEHICLES");

		// Verifying Heavy Vehicles page
		Keywords.clickElement(getTestObject("HP_BP_LINK"));
		Keywords.wait(3);
		Keywords.verifyText(
				getTestObject("HP_INNER_PAGE_HEADER_LABEL"),
				"BUSINESS PARTNERS");
		CustomReporter.LogEvent(TestStatus.EOM, "", "", "");
	}

	@Test
	public void billNumberError() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		System.out.println("Executing test script " + methodName);
		// Launching MyRTA Home page URL
		Keywords.launchBrowser(getTestData("TD_ACC_URL"));
		Keywords.wait(3);
		// verifying home page
		Keywords.verifyText(getTestObject("HP_RNR_LABEL"),
				"Renew registration");
		Keywords.editAField(getTestObject("HP_RNR_TXTBOX"),
				getTestData("RNR_TEXT"));
		Keywords.clickElement(getTestObject("HP_RNR_CONTINUE"));
		Keywords.verifyText(getTestObject("HP_RNR_ERR_MSG"),
				getTestData("ERR_MSG"));
		CustomReporter.LogEvent(TestStatus.EOM, "", "", "");
	}

	@Test
	public void signup_Failure() throws Exception {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		System.out.println("Executing test script " + methodName);
		// Launching MyRTA Home page URL
		Keywords.launchBrowser(getTestData("TD_ACC_URL"));
		Keywords.wait(3);
		// verifying home page
		Keywords.verifyText(getTestObject("HP_RNR_LABEL"),
				"Renew registration");
		Keywords.clickElement(getTestObject("HP_SIGNUP_BTN"));
		Keywords.wait(3);
		Keywords.editAField(getTestObject("SUP_FNAME"),
				getTestData("FNAME"));
		Keywords.editAField(getTestObject("SUP_PHOTOCARD_NO"),
				getTestData("PHOTOCARD_NO"));
		Keywords.editAField(getTestObject("SUP_CID_NO"),
				getTestData("CID_NO"));
		Keywords.verifyText(getTestObject("HP_RNR_ERR_MSG"),
				getTestData("SUP_ERR_MSG"));
		CustomReporter.LogEvent(TestStatus.EOM, "", "", "");
	}
}