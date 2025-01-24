package com.comcast.crm.orgtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateOrganizationWithPhoneNumberTest {

	public static void main(String[] args) throws Throwable {
		// Create Object
		FileUtility fLib = new FileUtility();
		ExcelUtility elib = new ExcelUtility();
		JavaUtility jLib = new JavaUtility();
		WebDriverUtility wLib = new WebDriverUtility();
		
		//Read common data
		String BROWSER = fLib.getDataFromPropertiesFile("browser");
		String URL = fLib.getDataFromPropertiesFile("url");
		String USERNAME = fLib.getDataFromPropertiesFile("username");
		String PASSWORD = fLib.getDataFromPropertiesFile("password");
		
		// Read testScript data from Excel File
		String orgName = elib.getDataFromExcel("org", 7, 2) + jLib.getRandomNumber();
		String phoneNumber = elib.getDataFromExcel("org", 7, 3);
		
		WebDriver driver = null;
		
		if(BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		}else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		}else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		}else {
			driver = new ChromeDriver();
		}
			
		// Step 1 : Login to app
		wLib.waitForPageToLoad(driver);
		driver.manage().window().maximize();
		driver.get(URL);
		
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		// Step 2 : Navigate to Organization Module
		driver.findElement(By.linkText("Organizations")).click();
		
		// Step 3 : Click on "Create Organization" Button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		
		// Step 4 : Enter all the details and create new organization
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		driver.findElement(By.id("phone")).sendKeys(phoneNumber);
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//Verify phone number info expected results
		String actualPhoneNumber = driver.findElement(By.id("dtlview_Phone")).getText();
		if(actualPhoneNumber.equals(phoneNumber)) {
			System.out.println(phoneNumber + " is created==PASS");
		} else {
			System.out.println(phoneNumber + " is not created==FAIL");
		}
		
		//Step 5 : Logout
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"))).perform();
		driver.findElement(By.linkText("Sign Out")).click();
		driver.quit();
	}
}