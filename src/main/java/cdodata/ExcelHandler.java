package cdodata;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelHandler {
	static Workbook workbook;
	static String excelFilePath;
	static Sheet sheet = null;

    public static void readExcel(String excelFileName, String sheetName) {
        excelFilePath = excelFileName;
    	try (FileInputStream fileInputStream = new FileInputStream(excelFilePath)) {
            // Open the Excel workbook
            workbook = WorkbookFactory.create(fileInputStream);

            // Access a specific sheet by name
            sheet = workbook.getSheet(sheetName);

            
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }

    public static void writeExcel(int rowIndex, int columnIndex, String value) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {

            // Check if the sheet exists
            if (sheet != null) {
                // Access a specific row
                Row row = sheet.getRow(rowIndex);
                // If the row does not exist, create a new one
                if (row != null) {
	                // Access a specific cell in the row
	                Cell cell = row.getCell(columnIndex);

	                // Set the cell value
	                cell.setCellValue(value);

	                // Save the changes
	                workbook.write(fileOutputStream);
                } 
                // later add if col does not exist
            } else {
                System.out.println("Sheet not found");
            }

        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }
    
    public static int findIndex(String targetString, int rowMax){
    	 for (int rowIndex = 0; rowIndex < rowMax; rowIndex++) {
             Row row = sheet.getRow(rowIndex);
             if (row != null) {
                 Cell cell = row.getCell(0); // Assuming you want to search the first column (column index 0)
                 if (cell != null && cell.getCellType() == CellType.STRING) {
                     String cellValue = cell.getStringCellValue();
                     if (cellValue.equals(targetString)) {
                         return rowIndex;
                     }
                 }
             }
         }
		return -1;
    	
    }
    
    public static void closeWorkbook(){
    	try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
