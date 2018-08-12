package com.qtpselenium.hybrid.utils;

import java.util.Hashtable;

public class DataUtil {
	
	public static Object[][] getTestData(String testName,Xls_Reader xls)
	{
		int testcaseRowNum=1;
		while(!xls.getCellData(Constants.DATA_SHEET, 0, testcaseRowNum).equals(testName))
		{
			testcaseRowNum++;
		}
		System.out.println(testName +": is present at row num:"+testcaseRowNum);
		int colStartRowNum = testcaseRowNum+1;
		int totalcols= 0;
		while(!xls.getCellData(Constants.DATA_SHEET, totalcols, colStartRowNum).equals(""))
		{
			totalcols++;
		}
		System.out.println(testName +": Total columns are :"+totalcols);
		int  dataStartRowNum =testcaseRowNum+2;
		int totalRows=0;
		while(!xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum).equals(""))
		{
			totalRows++;
			dataStartRowNum++;
		}
		System.out.println(testName +": Total Rows are :"+totalRows);
		
		dataStartRowNum =testcaseRowNum+2;
		Hashtable<String,String> table =null;
		
		int FinalRows = dataStartRowNum+totalRows;
		Object[][] myData = new Object[totalRows][1];
		
		int i=0;
		for (int rNum = dataStartRowNum;rNum < FinalRows ; rNum++ )
		{
			table = new Hashtable<String,String>();
			for(int cNum =0; cNum<totalcols;cNum++)
			{
				String Key=xls.getCellData(Constants.DATA_SHEET, cNum, colStartRowNum);
				String data=xls.getCellData(Constants.DATA_SHEET, cNum, rNum);
				table.put(Key, data);
			}
			myData[i][0]= table;
			i++;
			System.out.println(table);			
		}
		return myData;
	}

	//Function to check the runmode of the test
	//1. True N
	//2.false -Y
	
  public  static boolean isSkip(String testName,Xls_Reader xls)
  {
	   int rows = xls.getRowCount(Constants.TEST_CASES_SHEET);
	   for(int rNum= 2; rNum <= rows ; rNum++)
	   {
		String tcid = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID, rNum);
	   if(tcid.equals(testName))
	   {
		   String runmode = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, rNum);
		   if(runmode.equals(Constants.RUNMODE_NO))
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }
	   }
	   
	  }
	return true;
	  
  }
		  
		  
		  
		  
		  
		  
		  
	}


