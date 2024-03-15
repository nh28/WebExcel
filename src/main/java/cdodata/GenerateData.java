package cdodata;

import java.io.IOException;
import java.util.Scanner;

public class GenerateData {

	/* WARNINGS: 
	 * Names are case sensitive, make sure spacing, spelling, etc. in excel is the same as web
	 * Need to add all table titles in order for it to populate all the data
	 * Excel formatting is weird, so have to enter numbers as numbers, not as text
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Data file names for Excel: ");
		String excel1 = "1991-2020 Normals - Data Plan - DataT_EN.xlsx";
		String excel2 = "1991-2020 Normals - Data Plan - DataT_FR.xlsx";
		
		System.out.println("Enter the Analytics file names for Excel: ");
		String excel3 = "1991-2020 Normals - Data Plan - AnalyticsT_EN.xlsx";
		String excel4 = "1991-2020 Normals - Data Plan - AnalyticsT_FR.xlsx";
				
		String sheetData = "Data- Archive-Web Tables";
		String sheetDataFR = "Data Archive Table-Web Table";
		String sheetAnalytics =	"Analytic- Archive-Web Tables"; 
				
		System.out.println("Enter the websites (EN and FR) you want to extract from: ");
		String webEN = "https://climate.weather.gc.ca/climate_normals/results_1991_2020_e.html?searchType=stnName_1991&txtStationName_1991=lookout&searchMethod=contains&txtCentralLatMin=0&txtCentralLatSec=0&txtCentralLongMin=0&txtCentralLongSec=0&stnID=201000000&dispBack=1";
		String webFR = "https://climat.meteo.gc.ca/climate_normals/results_1991_2020_f.html?searchType=stnName_1991&txtStationName_1991=lookout&searchMethod=contains&txtCentralLatMin=0&txtCentralLatSec=0&txtCentralLongMin=0&txtCentralLongSec=0&stnID=201000000&dispBack=1";
		
		try {
			System.out.println("Getting Data...Please Wait");
			
			DataWebsite dataEN = new DataWebsite(excel1, sheetData);
			dataEN.parseWebsite(webEN, dataEN.handler);
			System.out.println("1/4 complete");
			dataEN.handler.closeWorkbook();
			
			DataWebsite dataFR = new DataWebsite(excel2, sheetDataFR);
			dataFR.parseWebsite(webFR, dataFR.handler);
			System.out.println("2/4 complete");
			dataFR.handler.closeWorkbook();
			
			AnalyticsWebsite analyticsEN = new AnalyticsWebsite(excel3, sheetAnalytics);
			analyticsEN.parseWebsite(webEN, analyticsEN.handler);
			System.out.println("3/4 complete");
			
			AnalyticsWebsite analyticsFR = new AnalyticsWebsite(excel4, sheetAnalytics);
			analyticsFR.parseWebsite(webFR, analyticsFR.handler);
			System.out.println("4/4 complete");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("Done");

	}

}
