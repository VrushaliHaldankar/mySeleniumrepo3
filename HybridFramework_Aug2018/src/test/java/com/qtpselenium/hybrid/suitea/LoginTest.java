package com.qtpselenium.hybrid.suitea;

import java.util.Hashtable;

import org.testng.SkipException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.base.BaseTest;

import com.qtpselenium.hybrid.utils.Constants;
import com.qtpselenium.hybrid.utils.DataUtil;


public class LoginTest extends BaseTest{
	
	
	@Test(dataProvider= "getData")
	public void loginTest(Hashtable<String, String> data) throws Exception
	{
		if(DataUtil.isSkip(testName, xls)||data.get(Constants.RUNMODE).equals(Constants.RUNMODE_NO)) {
			test.log(Status.SKIP, "Runmode is set to NO ");
			throw new SkipException("Runmode is set to NO ");
	}
		System.out.println("3. LoginTest     :=> Running Login Test : "+getClass().getName());
		
		
		ds.executeKeywords(testName, xls, data);
	}
	
	
}
