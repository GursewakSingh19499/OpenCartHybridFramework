package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

		//methods which we can re-use in test case classes
		public static WebDriver driver;
		
		//create logger variable
		public Logger logger;
		
		public Properties p;
		
		@BeforeClass(groups= {"Regression","Master"})
		@Parameters({"browser","os"})
		public void setup(String br, String os) throws IOException {
			
			//loading config.properties file
			FileReader file = new FileReader("./src//test//resources//config.properties");
			p = new Properties();
			p.load(file);
			
			logger = LogManager.getLogger(this.getClass());			//this will get the class and write logs of that class
			
			//selenium grid standalone setup -> as os and browser paramerets are comming from XML
			if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
				
				DesiredCapabilities capabilities = new DesiredCapabilities();
				
				
				//handle OS
				if(os.equalsIgnoreCase("windows")) {
					capabilities.setPlatform(Platform.WIN10);
				}else if(os.equalsIgnoreCase("linux")) {
					capabilities.setPlatform(Platform.LINUX);
				}else {
					System.out.println("No Matching Operating system");
					return;
				}
				
				//handle browser
				switch(br.toLowerCase())
				{
				case "chrome" : capabilities.setBrowserName("chrome");
				break;
				
				case "edge" : capabilities.setBrowserName("MicrosoftEdge");
				break;
				
				case "firefox" : capabilities.setBrowserName("firefox");
				break;
				
				default : System.out.println("Invalid Browser");
				return;
				}
				
				//Instead of creating a local WebDriver, you create a RemoteWebDriver pointing to the Hub.
				//Hub assigns the test to a matching Node (based on browser/OS config).
				driver = new RemoteWebDriver(new URL("http://192.168.1.12:4444/wd/hub"),capabilities);
			}
			
			if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
				
				switch(br.toLowerCase())
				{
				case "chrome" : driver = new ChromeDriver();
				break;
				
				case "edge" : driver = new EdgeDriver();
				break;
				
				case "firefox" : driver = new FirefoxDriver();
				break;
				
				default : System.out.println("Invalid Browser");
				return;
				}
			}
			
			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get(p.getProperty("appURL"));
			driver.manage().window().maximize();
		}
		
		@AfterClass(groups= {"Regression","Master"})
		public void teardown() {
			driver.quit();
		}
		
		//to generate random string
		public String randomString() {
			String generatedString = RandomStringUtils.randomAlphabetic(5);
			return generatedString;
		}
		public String randomNumber() {
			String generatedNumber = RandomStringUtils.randomNumeric(10);
			return generatedNumber;
		}
		
		public String captureScreen(String tname) throws IOException {
		    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		    String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
		    File targetFile = new File(targetFilePath);

		    sourceFile.renameTo(targetFile);

		    return targetFilePath;
		}


}
