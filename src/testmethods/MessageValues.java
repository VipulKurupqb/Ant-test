package testmethods;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Settings;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import pageobjects.MessageObjRepo;


import utility.ExcelRead;

public class MessageValues extends Settings {
	@Test(dataProvider="Cust_Reg", priority=0)
	 public void customer_regis(String Name, String Email, String Company, String Phone, String desc) throws Exception,
	   BiffException, IOException {
		open(getPageURL());
		//driver = initializedriver();
//		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		MessageObjRepo msg = new MessageObjRepo(driver);
		msg.MessageIcon().click();
		Thread.sleep(2000);
//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
msg.Entermsgvalues(Name, Email, Company, Phone, desc);
msg.dropdown();
		//msg.Entermsgvalues(Name, Email, Company, Phone, desc);
		
//		String name = Name;
//		String email = Email;
//		String company = Company;
		
	}
	
//	@Test(priority=2)
//	public void sampleTest() throws Exception {
//		Sample sampleObject = new Sample();
//		sampleObject.Test();
//		
//	}
	
//	@DataProvider(name = "Cust_Reg")
//	public Object[][] testex() throws Exception {
//		excelRead ex = new excelRead();
//		return ex.CustRegistration();
//		 
//	}
	
	
	ExcelRead excel=new ExcelRead();
	/*
	 * Input data: verify the Sign up functionality along with email verification.
	 */

	@DataProvider(name="Cust_Reg")
	public Object[][]getData0()throws Exception{
		return excel.getTableArray(InputData, "UserAuthentication", "UserRegistration");
	}
	
//	@DataProvider(name="Cust_Reg")
//	public Object[][]getData2()throws Exception{
//		excelRead ex = new excelRead();
//		return ex.xlmethod("UserAuthentication", "UserRegistration");
//		
//		
////		return excel.getTableArray(InputData, "UserRegistrationValidations", "UserRegistrationPasswordValidations");
//	}
	
//	ex.CustRegistration();
//	ex.sample();

	
	
//	@DataProvider(name = "diplomaListIndex")
//	public Object[][] getDiplomaIndexlData() throws Exception {
//		return excel.getTableArray(InputData, "UserAuthentication", "diplomaListIndex");
//
//	}
	
	
//	@DataProvider(name = "Cust_Reg")
//	  public Object[][] CustRegistration() throws Exception {
//	   FileInputStream filepath = new FileInputStream("C:\\Users\\qbuser\\Desktop\\Testingxl.xls");
//
//	   Workbook wb = Workbook.getWorkbook(filepath);
//	   Sheet sheet = wb.getSheet("conversation");
//
//	   int row = sheet.getRows();
//	   System.out.println("number of rows" + row);
//	   int column = sheet.getColumns();
//	   System.out.println("number of columns" + column);
//	   String Testdata[][] = new String[row - 1][column];
//	   int count = 0;
//
//	   for (int i = 1; i < row; i++) {
//	    for (int j = 0; j < column; j++) {
//	     Cell cell = sheet.getCell(j, i);
//	     Testdata[count][j] = cell.getContents();
//	    }
//	    count++;
//	   }
//	   filepath.close();
//	   return Testdata;
//	  }
	
//	@AfterTest
//	public void teardown()
//	{
//		driver.close();
//	}
}






