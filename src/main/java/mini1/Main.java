package mini1;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		WebDriver driver = new EdgeDriver();
		driver.get("https://www.stqatools.com/demo/Alerts.php");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Alert Part ***********
		// $$$try doing
		Thread.sleep(4000);
		//WebElement alert = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id=\"btnAlert\"]")));
		Actions action = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//button[@id=\"btnAlert\"]"));
		elem.click();
		
		//action.doubleClick(elem).perform();
		// Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();
		//Thread.sleep(3000);
		
		WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]")));
		//String actualText = driver.findElement(By.xpath("//div[4]")).getText();
		String actualText = text.getText();
		///System.out.println(actualText);
		//Assert.assertEquals(actualText, "Callback from alert");
		
		// driver.switchTo().alert().accept();;
		//Importing files from excel
		FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\testdata\\miniProject (1).xlsx");
		XSSFWorkbook workbook=new XSSFWorkbook(file);		
		XSSFSheet  sheet=workbook.getSheet("Sheet1");  //	XSSFSheet  sheet=workbook.getSheetAt(0);
		int totalRows=sheet.getLastRowNum();		
		int totalCells=sheet.getRow(0).getLastCellNum();
		List<String> deets = new ArrayList<String>();
		//HashMap<String, String> deets = new HashMap<>();
		System.out.println("number of rows:"+ totalRows); //5
		//System.out.println("number of cells:"+ totalCells);  //4
		for(int r=0;r<=totalRows;r++)
		{
			XSSFRow currentRow=sheet.getRow(r);
			for(int c=0;c<totalCells;c++)
			{
				XSSFCell cell=currentRow.getCell(c);
				deets.add(cell.toString());
				//System.out.print(cell.toString()+"\t");
			}
		}
		workbook.close();
		file.close();
		
		int j=0;
		for(int i=0; i<totalRows+1;i++) {
		
		driver.navigate().to("https://stqatools.com/demo/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.cssSelector("a[href=\"Register.php\"]")).click();
		//driver.findElement(By.id("name")).sendKeys(deets.get(j));
		
        //js.executeScript("arguments[0].value = '"+deets.get(j)+"';", nameElem);
		//arguments[0].value = 'deets.get(j)';
		WebElement nameElem = driver.findElement(By.id("name"));
		js.executeScript("arguments[0].value = '"+deets.get(j)+"';", nameElem);
		WebElement addressElem = driver.findElement(By.id("address"));
		js.executeScript("arguments[0].value = '"+deets.get(j+1)+"';", addressElem);
		Thread.sleep(3000);
		//driver.findElement(By.id("male")).click();
		WebElement sex = driver.findElement(By.id("male"));
		js.executeScript("arguments[0].click();", sex);
		driver.findElement(By.id("traveling")).click();
		WebElement country = driver.findElement(By.id("country"));
		Select s = new Select(country);
		s.selectByVisibleText("India");
		WebElement city = driver.findElement(By.id("city"));
		Select sc = new Select(city);
		sc.selectByVisibleText("Mumbai");
		driver.findElement(By.id("dob")).sendKeys(deets.get(j+2));
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		// driver.findElement(By.linkText("Submit")).click();
		j+=3;
		String suc = driver.findElement(By.cssSelector("form[id='registration-form'] p")).getText();
		// System.out.println(suc);
		Assert.assertEquals(suc, "User registration successful.");
		System.out.println(i);
		
		}
		driver.quit();
//		elem.click();
//		driver.findElement(By.xpath("//button[text()='Basic Alert']")).click();

//	JavascriptExecutor js = (JavascriptExecutor)driver;
////		
//		js.executeScript("arguments[0].click();", elem);
	}

}
