package com.comcast.crm.orgtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateOrganizationTest2 {

	@Test
	public void createOrganization() throws Throwable {
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
		String orgName = elib.getDataFromExcel("org", 1, 2) + jLib.getRandomNumber();
		
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
		driver.get(URL);
		
		// Rule 3 : Object Initialization
		//LoginPage lp = PageFactory.initElements(driver, LoginPage.class);
		LoginPage lp = new LoginPage(driver);
		
		// Rule 5 : Object Utilization - Instead of accessing each element, an action is provided in a method
		/*
		 * lp.getUsernameEdt().sendKeys("admin");
		 * lp.getPasswordEdt().sendKeys("manager"); 
		 * lp.getLoginBtn().click();
		 */
		
		lp.loginToApp(URL, USERNAME, PASSWORD);
		
		// Step 2 : Navigate to Organization Module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();
		
		// Step 3 : Click on "Create Organization" Button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		// Step 4 : Enter all the details and create new organization
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgName);
		
		// Verify Header msg Expected Result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actOrgName = oip.getHeaderMsg().getText();
		if(actOrgName.contains(orgName)) {
			System.out.println(orgName + " name is verified==PASS");
		} else {
			System.out.println(orgName + " name is not verified==FAIL");
		}
		
		//Step 5 : Logout
		hp.logout();
		driver.quit();
	}
}