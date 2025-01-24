package com.practice.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryTest {

	@Test(retryAnalyzer = com.comcast.crm.listenerutility.RetryListenerImplementation.class)
	public void activateSim() {
		System.out.println("Execute activateSim");
		//Assert.assertEquals("", "Login");
		System.out.println("Step-1");
		System.out.println("Step-2");
		System.out.println("Step-3");
		System.out.println("Step-4");
	}
}
