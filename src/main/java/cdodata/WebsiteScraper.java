package cdodata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteScraper {

	public static void parseWebsite(String website) throws IOException{
		org.jsoup.nodes.Document doc = Jsoup.connect(website).get();
		
		Elements allTables = doc.select(
				"details#normals-data .mrgn-bttm-md mrgn-tp-md pdng-rght-lg expand-collapse-all");
		int rowIndex = 0;
		
		for (Element groupTables : allTables) {
			 Elements singleTables = groupTables.select("div.table-responsive");

             // Iterate through the child div elements
             for (Element table : singleTables) {
            	 Element tableBody = table.select("table tbody").first();
            	 Elements rows = tableBody.select("tr");
            	 
            	 for(Element row : rows) {
            		 Element tableName = row.selectFirst("th");
            		 String name = tableName.text();
            		 
            		 
            		 Elements values = row.select("td");

                     // Iterate through the td elements
                     for (Element value : values) {
                         
                     }
            	 }
             }
        }
    }
 
}
