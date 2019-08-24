package com.hellofresh.testCases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.hellofresh.utilities.GlobalUtil;
import com.hellofresh.utilities.ReadConfig;

public class BaseClass {

	ReadConfig readconfig = new ReadConfig();

	public String baseURL = readconfig.getApplicationURL();

	public static WebDriver driver;

	public static Logger logger;

	GlobalUtil globalUtil = GlobalUtil.getInstance();
	SoftAssert softAssert = new SoftAssert();

	By signIn = By.xpath("//a[@class='login']");
	By logoutApp = By.xpath("//a[@class='logout']");

	public void signin() {
		globalUtil.getDriver().findElement(signIn).click();
	}

	public void logout() {
		globalUtil.getDriver().findElement(logoutApp).click();
	}

	@BeforeClass
	public void setup() {
		globalUtil.loadDriver();
		logger = Logger.getLogger("hellofresh");
		PropertyConfigurator.configure("Log4j.properties");
		globalUtil.getApplicationURL();

	}

	@AfterClass
	public void tearDown() {
		globalUtil.getDriver().quit();
		globalUtil.taskKill();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}

	public String randomestring() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return (generatedstring);
	}

	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}

	public String generateRandomEmail() {
		String randomEmail = randomestring() + randomeNum() + "@gmail.com";
		String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (randomEmail.matches(regex)) {
			softAssert.assertTrue(true);
		} else {
			softAssert.assertTrue(false);
		}
		return randomEmail;

	}
}
