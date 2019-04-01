package testmethods;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import base.Settings;

import pageobjects.GoogleCloudConsultant;
import testmethods.Menu;

public class Newpagenav extends Settings {

	@Test
	public void nextpage() throws IOException, InterruptedException {
		Menu nav = new Menu();
		nav.menutoggle();

		GoogleCloudConsultant G = new GoogleCloudConsultant(driver);
		G.microazure();
		
		
		System.out.println(driver.getCurrentUrl());

	}
	
	}