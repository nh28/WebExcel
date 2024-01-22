package cdodata;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelHandler {
	Workbook workbook;
	String excelFilePath;
	Sheet sheet = null;
	final String NUMBERS = "1234567890.-,";

    public ExcelHandler(String excelFileName, String sheetName) {
        excelFilePath = "src/main/resources/Ops/" + excelFileName;
    	try (FileInputStream fileInputStream = new FileInputStream(excelFilePath)) {
            
            workbook = WorkbookFactory.create(fileInputStream);
            
            sheet = workbook.getSheet(sheetName);
           

            
        } catch (IOException | EncryptedDocumentException e) {
            System.out.println("File not Found");
        	e.printStackTrace();
        }
    }

    

    public void writeExcel(int rowIndex, int columnIndex, String value) {
    	try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {

            if (sheet != null) {

                Row row = sheet.getRow(rowIndex);

                if (row != null) {

	                Cell cell = row.getCell(columnIndex);

	                if (cell == null)
	                {
	                	cell = row.createCell(columnIndex);
	                }
	                
	                if(!value.equals("") && isANumber(value))
	                {
	                	if(value.contains(",")) {
	                		int index = value.indexOf(",");
	                		if (value.contains(".")) {
	                			value = value.substring(0,index) + value.substring(index+1);
	                		}
	                		else {
	                			value = value.substring(0,index) + "." + value.substring(index+1);
	                		}
	                	}
	                	if(value.contains(".")) {
	                		cell.setCellValue(Double.parseDouble(value));
	                	}
	                	else {
	                		cell.setCellValue(Integer.parseInt(value));
	                	}
	                }
	                else{
	                	cell.setCellValue(value);
	                }
	                
	                // Save the changes
	                workbook.setForceFormulaRecalculation(true);
	                workbook.write(fileOutputStream);
                } 
               
            } else {
                System.out.println("Sheet not found");
            }

        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }
    
    public int findIndex(String targetString, int rowMax){
    	 for (int rowIndex = 0; rowIndex < rowMax; rowIndex++) {
             Row row = sheet.getRow(rowIndex);
             if (row != null) {
                 Cell cell = row.getCell(0); // Assuming you want to search the first column (column index 0)
                 if (cell != null && cell.getCellType() == CellType.STRING) {
                     String cellValue = cell.getStringCellValue();
                     if (cellValue.toLowerCase().contains(targetString.toLowerCase())) {
                         return rowIndex;
                     }
                 }
             }
         }
		return -1;
    	
    }
    
    public boolean isANumber (String value) {
    	int i = 0;
    	
    	while (i < value.length()) {
    		if (!(NUMBERS.contains(value.substring(i, i+1)))) {
    			return false;
    		}
    		i++;
    	}
    	return true;
    }
    public String findValue(int rowIndex) {
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
    
    public void closeWorkbook(){
    	try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
