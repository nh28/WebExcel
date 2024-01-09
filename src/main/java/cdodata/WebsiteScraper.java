package cdodata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteScraper {

	public static void parseWebsite(String website) throws IOException{
		org.jsoup.nodes.Document doc = Jsoup.connect(website).get();
		
		// Both English and French have the same route
		Elements allTables = doc.select(
				"details#normals-data .mrgn-bttm-md mrgn-tp-md pdng-rght-lg expand-collapse-all");
		int rowIndex = 0;
		
		for (Element groupTables : allTables) {
			 
			Element summaryElement = groupTables.select("summary.background-light").first();
			String groupName = summaryElement.text();
			
			rowIndex = ExcelHandler.findIndex(groupName, 211) + 2;
			
			Elements singleTables = groupTables.select("div.table-responsive"); 
            
             for (Element table : singleTables) {
            	 Element tableBody = table.select("table tbody").first();
            	 Elements rows = tableBody.select("tr");
            	 
            	 for(Element row : rows) {
            		 Element rowName = row.selectFirst("th");
            		 String name = rowName.text();
            		 
            		 int colIndex = 24; // col Y
            		 
            		 if(!(name.equals(ExcelHandler.findValue(rowIndex)))){
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
