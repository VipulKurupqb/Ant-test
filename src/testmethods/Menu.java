package testmethods;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.Settings;

import pageobjects.MenuMouseHover;
import utility.Log;

public class Menu extends Settings {

	@Test
	public void menutoggle() throws IOException, InterruptedException {
try
{
		//driver = initializedriver();
		open(getPageURL());
		Thread.sleep(5000);
		MenuMouseHover m = new MenuMouseHover(driver);
	m.Menu().click();
	m.hoverandclick();
//		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(screenshotFile, new File("E:\\Practice\\test.png"));
		 

	}
catch(Exception e) {
//	Log.addMessage("User registration unsuccessful");
//	Assert.assertTrue(false,"User registration failed");
	e.printStackTrace();
}
//	@AfterMethod //AfterMethod annotation - This method executes after every test execution
//	 public void screenShot(ITestResult result){
//	 //using ITestResult.FAILURE is equals to result.getStatus then it enter into if condition
//	 if(ITestResult.FAILURE==result.getStatus()){
//	 try{
//	 // To create reference of TakesScreenshot
//	 TakesScreenshot screenshot=(TakesScreenshot)driver;
//	 // Call method to capture screenshot
//	 File src=screenshot.getScreenshotAs(OutputType.FILE);
//	 // Copy files to specific location 
//	 // result.getName() will return name of test case so that screenshot name will be same as test case name
//	 FileUtils.copyFile(src, new File("E:\\Practice\\"+result.getName()+".png"));
//	 System.out.println("Successfully captured a screenshot");
//	 }catch (Exception e){
//	 System.out.println("Exception while taking screenshot "+e.getMessage());
//	 } 
//	 }
//	 driver.quit();
//	 }
//	

}
}
