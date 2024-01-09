package cdodata;

import java.io.IOException;
import java.util.Scanner;

public class GenerateData {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the file name for Excel: ");
		String excel = input.nextLine();
		System.out.println("Enter the sheet in Excel you want to write to: ");
		String sheet = input.nextLine();
		System.out.println("Enter the website you want to extract from: ");
		String web = input.nextLine();
		
		ExcelHandler.openWorkbook(excel, sheet);
		try {
			WebsiteScraper.parseWebsite(web);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExcelHandler.closeWorkbook();
		input.close();

	}

}
