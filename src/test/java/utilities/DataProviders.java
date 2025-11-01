package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException{
		
		String path = ".\\testData\\User_LoginData.xlsx";	//store excel file path
		
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1");	//check sheet name from excel file
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		
		String loginData[][] = new String[totalrows][totalcols];	//created 2D array which stored data from excel
		
		for(int i=1; i<=totalrows; i++) {
			for(int j=0; j<totalcols; j++) {
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
		return loginData;
	}

}
