package com.orangehrm.test;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.Pages.HomePage;
import com.orangehrm.Pages.LoginPage;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;




public class Login extends BaseClass {
	
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	
	  @BeforeMethod 
	  public void setupPages()
	  { loginPage = new LoginPage(getDriver());
	  homePage = new HomePage(getDriver()); 
	  }
	
	
	
	@Test
	public void getTitle()
	{
		
		log.info("Login-getMethod");
	//	ExtentManager.startTest("Valid Login Test");
		String title=getDriver().getTitle();
		loginPage.login("Admin", "admin123");
	//Assert.assertEquals(title, "OrangeHRM","actual is matched with Expected");	
	Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successfull login ");
	log.info("got title");
	}
	
	@Test(dataProvider="validLoginData", dataProviderClass=DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		log.info("Login-VerifyValidLogin");
	//	ExtentManager.startTest("Valid Login Test");// --This has been implemented in TestListener
		//System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifying Admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successfull login ");
		ExtentManager.logStep("Validation Successful");
	homePage.logout();
	ExtentManager.logStep("Logged out Successfully!");
		//staticWait(2);
	}
	
	@Test(dataProvider="inValidLoginData", dataProviderClass=DataProviders.class)
	public void inValidLoginTest(String username, String password){
		log.info("Login-InvalidMethod");
	//	ExtentManager.startTest("In-valid Login Test!"); //This has been implemented in TestListener
		//System.out.println("Running testMethod2 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login(username,password);
		String expectedErrorMessage = "valid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),"Test Failed: Invalid error message");
	//	ExtentManager.logStep("Validation Successful");
	}
}

