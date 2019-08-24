package com.hellofresh.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {

	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;

	String path = System.getProperty("user.dir") + "/src/test/java/com/hellofresh/testData/TestData.xlsx";

	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public Object[][] testData(String sheetName) throws IOException {

		int rownum = XLUtils.getRowCount(path, sheetName);
		int colnum = getCellCount(path, sheetName, 1);

		Object readExcelData[][] = new Object[rownum][1];

		for (int i = 0; i < rownum; i++) {
			Map<Object, Object> rDataMap = new HashMap<Object, Object>();
			for (int j = 0; j < colnum; j++) {
				rDataMap.put(XLUtils.ws.getRow(0).getCell(j).toString(),
						XLUtils.ws.getRow(i + 1).getCell(j).toString());
			}
			readExcelData[i][0] = rDataMap;

		}
		return readExcelData;
	}

}
