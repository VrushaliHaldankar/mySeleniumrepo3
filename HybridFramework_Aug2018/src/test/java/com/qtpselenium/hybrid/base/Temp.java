package com.qtpselenium.hybrid.base;

import java.util.Hashtable;

import com.qtpselenium.hybrid.utils.Xls_Reader;


public class Temp {

	public static void main(String[] args) {
		String testName = "LoginTest";
		
		Xls_Reader xls = new Xls_Reader("E:\\Module 21 Documentation\\SuiteA.xlsx");
		int testcaseRowNum=1;
		while(!xls.getCellData("Data", 0, testcaseRowNum).equals(testName))
		{
			testcaseRowNum++;
		}
		System.out.println(testName +": is present at row num:"+testcaseRowNum);
		
		int colStartRowNum = testcaseRowNum+1;
		int totalcols= 0;
		while(!xls.getCellData("Data", totalcols, colStartRowNum).equals(""))
		{
			totalcols++;
		}
		System.out.println(testName +": Total columns are :"+totalcols);
		
		
		int  dataStartRowNum =testcaseRowNum+2;
		int totalRows=0;
		while(!xls.getCellData("Data", 0, dataStartRowNum).equals(""))
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
				String Key=xls.getCellData("Data", cNum, colStartRowNum);
				String data=xls.getCellData("Data", cNum, rNum);
				//System.out.println(cNum+"."+Key +" : "+"----"+" : "+data+" ");
				table.put(Key, data);
				
			}
			myData[i][0]= table;
			i++;
			System.out.println(table);
			
			
			
		}
	}

}
