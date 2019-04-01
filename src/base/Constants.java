package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.PropertyUtility;
import utility.ReadEmail;

public class Constants {
	public static WebDriver driver;
    public static String url=null;
    public static String environment=null;
    public static String testdata =null;
    public static PropertyUtility prop = new PropertyUtility();
	public static String projectName=PropertyUtility.getProperty("ProjectName");
	public static String prodSite=PropertyUtility.getProperty("prodUrl");
	public static String testSite=PropertyUtility.getProperty("testUrl");
	public static String intSite=PropertyUtility.getProperty("integrationUrl");
	public static String devSite=PropertyUtility.getProperty("devUrl");
	protected static String ip = PropertyUtility.getProperty("Ip");
	protected static String InputData = PropertyUtility.getProperty("InputData");
	public static DesiredCapabilities webCapabilities = new DesiredCapabilities();
	public static DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
	public static DesiredCapabilities androidCapabilities = new DesiredCapabilities();
	public static Properties sysProps = System.getProperties();
	public static String userDirectory = sysProps.getProperty("user.dir");
	public static String suite =sysProps.getProperty("suite");
	public static String appType=sysProps.getProperty("appType");
	public static String OS = System.getProperty("os.name");
	public static ChromeDriverService service;
	public static String baseUri;
	public static RequestSpecification httpRequest;
	public static Response response;
	public static String responseBody;
	public static int statusCode;
	public static String contentType;
	public static String datafile;
	public static ReadEmail emailUtils;
}
