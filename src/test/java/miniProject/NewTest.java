package miniProject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import mini1.ExcelUtility;

public class NewTest {
	WebDriver driver;
	WebDriverWait wait;
	//First test to handle the alert
  @Test(priority=1)
  public void getAlert() throws InterruptedException {
	  	Thread.sleep(4000);
	  	//Locate the alert button
		WebElement elem = driver.findElement(By.xpath("//button[@id=\"btnAlert\"]"));
		//Click on it
		elem.click();
		//Click on ok button
		driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();
		//Explicitly wait for the Callback from alert text to be generated
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:nth-child(15)")));
		String actualText = text.getText();
		//Assert if it has been generated
		Assert.assertEquals(actualText, "Callback from alert","Element Intercepted");
	
  }
  
  //Testing navigation to login page
  @Test(priority=2)
  public void navigate() {
	  driver.navigate().to("https://stqatools.com/demo/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String demoUrl = driver.getCurrentUrl();
		Assert.assertEquals(demoUrl, "https://stqatools.com/demo/");
		driver.findElement(By.cssSelector("a[href=\"Register.php\"]")).click();
  }
  
  //Test to fill the login form
  @Test(priority=3)
  public void fillLogin() throws IOException, InterruptedException {
	  	//Getting the data from Excel sheet
		String filePath = "C:\\Users\\2320611\\eclipse-workspace\\Selenium Rahul\\Mini\\testdata\\miniExcel.xlsx";
		//Instantiating  the ExcelUtility class
	    ExcelUtility excelUtility = new ExcelUtility();
	    //Instantiating JavaScript Executor
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    //getting total rows
	    int totalRows = excelUtility.getTotalRow(filePath);
	    int j=0;
	    
	    for(int i=0; i<totalRows+1;i++) {
	    //Getting name from the excel sheet
	    String name = excelUtility.getCellValue(filePath, 0, i, j);
	    //Passing it to the Web page using JavaScript executor
	    WebElement nameElem = driver.findElement(By.id("name"));
		js.executeScript("arguments[0].value = '"+name+"';", nameElem);
		//Passing address to the Web page using JavaScript executor
		String address = excelUtility.getCellValue(filePath, 0, i, j+1);
		WebElement addressElem = driver.findElement(By.id("address"));
		js.executeScript("arguments[0].value = '"+address+"';", addressElem);
		//Selecting male Radio Button
		driver.findElement(By.id("male")).click();
		//Selecting traveling Check box
		driver.findElement(By.id("traveling")).click();
		//Selecting Country from the drop down menu
		WebElement countryElm = driver.findElement(By.id("country"));
		Select country = new Select(countryElm);
		country.selectByVisibleText("India");
		//Selecting Country from the drop down menu
		WebElement cityElm = driver.findElement(By.id("city"));
		Select city = new Select(cityElm);
		city.selectByVisibleText("Mumbai");
		//Passing address to the Web page using JavaScript executor
		String dob = excelUtility.getCellValue(filePath, 0, i, j+2);
		driver.findElement(By.id("dob")).sendKeys(dob);
		//Scrolling to the bottom using JavaScript executor
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		//Submit the form
		driver.findElement(By.cssSelector("button[type='submit']")).submit();
		//Assert if User registration is successful.
		String suc = driver.findElement(By.cssSelector("form[id='registration-form'] p")).getText();
		Assert.assertEquals(suc, "User registration successful.");
	    }
  }
  
  //Passing parameterized data to the Before class annotation 
  @BeforeClass
  @Parameters({"browser"})  // multiple browsing
  public void setUp(String br) throws InterruptedException {
	    	//Instantiate driver based on the Parameterized constructor
	    	switch(br.toLowerCase())	    	{
	        	case "chrome":
      		          driver = new ChromeDriver();
	        		System.out.println("\nChrome Succesfully Launched");
	        		break;
	        	
	        	case "edge":
	        		       		
	        		driver = new EdgeDriver();
	        		System.out.println("\nEdge Succesfully Launched");
	        		break;
	        	
	        	default :
	        		System.out.println("Invalid Browser");
	        		return;
	        	
	    	}
	    //Navigate to assigned url
		driver.get("https://www.stqatools.com/demo/Alerts.php");
		driver.manage().window().maximize();
		//Implicitly wait 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
  }
  
  @AfterClass
  public void tearDown() {
	  //quit the browser
	  driver.quit();
  }

}
