package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class GoogleCloudConsultant {

	public WebDriver driver;

	public GoogleCloudConsultant(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "div.owl-next")
	WebElement next;

	public WebElement next() {
		return next;
	}

	@FindBy(xpath = "//div/div/div[7]/li/a[@href='/azure-consulting/']")
	WebElement azure;

	public WebElement azure() {
		return azure;
	}
	public void microazure() throws InterruptedException {
	
		next.click();
		Thread.sleep(5000);
		//azure.click();

	      azure.click();
		
	}

}
