package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage {
	WebDriver driver;
	public ContactPage(WebDriver driver) { 
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//img[@title='Create Contact...']")
	private WebElement createContactBtn;
	
	@FindBy(className="dvHeaderText")
	private WebElement headerMsg;
	
	@FindBy(className="dtlview_Last Name")
	private WebElement actLastName;
	
	public WebElement getCreateContactBtn() {
		return createContactBtn;
	}

	public WebElement getHeaderMsg() {
		return headerMsg;
	}

	public WebElement getActLastName() {
		return actLastName;
	}

	public void createContact(String lastName) {
		/*
		 * orgNameEdt.sendKeys(lastName); saveBtn.click();
		 */
	}
	
	public void createOrg(String orgName, String industry) {
		/*
		 * orgNameEdt.sendKeys(orgName); Select sel = new Select(industryDB);
		 * sel.selectByVisibleText(industry); saveBtn.click();
		 */
	}
}
