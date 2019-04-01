package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Settings;

public class MenuMouseHover extends Settings {
//public WebDriver driver;
	
	



	public MenuMouseHover(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	

	@FindBy(css = "span.menuicon")
	WebElement Toggle;

	
	
	@FindBy(xpath = "//a[@href='/cloud-migration-services/']")
	WebElement cloud;
	
	@FindBy(xpath = "//a[@href='/google-cloud-consultantt/']")
	WebElement googlecloud;
	
	
	

	public WebElement Menu() {
		return Toggle;
	}
	
	public WebElement Cloud() {
		return cloud;
	}
	
	public WebElement googlecloud() {
		return googlecloud;
	}
	
	
	
	public void hoverandclick() throws InterruptedException
	{
		 Actions action = new Actions(driver);
		 
	        action.moveToElement(cloud).build().perform();
	        Thread.sleep(2000);
	        googlecloud().click();
	 
	        
	}
	
	
	

//	public void WE() throws InterruptedException {
//		// TODO Auto-generated method stub
//Menu().click();
//		
//		hoverandclick();
//	}
	
	
	
	
}
