package cdodata;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataWebsite extends Website{
	static int tableNum = 0;
	ExcelHandler handler;
	
	public DataWebsite(String excel, String sheet) {
		handler = new ExcelHandler(excel, sheet);
	}
	@Override
	public Elements setElements(Document doc) {
		return doc.select("details#normals-data table");
	}

	@Override
	public void handleFrostFree(Element FFTable, int rowIndex) {
		if (tableNum == 0) {
			iterateTable(rowIndex, FFTable, COLAK, handler);
			tableNum++;
		}
		else {
			Elements values = FFTable.getElementsByTag("td");
			int index = 200;
			for (Element val : values) {
				handler.writeExcel(index, COLAK, val.text());
				index++;
			}
		}
	}

	@Override
	public int setColIndex() {
		return COLY;
	}
	

	

}
