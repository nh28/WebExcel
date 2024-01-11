package cdodata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteScraper {

	public static void parseWebsite(String website) throws IOException{
		org.jsoup.nodes.Document doc = Jsoup.connect(website).get();
		
		// Both English and French have the same route
		org.jsoup.select.Elements allTables = doc.select("details#normals-data table");
		int rowIndex = 0;
		
		for (Element singleTable : allTables) {
			 
			String tableName = singleTable.select("a").first().text();
			
			rowIndex = ExcelHandler.findIndex(tableName, 211);
			
			if(rowIndex != -1) {
				rowIndex += 2;
				Elements rows = singleTable.select("tbody").select("tr");
	            	 
	            for(Element row : rows) {
	            	String rowName = row.select("th").first().text();
	            	int colIndex = 24; // col Y
	            		 
	            	while(!(rowName.equals(ExcelHandler.findValue(rowIndex))) && rowIndex <=rows.size()){
	            			 rowIndex++;
	            	}
	            		 
	            	Elements values = row.select("td");
	            		 	
	                for (Element value : values) {
	                    ExcelHandler.writeExcel(rowIndex, colIndex, value.text());
	                    colIndex++;
	                }
	                rowIndex++;
	            	 
	             }
	        }
		}
    }
 
}
