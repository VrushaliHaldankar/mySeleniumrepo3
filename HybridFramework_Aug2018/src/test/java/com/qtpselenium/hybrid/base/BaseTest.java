package com.qtpselenium.hybrid.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.reports.ExtentManager;
import com.qtpselenium.hybrid.utils.DataUtil;
import com.qtpselenium.hybrid.utils.Xls_Reader;

public class BaseTest {
	
	public Properties envProp;
	public Xls_Reader xls;
	public String testName;
	public  DriverScript ds;
	public ExtentReports rep;
	 public ExtentTest test;
	
	@BeforeTest
	public void init() throws FileNotFoundException
	{
		System.out.println("1. Initialization:=> Before Running Test : "+getClass().getSuperclass());
		
		envProp =new Properties();
		try {
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//env.properties");
    	envProp.load(fs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		testName=this.getClass().getSimpleName();
		String arr[] = this.getClass().getPackage().getName().split("\\.");
		String suiteName= arr[arr.length-1];
		System.out.println();
		xls  =new Xls_Reader(envProp.getProperty(suiteName+"_xls"));
		
		ds= new DriverScript();
		ds.setEnvProp(envProp);
		
	}
	
	@BeforeMethod
	public void initTest()
	{
		rep= ExtentManager.getInstance(envProp.getProperty("reportpath"));
		test= rep.createTest(testName);
		ds.setTest(test);
	}
	
	@AfterMethod
	public void quit()
	{
		if(ds!=null)
		{
			ds.quit();
		}
		
		if(rep!=null)
		{
			rep.flush();
		}
	}
	@DataProvider
	public Object[][] getData()
	{
		System.out.println("2. DataProvider  :=>  DataProvider declaration : "+getClass().getName());
		return DataUtil.getTestData(testName, xls);
	}

}
