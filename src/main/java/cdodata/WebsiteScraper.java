package cdodata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteScraper {
	static final int COLY = 24;
	static final int COLR = 17;
	static final int COLAK = 36;
	
	public static void parseWebsite(String website, String type) throws IOException{
		org.jsoup.nodes.Document doc = Jsoup.connect(website).get();
		org.jsoup.select.Elements allTables = null;
		
		// Both English and French have the same route
		if (type.equals("D"))
		{
			allTables = doc.select("details#normals-data table");
		}
		if (type.equals("A"))
		{
			allTables = doc.select("details#station-metadata table");
		}
		
		int rowIndex = 0;
		int frostFreeTable = 0;
		
		for (Element singleTable : allTables) {
			 
			String tableName = singleTable.select("a").first().text();
			
			if(tableName.equals("Frost-Free"))
			{
				frostFreeTable++;
			}
			rowIndex = ExcelHandler.findIndex(tableName, 211);
			
			if(rowIndex != -1 && frostFreeTable <= 1) {
				rowIndex += 2;
				Elements rows = singleTable.select("tbody").select("tr");
	            	 
	            for(Element row : rows) {
	            	String rowName = row.select("th").first().text();
	            	int colIndex = 0;
	            	if (type.equals("A")) {
	            		colIndex = COLR;
	            	}
	            	if (type.equals("D")) {
	            		colIndex = COLY;
	            		if (tableName.equals("Frost-Free")) {
	            			colIndex = COLAK;
	            		}
	            	}
	 
	            	while(!(ExcelHandler.findValue(rowIndex).contains(rowName)) && rowIndex < 211){
	            			 rowIndex++;
	            	}

					if (rowIndex < 211){
		            	
		            	Elements values = row.select("td");
		            		 	
		                for (Element value : values) {
		                    if (tableName.equals("Frost-Free") && type.equals("D")) {
		                    	ExcelHandler.writeExcel(rowIndex, COLAK, value.text());
		                    	if (frostFreeTable == 2) {
		                    		rowIndex++;
		                    	}
		                    }
		                    else {
		                    	if (value != null && !value.text().equals("")){
			                		ExcelHandler.writeExcel(rowIndex, colIndex, value.text());
			                	}
			                    if (value.select("b").first() !=  null) {
			                		ExcelHandler.writeExcel(rowIndex, COLAK, value.text());
			                	}
		                    }
		                    colIndex++;
		                }
		                if (tableName.equals("Frost-Free") && type.equals("A") && values == null) {
		                	rowIndex --;
		                }
		                rowIndex++;
					}
					else {
						System.out.println("Row not found");
					}
	            }
	        }
		}
    }
 
}
