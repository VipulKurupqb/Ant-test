package base;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.Log;
import utility.PropertyUtility;
import utility.ReadEmail;
import base.Constants;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


/*
 * Settings file that contains configuration related information.
 * Contains configurations for Web, Mobile OS, browsers, headless.
 * 
 */
public class Settings extends Constants{
	/*
	 * Function to be executed before executing any suite
	 */
	
	@SuppressWarnings("static-access")
	@Parameters({"appType"})
	
	@BeforeSuite
	
	public void beforeClassInBase(String appType) throws MessagingException {
	//	this.environment = sysProps.getProperty("environment");
	
		sysProps.setProperty("logfilename", suite);
		DOMConfigurator.configure("Log4j/log4j.xml");
		if (driver == null) {
			try {
				if (appType.equals("web")) 
					webSettings();
				else if (appType.equals("device")) 
					deviceSettings();
				else  if (appType.equals("API")) {
					APISettings();
				}
				else
					webSettings();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
		
	/*
	 * Function for API Settings
	 */
	
	@SuppressWarnings("static-access")
	public static void APISettings() {
		if(suite.contains("Web")) 
		RestAssured.baseURI=prop.getProperty("WebBaseUrl");
		datafile = prop.getProperty("WebAPIList");
		contentType="application/json";
	}
	
	/*
	 * Function for Device Settings to redirect according to the type of application
	 */
				
	public static void deviceSettings() throws InterruptedException, IOException {
		String device = sysProps.getProperty("device");
		if (device.equals("iOS"))
			iOSSettings();
		else if (device.equals("Android"))
			androidSettings();
		else
			iOSSettings();
	}
	
	/*
	 * Function for iOS Settings
	 */
	
	public static void iOSSettings() throws InterruptedException, IOException {
			stopServer();
			startServer();
			File appDir = new File(PropertyUtility.getProperty("iOSAppDir"));
			File app = new File(appDir, PropertyUtility.getProperty("iOSApp"));
			iOSCapabilities.setCapability("AUTOMATION_NAME",PropertyUtility.getProperty("iOSAUTOMATION_NAME"));
			iOSCapabilities.setCapability("platformName",PropertyUtility.getProperty("iOSPlatformName"));
			iOSCapabilities.setCapability("platformVersion", PropertyUtility.getProperty("iOSVersion"));
			iOSCapabilities.setCapability("deviceName", PropertyUtility.getProperty("iOSDeviceName"));
			iOSCapabilities.setCapability("udid", PropertyUtility.getProperty("Udid"));
			iOSCapabilities.setCapability("app", app.getAbsolutePath());
			iOSCapabilities.setCapability("appPackage", PropertyUtility.getProperty("iOSAppPackage"));
			iOSCapabilities.setCapability("newCommandTimeout", "120");
			iOSCapabilities.setCapability("noReset", true);
			iOSCapabilities.setCapability("clearSystemFiles", true);
			iOSCapabilities.setCapability("xcodeConfigfile", PropertyUtility.getProperty("Xcodeconfigfile"));
			iOSCapabilities.setCapability("realDeviceLogger", PropertyUtility.getProperty("Devicelogger"));
			try {
			driver = new IOSDriver<>(new URL(url), iOSCapabilities);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}catch(Exception e) {
			Log.addMessage("Appium Server not started. Plesae refer error log for more details");
		}
	}
	
	/*
	 * Function for Android Settings
	 */
	
	public static void androidSettings() {
		try {
			stopServer();
			startServer();
			File appDir = new File(PropertyUtility.getProperty("androidAppDir"));
			File app = new File(appDir, PropertyUtility.getProperty("androidApp"));
			androidCapabilities.setCapability("AUTOMATION_NAME",PropertyUtility.getProperty("androidAUTOMATION_NAME"));
			androidCapabilities.setCapability("platformName",PropertyUtility.getProperty("androidPlatformName"));
			androidCapabilities.setCapability("platformVersion", PropertyUtility.getProperty("androidVersion"));
			androidCapabilities.setCapability("deviceName", PropertyUtility.getProperty("androidDeviceName"));
			androidCapabilities.setCapability("app", app.getAbsolutePath());
			androidCapabilities.setCapability("appPackage", PropertyUtility.getProperty("androidAppPackage"));
			androidCapabilities.setCapability("newCommandTimeout", "120");
			androidCapabilities.setCapability("noReset", true);
			androidCapabilities.setCapability("clearSystemFiles", true);
			driver = new AndroidDriver<>(new URL(url), androidCapabilities);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}catch(Exception e) {
			Log.addMessage("Appium Server not started. Plesae refer error log for more details");
		}
	}
		
	/*
	 * Function for starting the Appium Server
	 */
	
	public static void startServer() throws IOException {	
		Log.addMessage("Starting the Appium Server");
		CommandLine command = new CommandLine("/usr/local/Cellar/node/8.4.0/bin/node");
		command.addArgument("/usr/local/lib/node_modules/appium/build/lib/main.js",false);
		command.addArgument("--address", false);
		command.addArgument(ip);
		command.addArgument("--port", false);
		command.addArgument("4723");
		command.addArgument("--no-reset", true);
		command.addArgument("--native-instruments-lib", true);
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			executor.execute(command, resultHandler);
			Thread.sleep(5000);
			Log.addMessage("Appium server started.");
		}catch (InterruptedException e) {
			Log.addIntExceptionMessage("Appium server not started",e);
			}		
	}

	/*
	 * Mobile Automation: Function for stopping the Appium Server
	 */
	
	public static void stopServer() throws InterruptedException {
		String[] command = { "/usr/bin/killall", "-KILL", "node" };
		try {
			Log.addMessage("Appium Server Stopped!!!");
			Runtime.getRuntime().exec(command);
			Thread.sleep(4000);
			Log.addMessage("Appium server stopped.");
		}catch (IOException e) {
			Log.addIOExceptionMessage("Appium server encountered an error ", e);
			}
	}		
	
	/*
	 * Web Automation: Function for Web Settings
	 */
	
	public static void webSettings() {
		String browser = sysProps.getProperty("browser");
		environment = sysProps.getProperty("environment");
		if (browser.equals("chrome")) 
			chromeSettings();
		else if (browser.equals("firefox")) 
			fireFoxSettings();
		else if (browser.equals("safari")) 
			safariSettings();
		else if (browser.equals("IE")) 
			IESettings();
		else if (browser.equals("headless"))
			headlessSettings();
//			headlessFirefoxSettings();
		else 
		 	chromeSettings();
		}
	
	/*
	 * Function for Chrome Settings if Google chrome is chosen as the web browser
	 */
	
	public static void chromeSettings(){
		webCapabilities = DesiredCapabilities.chrome();
		if(System.getProperty("os.name").startsWith("Windows")) {
			service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(userDirectory+"/Driver/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
		}
		else {
			service = new ChromeDriverService.Builder()
	                .usingDriverExecutable(new File(userDirectory+"/Driver/chromedriver"))
	                .usingAnyFreePort()
	                .build();
		}
		ChromeOptions options = new ChromeOptions();
		options.merge(webCapabilities);   
		options.addArguments("test-type");
		driver = new ChromeDriver(service, options);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("baseurl is: "+prop.getProperty("BaseUrl"));
		RestAssured.baseURI=prop.getProperty("BaseUrl");
		System.out.println("Path of web api list is:"+prop.getProperty("APIList"));
		datafile = prop.getProperty("APIList");
		contentType="application/json; charset=utf-8";
		}
	
	/*
	 * Function for Chrome headless settings.
	 */
	
	public static void headlessSettings(){
		webCapabilities = DesiredCapabilities.chrome();
		if(System.getProperty("os.name").startsWith("Windows")) {
			service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(userDirectory+"/Driver/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
		}
		else {
			service = new ChromeDriverService.Builder()
	                .usingDriverExecutable(new File(userDirectory+"/Driver/chromedriver"))
	                .usingAnyFreePort()
	                .build();
			}
		ChromeOptions options = new ChromeOptions();
		options.merge(webCapabilities);
		options.addArguments("--headless");
		options.addArguments("window-size=1200x600");
		options.addArguments("--ignore-certificate-errors");
		webCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
//		webCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
//		webCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driver = new ChromeDriver(service, options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
//		driver.get("https://cacert.org/");
		System.out.println("END OF HEADLESS METHOD");
		}

	/*
	 * Function for Firefox settings
	 */
	
	public static void fireFoxSettings() {
		if(System.getProperty("os.name").startsWith("Windows"))
			System.setProperty("webdriver.gecko.driver",userDirectory+"/Driver/geckodriver.exe");
		else
			System.setProperty("webdriver.gecko.driver",userDirectory+"/Driver/geckodriver");
		driver = new FirefoxDriver();		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement html = driver.findElement(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
		driver.manage().window().maximize();
		}
	
	/*
	 * Function for Headless Firefox settings
	 */
	
	public static void headlessFirefoxSettings(){
		 FirefoxBinary firefoxBinary = new FirefoxBinary();
		    firefoxBinary.addCommandLineOptions("--headless");
		    System.setProperty("webdriver.gecko.driver", userDirectory+"/Driver/geckodriver");
		    FirefoxOptions firefoxOptions = new FirefoxOptions();
		    firefoxOptions.setBinary(firefoxBinary);
		     driver = new FirefoxDriver(firefoxOptions);
		}
	
	/*
	 * Function for Safari settings.
	 */
	
	public static void safariSettings() {
		webCapabilities = new DesiredCapabilities();
		webCapabilities.setPlatform(Platform.MAC);
		driver = new SafariDriver();
		driver.manage().window().maximize();
		}
	
	/*
	 * Function for Internet Explorer settings
	 */
	
	public static void IESettings() {
		webCapabilities = new DesiredCapabilities();
		webCapabilities.setPlatform(Platform.WINDOWS);
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		}
	
	/*
	 * Function to open the URL.
	 */
	
	public void open(String url) {
		driver.get(url);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
				
	/*
	 * Function to set the URL based on the environment.
	 * 
	 */
	
	@SuppressWarnings("static-access")
	public String getPageURL() {
		if (environment.equals("production")) {
			url = prop.getProperty("prodUrl");
			}
		else if(environment.equals("test")) {
			url = prop.getProperty("testUrl");
			}
		else if (environment.equals("integration")) {
			url = prop.getProperty("integrationUrl");
			}
		else if (environment.equals("development")) {
			url = prop.getProperty("devUrl");
			}
		else {
			url = prop.getProperty("testUrl");
		}
		return(url);
	}
				
				
	/*
	 * Function for checking the status of website.
	 */
				
	public int getStatusCode(String pageUrl) throws Exception  {
		URL url = new URL(pageUrl);
		HttpURLConnection huc = (HttpURLConnection)url.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		return huc.getResponseCode();
	}
		
	/*
	 * Function to be executed after executing the suite.
	 */
	
	@AfterSuite
	/**
	 * To ensure that the driver has been closed or not
	 */
	public void tearDown() throws InterruptedException {
		boolean hasQuit = (driver.toString().contains("null")) ? true : false;
		if (hasQuit == false) {
			//driver.quit();
			//stopServer();
		}
	}
}

	
	

	

