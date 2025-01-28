package com.comcast.crm.basetest;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.comcasr.crm.generic.databaseutility.DataBaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass {
	
	// Create Objects
	public DataBaseUtility dbLib = new DataBaseUtility();
	public FileUtility fLib = new FileUtility();
	public static ExcelUtility elib = new ExcelUtility();
	public static JavaUtility jLib = new JavaUtility();
	public WebDriverUtility wLib = new WebDriverUtility();
	
	protected WebDriver driver = null;
	//We are creating this driver variable so that this can be accessible in ListenerImplementationClass for taking the screenshot.
	//We cant use the above driver variable because if we make it static, then this will not be available for parallel execution.
	public static WebDriver sDriver = null;

	@BeforeSuite(groups = {"smokeTest" , "regressionTest"})
	public void configBS() throws SQLException { 
		System.out.println("====Connect to DB, Report Config====");
		dbLib.getDbConnection();
	}
	
	//@Parameters("BROWSER")
	@BeforeClass(groups = {"smokeTest" , "regressionTest"})
	//public void configBC(String browser) throws Throwable { //This is the syntax when we use @Parameters
	public void configBC() throws Throwable { 
		System.out.println("==Launch the Browser==");
		//Getting from the properties file
		//String BROWSER = fLib.getDataFromPropertiesFile("browser");
		
		//Getting the value from testng file using @Parameters
		//String BROWSER = browser;
		
		//Getting value from the command line
		//String BROWSER = System.getProperty("browser");
		
		//Getting value from the command line
		String BROWSER = System.getProperty("browser",fLib.getDataFromPropertiesFile("browser"));
				
		if(BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		}else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		}else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		}else {
			driver = new ChromeDriver();
		}
		sDriver = driver;
	}
	
	@BeforeMethod(groups = {"smokeTest" , "regressionTest"})
	public void configBM() throws Throwable { 
		System.out.println("=Login=");
		//String URL = fLib.getDataFromPropertiesFile("url");
		//String USERNAME = fLib.getDataFromPropertiesFile("username");
		//String PASSWORD = fLib.getDataFromPropertiesFile("password");
		
		String URL = System.getProperty("url", fLib.getDataFromPropertiesFile("url"));
		String USERNAME = System.getProperty("username", fLib.getDataFromPropertiesFile("username"));
		String PASSWORD = System.getProperty("password", fLib.getDataFromPropertiesFile("password"));
		
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(URL, USERNAME, PASSWORD);
	}
	
	@AfterMethod(groups = {"smokeTest" , "regressionTest"})
	public void configAM() { 
		System.out.println("=Logout=");
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"))).perform();
		driver.findElement(By.linkText("Sign Out")).click();
	}
	
	@AfterClass(groups = {"smokeTest" , "regressionTest"})
	public void configAC() { 
		System.out.println("==Close the Browser==");
		driver.quit();
	}
	
	@AfterSuite(groups = {"smokeTest" , "regressionTest"})
	public void configAS() throws SQLException { 
		System.out.println("====Close DB, Report backup====");
		dbLib.closeDbConnection();
	}
}
