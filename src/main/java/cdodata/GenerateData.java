package cdodata;

import java.io.IOException;
import java.util.Scanner;

public class GenerateData {

	/* WARNINGS: 
	 * Names are case sensitive, make sure spacing, spelling, etc. in excel is the same as web
	 * Need to add all table titles in order for it to populate all the data
	 * Have to manually do frost free
	 * Some little mistakes due to dev site being different than published site (testing with published site)
	 * Only for the normals data FOR NOW
	 * Excel formatting is weird, so have to enter numbers as numbers, not as text
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the file name for Excel: ");
		String excel = "1991-2020 - Normals - Data Plan - HAY RIVER - AnalyticsT_EN.xlsx";
				//input.nextLine();
		System.out.println("Enter the sheet in Excel you want to write to: ");
		String sheet = "Analytic- Archive-Web Tables"; 
				//input.nextLine();
		System.out.println("Enter the website you want to extract from: ");
		String web = "https://climate-dev.cmc.ec.gc.ca/climate_normals/results_1991_2020_e.html?searchType=stnName_1991&txtStationName_1991=hay+river&searchMethod=contains&txtCentralLatMin=0&txtCentralLatSec=0&txtCentralLongMin=0&txtCentralLongSec=0&stnID=407000000&dispBack=1";
				//input.nextLine();
		System.out.println("Data[D] or Analytics[A]?");
		String type = input.nextLine();
		
		ExcelHandler.openWorkbook(excel, sheet);
		try {
			System.out.println("Getting the data...please wait");
			if (type.equals("D")) {
				DataWebsite data = new DataWebsite();
				data.parseWebsite(web);
			}
			else {
				AnalyticsWebsite analytics = new AnalyticsWebsite();
				analytics.parseWebsite(web);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ExcelHandler.workbook != null) {
			ExcelHandler.closeWorkbook();
		}
		input.close();
		System.out.println("Done");

	}

}
