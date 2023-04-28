package org.jgrpah.archetypes.GraphTheoryProject;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class Test {
	public void readFile(String file) {
		
		File file = new File("Geeks.xlsx");
		  
        // Create a FileInputStream object
        // for getting the information of the file
        FileInputStream fip = new FileInputStream(file);
  
        // Getting the workbook instance for XLSX file
        XSSFWorkbook workbook = new XSSFWorkbook(fip);
  
        // Ensure if file exist or not
        if (file.isFile() && file.exists()) {
            System.out.println("Geeks.xlsx open");
        }
        else {
            System.out.println("Geeks.xlsx either not exist"
                               + " or can't open");
        }
		
	}
	
	public static void main(String[] args) {
		
	}
}
