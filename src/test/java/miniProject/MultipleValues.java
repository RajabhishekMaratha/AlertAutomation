package miniProject;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mini1.ExcelUtility;

public class MultipleValues {

	WebDriver driver;
	WebDriverWait wait;
  @Test(priority=1)
  public void getAlert() throws InterruptedException {
	  	Thread.sleep(4000);
		WebElement elem = driver.findElement(By.xpath("//button[@id=\"btnAlert\"]"));
		elem.click();
		// Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();
		//Thread.sleep(3000);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]")));
//		Thread.sleep(2000);
//		String actualText = driver.findElement(By.xpath("//div[4]")).getText();
		String actualText = text.getText();
		//System.out.println(actualText);
		//Assert.assertEquals(actualText, "Callback from alert");
		
		
  }
//  @Test(priority=2)
//  public void navigate() {
//	  driver.navigate().to("https://stqatools.com/demo/");
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		String demoUrl = driver.getCurrentUrl();
//		Assert.assertEquals(demoUrl, "https://stqatools.com/demo/");
//		driver.findElement(By.cssSelector("a[href=\"Register.php\"]")).click();
//		
//  }
  @Test(priority=2)
  public void fillLogin() throws IOException, InterruptedException {
	  	driver.navigate().to("https://stqatools.com/demo/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String demoUrl = driver.getCurrentUrl();
		Assert.assertEquals(demoUrl, "https://stqatools.com/demo/");
		driver.findElement(By.cssSelector("a[href=\"Register.php\"]")).click();
		
		String filePath = "C:\\Users\\2320611\\eclipse-workspace\\Selenium Rahul\\Mini\\testdata\\multipleVals.xlsx";
	    ExcelUtility excelUtility = new ExcelUtility();
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    int totalRows = excelUtility.getTotalRow(filePath);
	    int j=0;
	    //System.out.println(totalRows);
	    for(int i=0; i<totalRows+1;i++) {
	    String name = excelUtility.getCellValue(filePath, 0, i, j);
	    WebElement nameElem = driver.findElement(By.id("name"));
		js.executeScript("arguments[0].value = '"+name+"';", nameElem);
		String address = excelUtility.getCellValue(filePath, 0, i, j+1);
		WebElement addressElem = driver.findElement(By.id("address"));
		js.executeScript("arguments[0].value = '"+address+"';", addressElem);
		Thread.sleep(3000);
		driver.findElement(By.id("male")).click();
		driver.findElement(By.id("traveling")).click();
		WebElement country = driver.findElement(By.id("country"));
		Select s = new Select(country);
		s.selectByVisibleText("India");

		WebElement city1 = driver.findElement(By.id("city"));
		Select sc = new Select(city1);
		sc.selectByVisibleText("Mumbai");
		
		String dob = excelUtility.getCellValue(filePath, 0, i, j+2);
		
		driver.findElement(By.id("dob")).sendKeys(dob);
		// driver.findElement(By.linkText("Submit")).click();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		String suc = driver.findElement(By.cssSelector("form[id='registration-form'] p")).getText();
		// System.out.println(suc);
		//System.out.println(i);
		//Assert.assertEquals(suc, "User registration successful.");
	    }
  }
  @BeforeClass
  public void setUp() {
	  driver = new ChromeDriver();
		driver.get("https://www.stqatools.com/demo/Alerts.php");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
  }
  
  @AfterClass
  public void tearDown() {
	  driver.quit();
  }
}
