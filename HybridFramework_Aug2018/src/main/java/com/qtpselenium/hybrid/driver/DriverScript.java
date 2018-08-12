package com.qtpselenium.hybrid.driver;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;



import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.keywords.AppKeywords;
import com.qtpselenium.hybrid.utils.Constants;
import com.qtpselenium.hybrid.utils.Xls_Reader;

public class DriverScript {
	public Properties envProp;
	public AppKeywords app;
	public ExtentTest test;

	public void setTest(ExtentTest test) {
		this.test = test;
	}

	public void executeKeywords(String testName,Xls_Reader xls ,Hashtable<String, String> testData) throws Exception
	{
		int rows =xls.getRowCount(Constants.KEYWORDS_SHEET);
		System.out.println("Rows : "+rows);
		app =new AppKeywords();
		//1. send properties to Generickeywords .java
		app.setEnvProp(envProp);
		
		//2. send the data 
		app.setData(testData);
		app.setTest(test);
		for(int rNum =2;rNum <=rows ;rNum++)
		{
			
			String tcid =xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TEST_CASE_ID, rNum);
			if(tcid.equals(testName))
			{
			String keyword =xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD, rNum);
			String objectKey =xls.getCellData(Constants.KEYWORDS_SHEET,Constants.OBJECT, rNum);
			String Proceed_On_Fail =xls.getCellData(Constants.KEYWORDS_SHEET,Constants.Proceed_On_Fail, rNum);
			String dataKey =xls.getCellData(Constants.KEYWORDS_SHEET,Constants.DATA_SHEET, rNum);
			String data = testData.get(dataKey);
			test.log(Status.INFO, tcid+"---"+keyword+"---"+envProp.getProperty(objectKey)+"---"+Proceed_On_Fail+"---"+data);
			app.setDataKey(dataKey);
			app.setObjectKey(objectKey);
			app.setProceed_On_Fail(Proceed_On_Fail);
			
			 Method method;
			
				method =app.getClass().getMethod(keyword);
				method.invoke(app);
				
			
		
			}
			
		}
		app.assert_all();
	}
	

	public Properties getEnvProp() {
		return envProp;
	}

	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}
	
	public void quit()
	{
		if(app!=null)
		app.quit();
	}
	
	
}
