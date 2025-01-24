package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage {

	@FindBy(xpath = "//input[@alt='Create Product...]")
	private WebElement createProductImgBtn;
	
}
