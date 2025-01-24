package com.practice.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfoTest {
	
	@Test(dataProvider = "getData")
	public void getProductInfoTest(String brandName, String productName) {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://amazon.in");
		
		//Search product
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(brandName, Keys.ENTER);
		
		//Capture product info
		String x = "//span[text()='" + productName + "']/../../../../div[3]/div[1]/div/div[1]/div[1]/div/a/span[1]/span[2]/span[2]";
		String price = driver.findElement(By.xpath(x)).getText();
		System.out.println("Price : " + price);
		
		driver.quit();
	}
	
	@DataProvider
	public Object[][] getData() throws Throwable {
		ExcelUtility eLib = new ExcelUtility();
		int rowCount = eLib.getRowCount("product");
		
		Object[][] objArr = new Object[rowCount][2];
		for(int i=0; i<rowCount; i++) {
			objArr[i][0] = eLib.getDataFromExcel("product", i+1, 0);
			objArr[i][1] = eLib.getDataFromExcel("product", i+1, 1);
		}
		return objArr;
	}
}
