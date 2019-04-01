package testmethods;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import base.Settings;

import pageobjects.SearchObjRepo;

public class PassSearchValues extends Settings {

	@Test
	public void TClogin() throws InterruptedException, IOException {
		//driver = initializedriver();
		SearchObjRepo Lo = new SearchObjRepo(driver);
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		Lo.Search().click();
		Lo.searchtextbox().sendKeys("testing");
		Lo.searchtextbox().sendKeys(Keys.ENTER);
		//driver.navigate().back();

	}

	// @AfterTest
	// public void teardown()
	// {
	// driver.close();
	// }

}
