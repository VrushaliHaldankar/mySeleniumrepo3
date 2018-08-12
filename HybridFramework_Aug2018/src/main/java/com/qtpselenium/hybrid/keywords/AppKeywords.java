package com.qtpselenium.hybrid.keywords;

public class AppKeywords  extends GenericKeywords{
	
	 public void defaultLogin()
	 {
		 String username  =envProp.getProperty("Username");
		 String passsword  =envProp.getProperty("Password");
		 System.out.println("Default Username : "+username);
		 System.out.println("Default Password : "+passsword);
	 }

}
