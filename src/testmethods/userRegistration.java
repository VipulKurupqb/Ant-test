package testmethods;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import base.Settings;
import pageobjects.SignUp;
import utility.DBUtility;
import utility.ExcelRead;
import utility.Log;






public class userRegistration extends Settings {

	/*
	 * TEST CASE: verify the Sign up functionality along with email verification.
	 */

	@Test(dataProvider="registration", priority=0)
	public void userRegistrationmethod(String firstNameInput, String lastNameInput, String usernameInput, String passwordInput, 
			String confirmPasswordInput) throws InterruptedException {
		try {
			open(getPageURL());
			SignUp object1 = new SignUp(driver);
			object1.signUpMethod(firstNameInput, lastNameInput, usernameInput, passwordInput, confirmPasswordInput);
			Thread.sleep(10000);
			//object1.emailVerification();
			Log.addMessage("User details entered");
		}
		catch(Exception e) {
			Log.addMessage("User registration unsuccessful");
			Assert.assertTrue(false,"User registration failed");
			e.printStackTrace();
		}
	}

//	/*
//	 * TEST CASE: verify the general Sign up validations.
//	 */
//
//	@Test(dataProvider="registrationValidations", priority=1)
//	public void userRegistrationValidationmethod(String firstNameInput, String lastNameInput, String emailInput, String passwordInput, 
//			String confirmPasswordInput, String message1, String message2, String message3) {
//		try {
//			open(getPageURL());
//			SignUp object1 = new SignUp(driver);
//			object1.signUpValidations(firstNameInput,lastNameInput, emailInput, passwordInput, confirmPasswordInput, message1, message2, message3);
//			Log.addMessage("General sign up validations have been passed");
//		}
//		catch(Exception e) {
//			Log.addMessage("Some errors/exceptions occurred while verifying general validations");
//			Assert.assertTrue(false,"Errors/exceptions observed");
//			e.printStackTrace();
//		}
//	}
//	/*
//	 * TEST CASE: verify the Sign up validations for the password field.
//	 */
//
//	@Test(dataProvider="registrationPasswordValidations", priority=2)
//	public void userRegistrationPasswordmethod(String firstNameInput, String lastNameInput, String emailInput, String passwordInput, 
//			String confirmPasswordInput, String message1, String message2, String message3) {
//		try {
//			open(getPageURL());
//			SignUp object1 = new SignUp(driver);
//			object1.passwordValidations(firstNameInput,lastNameInput, emailInput, passwordInput, confirmPasswordInput, message1, message2);
//			Log.addMessage("Password validations have been passed");
//		}
//		catch(Exception e) {
//			Log.addMessage("Some errors/exceptions occurred while verifying password validations");
//			Assert.assertTrue(false,"Errors/exceptions observed");
//			e.printStackTrace();
//		}
//	}
//	
//	/*
//	 * TEST CASE: verify the already used email scenario
//	 */
//
//	@Test(dataProvider="RegistrationAlreadyEmail", priority=3)
//	public void userRegistrationEmailAlreadyTest(String firstNameInput, String lastNameInput, String emailInput, String passwordInput, 
//			String confirmPasswordInput, String message) {
//		try {
//			open(getPageURL());
//			SignUp object1 = new SignUp(driver);
//			object1.emailUsedMethod(firstNameInput,lastNameInput, emailInput, passwordInput, confirmPasswordInput, message);
//			Log.addMessage("Email already exists scenario is verified");
//		}
//		catch(Exception e) {
//			Log.addMessage("Some errors/exceptions occurred while verifying password validations");
//			Assert.assertTrue(false,"Errors/exceptions observed");
//			e.printStackTrace();
//		}
//	}	
//
//	/*
//	 * TEST CASE: to delete users from database
//	 */
//
//	@Test(dataProvider="dbSignUpUserDelete", priority=4)
//	public static void dataBaseDeleteUser(String username) throws SQLException {
//		try {
//			DBUtility deleteUsers=new DBUtility();
//			deleteUsers.userDeleteDB(username);
//		}
//		catch(Exception e) {	
//			Log.addMessage("Test case failed. Please check stack trace for more details.");
//			Assert.assertTrue(false,"Errors are observed");
//			e.printStackTrace();
//		}
//	}


	/*--------------------------------------------------INPUT DATA----------------------------------------------------------------*/

	ExcelRead excel=new ExcelRead();
	/*
	 * Input data: verify the Sign up functionality along with email verification.
	 */

	@DataProvider(name="registration")
	public Object[][]getData0()throws Exception{
		return excel.getTableArray(InputData, "UserAuthentication", "UserRegistration");
	}

	/*
	 * Input data: verify the generic Sign up validations
	 */

//	@DataProvider(name="registrationValidations")
//	public Object[][]getData1()throws Exception{
//		return excel.getTableArray(InputData, "UserRegistrationValidations", "UserRegistrationValidations");
//	}
//
//	/*
//	 * Input data: verify the Sign up validations for password.
//	 */
//
//	@DataProvider(name="registrationPasswordValidations")
//	public Object[][]getData2()throws Exception{
//		return excel.getTableArray(InputData, "UserRegistrationValidations", "UserRegistrationPasswordValidations");
//	}
//	
//	/*
//	 * Input data: verify the Sign up validations for already existing email
//	 */
//
//	@DataProvider(name="RegistrationAlreadyEmail")
//	public Object[][]getDataEmailExists()throws Exception{
//		return excel.getTableArray(InputData, "UserRegistrationValidations", "RegistrationAlreadyEmail");
//	}
//
//	/*
//	 * Input data: delete users from database
//	 */
//	@DataProvider(name="dbSignUpUserDelete")
//	public Object[][]getDataDelete()throws Exception{
//		return excel.getTableArray(InputData, "UserAuthentication", "dbSignUpUserDelete");
//	}


}