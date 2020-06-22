package com.hellofresh.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlobalUtil {

	public static Properties prop = new Properties();

	public static WebDriver driver;
	private static GlobalUtil globalUtil = new GlobalUtil();
	public String configFileName = System.getProperty("user.dir") + "\\Configuration\\config.properties";
	public static Logger logger;
	static String uploadPath = System.getProperty("user.dir") + "\\Screeenshots";

	public static GlobalUtil getInstance() {
		return globalUtil;
	}

	private GlobalUtil() {

		try {
			FileInputStream inputStream = new FileInputStream(configFileName);
			prop.load(inputStream);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		logger = Logger.getLogger("AutomationTest");
		PropertyConfigurator.configure("log4j.properties");
		return logger;
	}

	// get values of the key from config file
	public String getValue(String key) {
		return prop.getProperty(key).toString();
	}

	// Instantiate brower driver
	@SuppressWarnings("deprecation")
	public void loadDriver() {
		String browserName = getValue("browserType");
		if ("IE".equalsIgnoreCase(browserName)) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			System.setProperty("webdriver.ie.driver", getValue("iepath"));
			driver = new InternetExplorerDriver(capabilities);
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			System.setProperty("webdriver.chrome.driver", getValue("chromepath"));
			driver = new ChromeDriver();
		}

		else if ("firefox".equalsIgnoreCase(browserName)) {
			System.setProperty("webdriver.gecko.driver", getValue("firefoxpath"));
			driver = new FirefoxDriver();
		}

		else {
			System.out.println("driver info not present");
		}

	}

	// return driver
	public WebDriver getDriver() {
		return driver;
	}

	/*
	 * public void getEnvironment(){ String env = getValue("environment");
	 * if("SIT".equalsIgnoreCase(env)){ loadUrl(); } else
	 * if("UAT".equalsIgnoreCase(env)){ loadUrl(); } else{
	 * System.out.println("environment details not present"); } }
	 */

	// get the environment value from the config and launch the application with
	// the respective url
	public void getApplicationURL() {
		String env = getValue("environment");

		if ("SIT".equalsIgnoreCase(env)) {
			driver.get(getValue("siturl"));
			maximizeWindow();
		}

		else if ("UAT".equalsIgnoreCase(env)) {
			driver.get(getValue("uaturl"));
			maximizeWindow();
		} else {
			System.out.println("environment details not present");
		}

	}

	// maximize the window
	public void maximizeWindow() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	// apply Implicit wait
	public void implicitWait(int time) {
		getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	// kill the driver process
	public void taskKill() {
		String browserName = getValue("browserType");
		try {

			if ("InternetExplorer".equalsIgnoreCase(browserName)) {
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			} else if ("Chrome".equalsIgnoreCase(browserName)) {
				Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
			}

			else if ("Mozilla".equalsIgnoreCase(browserName)) {
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");

			}

			else {
				System.out.println("browser process did not killed");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// select dropdown values by webelement values
	public void selectByValue(WebElement drpdownelement, String value) {
		Select sel = new Select(drpdownelement);
		sel.selectByValue(value);
	}

	// select dropdown values by webelement visible text
	public void selectByVisibleText(WebElement visibleElement, String visibleTxt) {
		Select sel = new Select(visibleElement);
		sel.selectByVisibleText(visibleTxt);
	}

	// apply explicit wait on webelements
	public void explicitWait(By webElement, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(webElement));

	}

	// convert the string into specified format
	public String dateFormat(String val) throws ParseException {
		SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yyyy");

		Date date = sdfSource.parse(val);
		String format = sdfSource.format(date);
		return format;
	}

	// split date and retrive day, month and year from the given String Date
	public Map<String, String> splitDate(String iSplit) throws ParseException {
		String birthDay = dateFormat(iSplit);
		String[] dayFormat = birthDay.split("/");
		String strpattern = "^0+";
		String dd = dayFormat[0];
		String trimDay = dd.replaceAll(strpattern, "");
		String month = dayFormat[1];

		String trimMonth = month.replaceAll(strpattern, "");
		System.out.println("trim val is:" + trimMonth);
		String year = dayFormat[2];

		Map<String, String> datemap = new HashMap<String, String>();
		datemap.put("date", trimDay);
		datemap.put("month", trimMonth);
		datemap.put("year", year);
		System.out.println(datemap);
		return datemap;

	}

}
