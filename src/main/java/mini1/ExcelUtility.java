package mini1;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public String getCellValue(String filePath, int sheetIndex, int rowIndex, int cellIndex) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(cellIndex);
        String cellValue = cell.toString();
        workbook.close();
        fileInputStream.close();
        return cellValue;
    }
	
	public int getTotalRow(String filePath) throws IOException {
		FileInputStream fileInputStream=new FileInputStream(filePath);
        
		XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);		
		XSSFSheet  sheet=workbook.getSheet("Sheet1");  //	XSSFSheet  sheet=workbook.getSheetAt(0);
		int totalRows=sheet.getLastRowNum();
		workbook.close();
        fileInputStream.close();
		return totalRows;
	}
	
	public int getTotalCell(String filePath) throws IOException {
		FileInputStream fileInputStream=new FileInputStream(filePath);
        
		XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);		
		XSSFSheet  sheet=workbook.getSheet("Sheet1");  //	XSSFSheet  sheet=workbook.getSheetAt(0);
		int totalCells = sheet.getRow(1).getLastCellNum();
		workbook.close();
        fileInputStream.close();
		return totalCells;
	}
}
