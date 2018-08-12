package com.qtpselenium.hybrid.keywords;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.reports.ExtentManager;

public class GenericKeywords {
	public String objectKey;
	public String dataKey;
	public Hashtable<String,String> data;
	public WebDriver driver;
	public Properties envProp;
	public ExtentTest test;
	public String Proceed_On_Fail;
	public SoftAssert sftasrt = new SoftAssert();;
	
	
	
	/********************** Setter Functions ***************************/
	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	public void setData(Hashtable<String, String> data) {
		this.data = data;
	}

	public void setTest(ExtentTest test) {
		this.test = test;
	}
	public void setProceed_On_Fail(String proceed_On_Fail) {
		this.Proceed_On_Fail = proceed_On_Fail;
	}
	
	public void openBrowser()
	{
		String Browser =data.get(dataKey);
		test.log(Status.INFO,"Opening Browser.."+ Browser);
		if(Browser.equals("Mozilla"))
		{
			System.setProperty("webdriver.gecko.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\geckodriver-v0.20.1-win64\\geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			 driver= new FirefoxDriver();
	
		}
		else if (data.equals("Chrome"))
			 {
				 System.setProperty("webdriver.chrome.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\chromedriver_win32\\chromedriver.exe");
				 driver= new ChromeDriver();
			 }
			 else if (data.equals("IE"))
			 {
				 System.setProperty("webdriver.ie.driver", "E:\\SELENIUM_DOWNLOADS\\Drivers\\IEDriverServer_Win32_3.8.0\\IEDriverServer.exe");
				 driver= new InternetExplorerDriver();
			 }
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	
		}
			
		
		
	
	public void navigate() throws InterruptedException
	{
		test.log(Status.INFO,"Navigating to URL..."+envProp.getProperty(objectKey));
		driver.get(envProp.getProperty(objectKey));
		Thread.sleep(1000);
	}
	public void ValidateElementPresent()
	{
		if(!isElementPresent(objectKey))
		{
			// Report Failure
			reportFailure(objectKey+" Element not found ");
		}
		
	}
	public void TypeInput()
	{
		test.log(Status.INFO,"Typing the Input...."+ data.get(dataKey));
		getObject(objectKey).sendKeys(data.get(dataKey));
	}
	
	public void SubmitLogin() throws InterruptedException
	{
		test.log(Status.INFO,"Submitting to Login...."+envProp.getProperty(objectKey));
		getObject(objectKey).submit();
		System.out.println("---------------------------------------");
		Thread.sleep(5000);
	}
	
	public void quit()
	{
		if(driver!=null)
		{
			driver.quit();
		}
	}
	public void  ValidateTitle()
	{
		test.log(Status.INFO,"Validating the Login..."+(envProp.getProperty(objectKey)));
		String expectedTitle =envProp.getProperty(objectKey);
		String ActualTitle = driver.getTitle();
		if(!expectedTitle.equals(ActualTitle))
		{
			//report Failure
			reportFailure("Title mismatch. Got title as  " +ActualTitle);
		}
	}
	
	/**************             Utility Methods              ******** */
	public WebElement getObject(String objectKey)
	{
		WebElement e =null;
		try
		{
			if(objectKey.endsWith("_xpath"))
		e= driver.findElement(By.xpath(envProp.getProperty(objectKey)));
			else if(objectKey.endsWith("_css"))
		e= driver.findElement(By.cssSelector(envProp.getProperty(objectKey)));
			else if(objectKey.endsWith("_id"))
		e= driver.findElement(By.id(envProp.getProperty(objectKey)));

		WebDriverWait wait =new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOf(e));
		wait.until(ExpectedConditions.elementToBeClickable(e));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			reportFailure("Object not Found. "+objectKey);
		}
		return e;
	}
	
	public boolean isElementPresent(String objectKey)
	{
		List<WebElement> list =null;
		if(objectKey.endsWith("_xpath"))
			list= driver.findElements(By.xpath(envProp.getProperty(objectKey)));
		else if(objectKey.endsWith("_css"))
			list= driver.findElements(By.cssSelector(envProp.getProperty(objectKey)));
		else if(objectKey.endsWith("_id"))
		     list= driver.findElements(By.id(envProp.getProperty(objectKey)));
		
		if(list.size() ==0)
		return false;
		else
		return true;
	}
	
	
	/**************             Reporting Functions              **********/
	
	public void reportFailure(String failureMsg)
	{
		//1. Fail the Test
		//2.take the Screenshot , Embed screenshots in Reports
		
		test.log(Status.FAIL, failureMsg);
		takscreenshots();
		
		if(Proceed_On_Fail.equals("Y"))
		{  
			
			sftasrt.fail(failureMsg);
			
		}
		else
		{
			sftasrt.fail(failureMsg);
			sftasrt.assertAll();
			
		}
	}
	public void assert_all()
	{
		sftasrt.assertAll();
	}
	
	public void takscreenshots() 
	{
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//put screenshot file in reports
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	


	

}
