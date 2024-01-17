package cdodata;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

abstract class Website {
	public static Elements allTables;
	static final int COLY = 24;
	static final int COLR = 17;
	static final int COLAK = 36;
	abstract public Elements setElements(Document doc);
	abstract public void handleFrostFree(Element FFTable, int rowIndex);
	abstract public int setColIndex();
	
	public void parseWebsite(String website) throws IOException {
		Document doc = Jsoup.connect(website).get();
		allTables = setElements(doc);
		
		int rowIndex = 0;
		for (Element singleTable : allTables) {
			String tableName = singleTable.select("a").first().text();
			rowIndex = ExcelHandler.findIndex(tableName, 211);
			
			if (tableName.equals("Frost-Free")) {
				handleFrostFree(singleTable, rowIndex);
			}
			else {
				iterateTable(rowIndex, singleTable, setColIndex());
			}
			
		}
	}
	
	public void iterateTable(int rowIndex, Element singleTable, int column) {
		if(rowIndex != -1) {
			rowIndex += 2;
			Elements rows = singleTable.select("tbody").select("tr");
			
			for (Element row : rows) {
				String rowName = row.select("th").first().text();
            	int colIndex = column;
            	
            	if (row.select("td").first() != null){
            		while(!(ExcelHandler.findValue(rowIndex).contains(rowName) || rowName.contains(ExcelHandler.findValue(rowIndex))) && rowIndex < 211){
              			 rowIndex++;
                   	}
                   	
                   	if (rowIndex < 211) { 
                   		rowIndex = iterateRow(row, rowIndex, colIndex); 
                   	}
                   	else { System.out.println("Row not found"); }
            	}
            	
			}
		}     	 
	}
	
	public int iterateRow(Element row, int rowIndex, int colIndex) {
		Elements values = row.select("td");
		
		for (Element value : values) {
            if (value != null && !value.text().equals("")){
            	ExcelHandler.writeExcel(rowIndex, colIndex, value.text());
            }
            if (value.select("b").first() !=  null) {
            	ExcelHandler.writeExcel(rowIndex, COLAK, value.text());
            }
            colIndex++;
        }
		
		if (values != null) { return rowIndex++; }
		return rowIndex;
		
	}
}
