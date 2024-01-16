package cdodata;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyticsWebsite extends Website{

	@Override
	public Elements setElements(Document doc) {
		return doc.select("details#station-metadata table");
	}

	@Override
	public void handleFrostFree(Element FFTable, int rowIndex) {
		iterateTable(rowIndex, FFTable, COLR);
		
	}

	@Override
	public int setColIndex() {
		return COLR;
	}

}
