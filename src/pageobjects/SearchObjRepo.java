package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Settings;




public class SearchObjRepo extends Settings {
	//public WebDriver driver;
	
public SearchObjRepo(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	@FindBy(xpath = "//*[@id='searchWrap']/div")
	WebElement Search;
	
	
	
	@FindBy(xpath="//input[@id='q']")
	WebElement searchtextbox;
	
	
	public WebElement Search()
	{
		return Search;
	}
	
public WebElement searchtextbox()
	{
		return searchtextbox;
}
	
	

}
