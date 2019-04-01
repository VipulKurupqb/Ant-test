package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MessageObjRepo  {
	
	public WebDriver driver;
	
	public MessageObjRepo(WebDriver driver) {
			
			this.driver = driver;
			PageFactory.initElements(driver, this);
			
		}
	@FindBy(xpath = "//div[@id = 'menuIconInquiry']")
	WebElement MessageIcon;
	
	
	//input[@id ='id_inquiryName']
	
	@FindBy(xpath = "//input[@id ='id_inquiryName']")
	WebElement Name;
	
	@FindBy(xpath = "//input[@id ='email_id']")
	WebElement Email;
	
	@FindBy(xpath = "//input[@id ='company_id']")
	WebElement Company;
	
	@FindBy(xpath = "//input[@id ='phone_id']")
	WebElement Phone;
	
	@FindBy(xpath = "//textarea[@id='descrtextarea']")
	WebElement desc;
	
	@FindBy(id = "protype_id")
	WebElement drop;
	
	
	
	
	public WebElement MessageIcon()
	{
		return MessageIcon;
	}
	
	public WebElement Name()
	{
		return Name;
	}
	public WebElement Email()
	{
		return Email;
	}
	
	public WebElement Company()
	{
		return Company;
	}

	public WebElement Phone()
	{
		return Phone;
	}
	
	
	public WebElement desc()
	{
		return desc;
	}
	
	public WebElement drop()
	{
		return drop;
	}
	
//	public void dropp()
//	{
//		drop.click();
//	}
	
	public void Entermsgvalues(String Name, String Email, String Company,String Phone,String desc)
	
	{
		
		Name().sendKeys(Name);
		Email().sendKeys(Email);
		Company().sendKeys(Company);
		Phone().sendKeys(Phone);
		desc().sendKeys(desc);
	}

	public void dropdown()
	{
	   drop.click();
		new Select(drop).selectByVisibleText("Mobile Development");
		//selectByVisibleText("Mobile Development");
		drop.click();
	}
	

	
}
