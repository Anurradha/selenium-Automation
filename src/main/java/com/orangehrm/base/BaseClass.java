package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	//protected static  WebDriver driver;
	protected static Properties prop ;
	private FileInputStream fis;
	//private static ActionDriver actiondriver;
	public static final Logger log = LoggerManager.getLogger(BaseClass.class);
	private static ThreadLocal<WebDriver> driver= new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actiondriver=new ThreadLocal<>();
	
	
	@BeforeSuite
	public void loadConfig() throws IOException {
		 prop = new Properties();
		String path = System.getProperty("user.dir") + "/resources/config.properties";
		 fis = new FileInputStream(path);
		prop.load(fis);
		log.info("properties file loaded");
	//	ExtentManager.getReporter();
		if (prop == null) {
		    throw new RuntimeException("Properties not loaded");
		}
		WebDriverManager.chromedriver().setup();
	}

	 public Properties getProperties()

	 {
		return prop;
		 
	 }
	 
	 
	 @BeforeMethod(alwaysRun = true)
	public synchronized void setup() throws Exception {
Thread.sleep(5000);
		/*
		 * Properties p = new Properties();
		 * 
		 * FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+
		 * "/resources/config.properties"); p.load(fis);
		 */
		 
		String browser = prop.getProperty("browser");
		//System.out.println(prop.getProperty("username"));

		if (browser.equals("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
		} else if (browser.equals("edge")) {
			//driver = new EdgeDriver();
			
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
		}

		else if (browser.equals("firefox")) {
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
}
		log.info("Browser Launched"+Thread.currentThread().getId());
     getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		actiondriver.set(new ActionDriver(driver.get()));
		}
	 
	 
		
@AfterMethod
	public void tearDown() {
	 try {
	        WebDriver drv = driver.get();   // NOT getDriver()
	        if (drv != null) {
	            drv.quit();
	        }
	    } catch (Exception e) {
	        System.out.println("Teardown issue: " + e.getMessage());
	    } finally {
	        driver.remove();
	        actiondriver.remove();
	    }
	// driver = null;
	// actionDriver = null;
	// ExtentManager.endTest(); --This has been implemented in TestListener
}

	



@AfterSuite
public void close() throws IOException
{ 
	
	fis.close();
//	ExtentManager.endTest();
	}
public static WebDriver getDriver()
{
	 if(driver.get()==null)
	 {
		System.out.println("driver not initialized");
		throw new IllegalStateException("browser not initialised");
	 }
	 
	return driver.get();
	 
	 
}


public static ActionDriver getActionDriver()
{
	 if(actiondriver.get()==null) {
	 System.out.println("Actiondriver not Initialized");
	 throw new IllegalStateException("Actiondrive rnot initilized");
	 }
	return actiondriver.get();
	 
}

}
