 package contact_form;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

public class NewTest {
	public WebDriver driver;
	
  @Test(priority=1)
  public void Page_load(){
	JavascriptExecutor js = (JavascriptExecutor)driver;
	
	//checking the ready state of a page
	if(js.executeScript("return document.readyState").toString().equals("complete")){
		System.out.println("Page is loaded");
		return;
	}  
	
	//This loop will rotate for 100 times to check if the page is ready after every 1 second
	for (int i=0;i<100;i++){
		try{
			Thread.sleep(1000);
		}catch (InterruptedException e){}
		
		if(js.executeScript("return document.readyState").toString().equals("complete")){
			break;
		}
		
	}
	  
  }
  
  @Test(priority=2)
  public void enableElement() {
	  WebElement form= driver.findElement(By.className("col-sm-6 contact-form"));
	  List<WebElement> form_elements= form.findElements(By.tagName("input"));
	  for(WebElement item: form_elements){
		if(item.isEnabled()) {
			break;
		}
		else{
		 JavascriptExecutor javascript=(JavascriptExecutor)driver;
		 javascript.executeScript("item.removeAttribute('disabled')");
		 
		 
		}
	  }
  }
  
  @Test(priority=3)
  public void FillOutForm() {
	  //Click on the contact button
	  driver.findElement(By.linkText("Contact")).click();
	  
	  //explicit wait until the contact form load
	  WebDriverWait wait = new WebDriverWait(driver,10);
	  wait.until(ExpectedConditions.titleContains("OneTrust | Contact"));
	  
	  //Fill out the form
	  driver.findElement(By.xpath(".input//[@id='input-1']")).sendKeys("Sofiane");
	  driver.findElement(By.xpath(".//input[@id='input-2']")).sendKeys("Amarouche");
	  driver.findElement(By.xpath(".//input[@id='input-3']")).sendKeys("azroubar@gmail.com");
	  driver.findElement(By.xpath(".//input[@id='input-5']")).sendKeys("OnetrustAutomation");
	  driver.findElement(By.xpath(".//input[@id='input-4']")).sendKeys("404-503-5957");
	  driver.findElement(By.xpath(".//textarea[@id='00N3600000LNxBv']")).sendKeys("This is a test");
	  driver.findElement(By.name("submit")).click();
	  
  }
  
  @BeforeTest
  @Parameters("browser")
  public void Invoke_Browser(String browser) {
	  if (browser.equalsIgnoreCase("Firefox")) {
		   System.out.println("Running Firefox");
			System.setProperty("webdriver.gecko.driver","C:/Selenium2/Browsers-Drivers/geckodriver.exe");
		   driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
		   System.out.println("Running Chrome");
			System.setProperty("webdriver.firefox.driver","C:/Selenium2/Browsers-Drivers/chromedriver.exe");
		   driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
		   System.out.println("Running Microsoft Edge");
		   System.setProperty("webdriver.edge.driver","C:/Selenium2/Browsers-Drivers/MicrosoftWebDriver.exe");
		   driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("opera")) {
			   System.out.println("Running Opera");
			   System.setProperty("webdriver.opera.driver","C:/Selenium2/Browsers-Drivers/operadriver.exe");
			   driver = new OperaDriver();
			}
					
	  //maximize the browser window	
	  driver.manage().window().maximize();
	  
	  //implicitly wait for 5 seconds then navigate to the given URL
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  driver.get("https://onetrust.com/");
	  
	  //This method will delete all the cookies before initiating the test
	  driver.manage().deleteAllCookies();
	  
  }

  @AfterTest
  public void TakingScreenShot() throws IOException{
		 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      //The below method will save the screenshot into a folder named ScreenShot under C drive with the file name "screenshot.png"
         FileUtils.copyFile(scrFile, new File("C:\\ScreenShot\\Test_screenshot.png")); 
		 
	 }
  
  @AfterClass
  public void quitBrowser() {
	  driver.quit();
	  
	  
  }

}
