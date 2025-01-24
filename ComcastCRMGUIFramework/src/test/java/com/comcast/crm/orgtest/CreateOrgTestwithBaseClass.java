package com.comcast.crm.orgtest;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateOrgTestwithBaseClass extends BaseClass{
	@Test(groups = {"smokeTest"})
	public void createContactTest() throws Throwable {

		// Read testScript data from Excel File
		String orgName = elib.getDataFromExcel("org", 1, 2) + jLib.getRandomNumber();

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
		if (actOrgName.contains(orgName)) {
			System.out.println(orgName + " name is verified==PASS");
		} else {
			System.out.println(orgName + " name is not verified==FAIL");
		}
	}

	@Test(groups = "regressionTest")
	public void createContactWithSupportDateTest() throws Throwable {

		// Read testScript data from Excel File
		String lastName = elib.getDataFromExcel("contact", 1, 2) + jLib.getRandomNumber();
		// Step 2 : Navigate to Contact Module
		driver.findElement(By.linkText("Contacts")).click();

		// Step 3 : Click on "Create Contact" Button
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// Step 4 : Enter all the details and create new Contact
		String startDate = jLib.getSystemDateYYYYDDMM();
		String endDate = jLib.getRequiredDateYYYYDDMM(30);

		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.name("support_start_date")).sendKeys(startDate);
		driver.findElement(By.name("support_end_date")).sendKeys(endDate);

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify start date and end date expected results
		String actualStartDate = driver.findElement(By.id("dtlview_Support Start Date")).getText();
		if (actualStartDate.equals(startDate)) {
			System.out.println(startDate + " is created==PASS");
		} else {
			System.out.println(startDate + " is not created==FAIL");
		}

		String actualEndDate = driver.findElement(By.id("dtlview_Support End Date")).getText();
		if (actualEndDate.equals(endDate)) {
			System.out.println(endDate + " is created==PASS");
		} else {
			System.out.println(endDate + " is not created==FAIL");
		}
	}

	@Test(groups = "regressionTest")
	public void createContactWithOrgTest() throws Throwable {
		// Read testScript data from Excel File
		String orgName = elib.getDataFromExcel("Contact", 7, 2) + jLib.getRandomNumber();
		String contactLastName = elib.getDataFromExcel("Contact", 7, 3);

		// Step 2 : Navigate to Organization Module
		driver.findElement(By.linkText("Organizations")).click();

		// Step 3 : Click on "Create Organization" Button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		// Step 4 : Enter all the details and create new organization
		driver.findElement(By.name("accountname")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify header message expected results
		String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerInfo.contains(orgName)) {
			System.out.println(orgName + " is created==PASS");
		} else {
			System.out.println(orgName + " is not created==FAIL");
		}

		// Step 5 : Navigate to Contact module
		driver.findElement(By.linkText("Contacts")).click();

		// Step 6 : Click on "Create Contact" Button
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		// Step 7 : Enter all the details and create new Contact
		driver.findElement(By.name("lastname")).sendKeys(contactLastName);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();

		// Switch to child window
		wLib.switchToTabOnURL(driver, "module=Accounts");

		driver.findElement(By.name("search_text")).sendKeys(orgName);
		driver.findElement(By.name("search")).click();
		// Dynamic xpath
		driver.findElement(By.xpath("//a[text()='" + orgName + "']")).click();

		// Switch to parent window
		wLib.switchToTabOnURL(driver, "contacts&action");

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// Verify header message expected results
		headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerInfo.contains(contactLastName)) {
			System.out.println(contactLastName + " is created==PASS");
		} else {
			System.out.println(contactLastName + " is not created==FAIL");
		}

		// Verify header orgName info expected results
		String actualOrgName = driver.findElement(By.id("mouseArea_Organization Name")).getText();

		// We used trim() because there was a whitespace in the beginning of the
		// actualOrgName
		if (actualOrgName.trim().equals(orgName)) {
			System.out.println(orgName + " is created==PASS");
		} else {
			System.out.println(orgName + " is not created==FAIL");
		}
	}
}
