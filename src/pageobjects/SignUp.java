package pageobjects;
import java.time.Duration;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import base.Settings;
import utility.Log;
import utility.ReadEmail;

public class SignUp extends Settings {
//	@FindBy(xpath="//button[text()='SIGN UP']")
//	WebElement signUpLink;
	
	@FindBy(xpath="//input[@id='firstName']")
	WebElement firstname;
	
	@FindBy(xpath="//input[@id='lastName']")
	WebElement lastname;
	
	@FindBy(xpath="//input[@id='username']")
	WebElement username;
	
//	@FindBy(css="input[name=username]")
//	WebElement userName;
//	
	@FindBy(xpath="//input[@name='Passwd']")
	WebElement password;
	
	@FindBy(xpath="//input[@name='ConfirmPasswd']")
	WebElement confirmPassword;
	
//	@FindBy(css="button[data-test-id=register__form__submit]")
//	WebElement signUpButton;
//	
//	@FindBy(className="notification__wrapper")
//	WebElement message;
//	
//	@FindBy(css="div[data-test-id=headernav__logout]")
//	WebElement logoutButton;
//	
//	//**Elements of Sign up Validation messages
//		     
//	@FindBy(xpath="//div[@data-test-id='register__form__firstName']/*[contains(@class, 'field__error')]")
//	WebElement firstNameValidationMessage;
//	
//	@FindBy(xpath="//div[@data-test-id='register__form__email']/*[contains(@class, 'field__error')]")
//	WebElement emailValidationMessage;
//	
//	@FindBy(xpath="//div[@data-test-id='register__form__password']/*[contains(@class, 'field__error')]")
//	WebElement passwordValidationMessage;
//	
//	@FindBy(xpath="//div[@data-test-id='register__form__confirmPassword']/*[contains(@class, 'field__error')]")
//	WebElement confirmPasswordValidationMessage;
//	
//	@FindBy(className="notification__wrapper")
//	WebElement verificationMessage;
//	
//	//**FACEBOOK SIGN UP ELEMENTS
//	@FindBy(className="facebook")
//	WebElement facebookButton;
//	
//	@FindBy(id="email")
//	WebElement facebookId;
//	
//	@FindBy(id="pass")
//	WebElement facebookPassword;
//	
//	@FindBy(id="loginbutton")
//	WebElement facebookLoginButton;
//	
//	@FindBy(id="checkpointSubmitButton")
//	WebElement secureFacebookButton;
//	
//	@FindBy(xpath="//input[@name='p_pn']")
//	WebElement contactNumberFacebook;
//	
//	@FindBy(id="checkpointSubmitButton")
//	WebElement sendCodeButton;
//	
//	@FindBy(css="button[name=__CONFIRM__]")
//	WebElement facebookContinue;
//	
//	@FindBy(xpath="//*[@id=\"app\"]/div/div[2]/div/div[2]/h3")
//	WebElement messageDashboard;
//	
//	//**LINKEDIN SIGN UP ELEMENTS
//	
//	@FindBy(className="linkedin")
//	WebElement linkedInButton;
//	
//	@FindBy(id="session_key-login")
//	WebElement linkedinId;
//	
//	@FindBy(id="session_password-login")
//	WebElement linkedinPassword;
//	
//	@FindBy(id="btn-primary")
//	WebElement linkedinLoginButton;
//	
//	//**GOOGLE SIGN UP ELEMENTS
//	
//	@FindBy(className="google")
//	WebElement googleButton;
//	
//	@FindBy(id="identifierId")
//	WebElement googleId;
//	
//	@FindBy(id="identifierNext")
//	WebElement googleNextButton;
//	
//	@FindBy(xpath="//*[@id=\"password\"]/div[1]/div/div[1]/input")
//	WebElement googlePassword;
//	
//	@FindBy(id="passwordNext")
//	WebElement googleLoginButton;
//	
//	@FindBy(id="submit_approve_access")
//	WebElement allowGoogleButton;
	

	
	public SignUp(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	 /*
	  * Sign up functionality
	  */
	
	public void signUpMethod(String firstNameInput, String lastNameInput, String usernameInput,String passwordInput, 
			String confirmPasswordInput){
		Log.addMessage("User is navigated to home page");
		System.out.println("BEFORE CLICKING SIGN UP");
		//signUpLink.click();
//		System.out.println("CLICKED ON SIGN UP");
		Log.addMessage("User sign up pop-up is displayed");
		firstname.sendKeys(firstNameInput);
		lastname.sendKeys(lastNameInput);
		username.sendKeys(usernameInput);
		password.sendKeys(passwordInput);
		confirmPassword.sendKeys(confirmPasswordInput);
		//signUpButton.click();
		Log.addMessage("User clicked on the Sign up button");
//		String messageDisplayed=message.getText();
//		Assert.assertTrue(messageDisplayed.contains("Registration successful"),"Registration is unsuccessful");
}
	
	 /*
	  * Sign up functionality for company
	  */
//	
//	public void signUpMethodCompany(String firstNameInput, String lastNameInput, String emailInput,String passwordInput, 
//			String confirmPasswordInput){
//		Log.addMessage("User is navigated to home page");
//		Log.addMessage("User sign up pop-up is displayed");
//		firstname.sendKeys(firstNameInput);
//		lastname.sendKeys(lastNameInput);
//		username.sendKeys(emailInput);
//		password.sendKeys(passwordInput);
//		confirmPassword.sendKeys(confirmPasswordInput);
//		signUpButton.click();
//		Log.addMessage("User clicked on the Sign up button");
//		String messageDisplayed=message.getText();
//		Assert.assertEquals(messageDisplayed,"Company admin registration successful\n" + 
//				"Please log in using the credentials provided");
//	}
//	
//	 /*
//	  * Generic field validations of Sign up functionality
//	  */
//	
//	public void signUpValidations(String firstNameInput, String lastNameInput, String emailInput,String passwordInput, 
//			String confirmPasswordInput, String message1, String message2, String message3){
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		firstname.sendKeys(firstNameInput);
//		lastname.sendKeys(lastNameInput);
//		username.sendKeys(emailInput);
//		password.sendKeys(passwordInput);
//		confirmPassword.sendKeys(confirmPasswordInput);
//		signUpButton.click();
//		String vm1 = firstNameValidationMessage.getText();
// 		String vm2 = emailValidationMessage.getText();
// 		String vm3 = passwordValidationMessage.getText();
// 		String vm4 = confirmPasswordValidationMessage.getText();
//
// 		System.out.println("Validation Messages1:"+vm2);
// 		System.out.println("Validation Messages1:"+vm3);
// 		System.out.println("Validation Messages1:"+vm4);
// 		String errorMessage = "";
// 		if(!vm1.equals(message1)) {
// 			errorMessage = "First Name error message is not displayed. ";
// 			System.out.println("Inside First Name");
// 		}
// 		if(!vm2.equals(message2)) {
// 			errorMessage += "Email error message is not displayed. ";
// 			System.out.println("Inside Email");
// 		}
// 		if(!vm3.equals(message3)) {
// 			errorMessage += "Password error message is not displayed. ";
// 			System.out.println("Inside Password");
// 		}
// 		if(!vm4.equals(message3)) {
// 			errorMessage += "Password error message is not displayed. ";
// 			System.out.println("Inside Password");
// 		}
// 		String combinedActual =  vm1+vm2+vm3+vm4;
// 		String combinedExpected = message1+message2+message3;
// 		System.out.println("COMBINED ACTUAL MESSAGE:"+combinedActual);
// 		System.out.println("COMBINED EXPECTED MESSAGE:"+combinedExpected);
// 		System.out.println("CONCATENATED ERROR MESSAGE:"+errorMessage);
//		
//Assert.assertEquals(combinedActual, combinedExpected, errorMessage);
//	}
//	
//	 /*
//	  * Password validations of Sign up functionality
//	  */
//	
//	public void passwordValidations(String firstNameInput, String lastNameInput, String emailInput,String passwordInput, 
//			String confirmPasswordInput, String message1, String message2){
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		firstname.sendKeys(firstNameInput);
//		lastname.sendKeys(lastNameInput);
//		username.sendKeys(emailInput);
//		password.sendKeys(passwordInput);
//		confirmPassword.sendKeys(confirmPasswordInput);
//		signUpButton.click();
//		String vm3 = passwordValidationMessage.getText();
//		String vm4 = confirmPasswordValidationMessage.getText();
//		String errorMessage = "";
//		if(!vm3.equals(message1)) {
//			errorMessage = "Valid password error message is not displayed. ";
//			System.out.println("Inside First Name");
//		}
//		if(!vm4.equals(message2)) {
//			errorMessage += "Matching password error message is not displayed. ";
//			System.out.println("Inside Email");
//		}
//		String combinedActual = vm3+vm4;
//		String combinedExpected = message1+message2;
//		System.out.println("COMBINED ACTUAL MESSAGE:"+combinedActual);
//		System.out.println("COMBINED EXPECTED MESSAGE:"+combinedExpected);
//		System.out.println("CONCATENATED ERROR MESSAGE:"+errorMessage);
//		
//		Assert.assertEquals(combinedActual, combinedExpected, errorMessage);
//	}
//	
//	public void emailUsedMethod(String firstNameInput, String lastNameInput, String emailInput,String passwordInput, 
//			String confirmPasswordInput,String message) {
//		
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		firstname.sendKeys(firstNameInput);
//		lastname.sendKeys(lastNameInput);
//		username.sendKeys(emailInput);
//		password.sendKeys(passwordInput);
//		confirmPassword.sendKeys(confirmPasswordInput);
//		signUpButton.click();
//		String validationMessage = verificationMessage.getText();
//		String errorMessage="";
//		if(!validationMessage.equals(message)) {
//			errorMessage = "Proper error message is not displayed. ";
//		}
//			Assert.assertEquals(validationMessage, message, errorMessage);
//	}
//
//	 /*
//	  * Email verification method after Sign up.
//	  */
//	
//	public void emailVerification() throws Exception{
//
//		try {	
//			ReadEmail emailUtility = new ReadEmail(prop.getProperty("email.address"), prop.getProperty("email.password"), prop.getProperty("email.server"), prop.getProperty("email.protocol"),993,ReadEmail.EmailFolder.INBOX);
//			String activationLink=emailUtility.getActivationLink("SCU Registration");
//			System.out.println("Activation Link is: "+activationLink);
//			Log.addMessage("Received the activation link");
//			driver.get(activationLink);
//			Log.addMessage("Opened the activation link in the browser");
//			String verificationTextMessage = verificationMessage.getText();
//			Assert.assertTrue(verificationTextMessage.contains("Signup account verification successful"),"User Registration is unsuccessful");
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	 /*
//	  * Facebook Signup method
//	  */
//	
//	public void facebookSignUp(String userId, String password) {
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		facebookButton.click();
//		facebookId.sendKeys(userId);
//		facebookPassword.sendKeys(password);
//		facebookLoginButton.click();
//		String messageDisplayed=messageDashboard.getText();
//		Assert.assertTrue(messageDisplayed.contains("Here goes your awesome dashboard"),"Successfully logged in");
//	}
//	
//	 /*
//	  * Linkedin Signup method
//	  */
//	
//	public void linkedinSignUp(String linkedinUserId, String linkedinPasswordText) {
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		linkedInButton.click();
//		linkedinId.sendKeys(linkedinUserId);
//		linkedinPassword.sendKeys(linkedinPasswordText);
//		linkedinLoginButton.click();
//		String messageDisplayed=messageDashboard.getText();
//		Assert.assertTrue(messageDisplayed.contains("Here goes your awesome dashboard"),"Successfully logged in");
//	}
//	
//	/*
//	  * Google Signup method
//	  */
//	
//	
//	public void googleSignUp(String googleUserId, String googleinPasswordText) {
//		Log.addMessage("User is navigated to home page");
//		signUpLink.click();
//		Log.addMessage("User sign up pop-up is displayed");
//		googleButton.click();
//		googleId.sendKeys(googleUserId);
//		googleNextButton.click();
//		googlePassword.sendKeys(googleinPasswordText);
//		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//			    .withTimeout(Duration.ofSeconds(30))
//			    .pollingEvery(Duration.ofMillis(1000))
//			    .ignoring(NoSuchElementException.class);
//		wait.until(ExpectedConditions.visibilityOf(googleLoginButton));
//		googleLoginButton.click();
//		String messageDisplayed=messageDashboard.getText();
//		Assert.assertTrue(messageDisplayed.contains("Here goes your awesome dashboard"),"Successfully logged in");
//	}
//	
//	/*
//	  * Logout functionality
//	  */
//	
//	
//	public void LogoutMethod() throws InterruptedException {
//		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//			    .withTimeout(Duration.ofSeconds(30))
//			    .pollingEvery(Duration.ofMillis(1000))
//			    .ignoring(NoSuchElementException.class);
//		wait.until(ExpectedConditions.visibilityOf(logoutButton));
//		logoutButton.click();
//		Log.addMessage("User has logged out of the application");
//	}
//	


}
	