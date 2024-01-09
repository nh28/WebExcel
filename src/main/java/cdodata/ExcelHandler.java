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

    public static void openWorkbook(String excelFileName, String sheetName) {
        excelFilePath = excelFileName;
    	try (FileInputStream fileInputStream = new FileInputStream(excelFilePath)) {
            
            workbook = WorkbookFactory.create(fileInputStream);

            sheet = workbook.getSheet(sheetName);

            
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }

    public static void writeExcel(int rowIndex, int columnIndex, String value) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {

            if (sheet != null) {

                Row row = sheet.getRow(rowIndex);

                if (row != null) {

	                Cell cell = row.getCell(columnIndex);

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
    
    public static String findValue(int rowIndex) {
    	String val = null;
    	Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(2);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                val = cell.getStringCellValue();
            }
        }
        return val;
    }
    
    public static void closeWorkbook(){
    	try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
