package com.converter.ankitosql;

import static com.converter.ankitosql.Service.*;

public class App {

	public static void main(String[] args) {
		long startTime, endTime, resultTime;
		startTime = System.currentTimeMillis();
		printHeader();
		
		String filePath = "";
		String inputString = "";
		
		try{
		inputString = args[0];
		} catch (IndexOutOfBoundsException e){
			e.getMessage();
		}
		
		filePath = getCurrentPathToTheFile(inputString);
		ZipManager zipManager = new ZipManager(filePath);
        zipManager.unZip();
        
        DBUtil dbUtil = new DBUtil(ankiFilePath);
        
        
        writeSQLFile(dbUtil.selectAll());
        deleteFile(ankiFilePath);
        endTime = System.currentTimeMillis();
        
        resultTime = endTime-startTime;
        System.out.println("____________________________________");
        System.out.println("Successfully finished! Elapsed time: " + String.format("%,12d", resultTime) + " ms");
	}

}
