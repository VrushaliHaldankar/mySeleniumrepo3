package com.qtpselenium.hybrid.suitea;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.base.BaseTest;
import com.qtpselenium.hybrid.reports.ExtentManager;
import com.qtpselenium.hybrid.utils.Constants;
import com.qtpselenium.hybrid.utils.DataUtil;

public class TestB  extends BaseTest{
	

	@Test(dataProvider= "getData")
	public void loginTest(Hashtable<String, String> data)
	
	{
		
		test.log(Status.INFO, "Starting TestB");
		
		

		if(DataUtil.isSkip(testName, xls)||data.get(Constants.RUNMODE).equals(Constants.RUNMODE_NO))
		{
			test.log(Status.SKIP, "Runmode is set to NO ");
			throw new SkipException("Runmode is set to NO ");
		}
		System.out.println("3. LoginTest     :=> Running Login Test : "+getClass().getName());
		
		
		//ds.executeKeywords(testName, xls, data);
		
		test.log(Status.INFO, "Executing Keywords");
		test.log(Status.PASS, "Test Passed");
		rep.flush();
		
	}
	

}
