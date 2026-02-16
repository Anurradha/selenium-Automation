package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.Pages.HomePage;
import com.orangehrm.Pages.LoginPage;
import com.orangehrm.base.BaseClass;
import com.orangehrm.listeners.TestListener;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage  = new HomePage(getDriver());
	}
	@Test
	public void verifyOrangeHRMLogo() {
		log.info("HomepageTest");
		ExtentManager.startTest("Home Page Verify Logo Test"); //--This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying Logo is visible or not");
     Assert.assertTrue(homePage.verifyOrangeHRMlogo(),"Logo is not visible");
}}
