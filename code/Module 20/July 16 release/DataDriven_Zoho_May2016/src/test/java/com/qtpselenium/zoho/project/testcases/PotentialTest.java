package com.qtpselenium.zoho.project.testcases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.zoho.project.base.BaseTest;
import com.qtpselenium.zoho.project.util.DataUtil;
import com.qtpselenium.zoho.project.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class PotentialTest extends BaseTest{
	Xls_Reader xls;
	SoftAssert softAssert;
	
	@Test(priority=1,dataProvider="getData")
	public void createPotentialTest(Hashtable<String,String> data){
		test = rep.startTest("CreatePotentialTest");
		test.log(LogStatus.INFO, data.toString());
		
		if(!DataUtil.isRunnable("CreatePotentialTest", xls) ||  data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"));
		navigate("appurl");
		doLogin(envProp.getProperty("username"), envProp.getProperty("password"));
		click("crmlink_xpath");
		click("potentials_xpath");
		click("newPotential_xpath");
		type("potentialName_xpath",data.get("PotentialName"));
		type("accountName_xpath",data.get("AccountName"));
		type("stage_xpath",data.get("Stage"));
		selectDate(data.get("ClosingDate"));
		click("savePotentialButton_xpath");
		//validate - you
		reportPass("Test Passed");
		
	}
	
	
	@Test(priority=2,dependsOnMethods={"createPotentialTest"})
	public void deletePotentialAccountTest(){
		// you
		
		test = rep.startTest("deletePotentialAccountTest");
		reportPass("Test Passed");
		 
	}
	
	@DataProvider
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, "CreatePotentialTest");
		
	}
	
	@BeforeMethod
	public void init(){
		softAssert = new SoftAssert();
	}
	
	
	@AfterMethod
	public void quit(){
		try{
			softAssert.assertAll();
		}catch(Error e){
			test.log(LogStatus.FAIL, e.getMessage());
		}
		if(rep!=null){
			rep.endTest(test);
			rep.flush();
		}
		if(driver!=null)
			driver.quit();
	}
	
}
