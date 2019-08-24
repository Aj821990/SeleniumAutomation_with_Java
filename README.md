This project is a test setup developed on top of Maven, TestNG, Selenium WebDriver, Extent Reporting, Log4j, Taking Screenshots and DataProvider to test the functionalities of http://automationpractice.com/index.php website. Can be executed in Google Chrome , Mozilla Firefox and Internet Explorer.

# **Prerequisites:**
- Java 8 JDK
- Maven
- Latest Chrome, Firefox and internet explorer

# **Steps for Execution:**
1. Download the git project in your local system and unzip the folder.
2. In the folder edit the run.bat file and replace the given project location with your project location
	e.g. cd C:\<<some path>>\API-automation-with-Rest-Assured\ServicesGroupKT
3. Save the run.bat file and double click it for execution

# **Note:**
1. If you need to run in specific browser, please enter the browser name (as "chrome" , "firefox" or "ie") in the config.properties file->save it->execute the bat file.
2. All the logs are available at location ./log
3. Screenshots for failed scenarios will be saved at location - ./Screenshots and will be names as /<<testname>>.png
4. All test reports are available at location ./test-output
