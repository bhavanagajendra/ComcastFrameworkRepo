package com.comcast.crm.listenerutility;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseClass;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ListenerImplementationClass implements ITestListener, ISuiteListener {

	public ExtentSparkReporter spark;
	public static ExtentReports report;
	public ExtentTest test;
	
	@Override
	public void onStart(ISuite suite) {
		//ISuiteListener.super.onStart(suite);
		System.out.println("Report configuration");
		
		//Spark report config - Extent Report
		spark = new ExtentSparkReporter("./AdvancedReport/report.html");
		spark.config().setDocumentTitle("CRM TestSuite Results");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		
		//Add Evn information & create test
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "CHROME-100");
	}
	
	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Report backup");
		
		//Extent Report
		report.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("========>" + result.getMethod().getMethodName() + "<========");
		test = report.createTest("createContactWithPNTest");
		test.log(Status.INFO, result.getMethod().getMethodName() + "==> STARTED <==");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("========>" + result.getMethod().getMethodName() + "<========");
		test.log(Status.PASS, result.getMethod().getMethodName() + "==> COMPLETED <==");
	}

	@Override
	public void onTestFailure(ITestResult result){
		String testName = result.getMethod().getMethodName();
		
		TakesScreenshot eDriver = (TakesScreenshot) BaseClass.sDriver;
		
		//To save/copy screenshot it local system
		//File srcFile = eDriver.getScreenshotAs(OutputType.FILE);
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		//try {
		//	FileHandler.copy(srcFile, new File("./screenshot/" + testName + "_" + time + ".png"));
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
		//To attach the screenshot in extent report
		String filePath = eDriver.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(filePath, testName + "_" + time);
		test.log(Status.FAIL, result.getMethod().getMethodName() + "==> FAILED <==");
	}
	
}
