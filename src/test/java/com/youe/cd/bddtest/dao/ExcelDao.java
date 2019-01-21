package com.youe.cd.bddtest.dao;

import java.io.File;
import java.io.IOException;

//import jxl.Cell;  
import jxl.Sheet;  
import jxl.Workbook;  
import jxl.read.biff.BiffException; 

import com.youe.cd.bddtest.util.Config;

public class ExcelDao {
	/**
	 * 获取Excel中第1个sheet页。注意：返回为Sheet类型数据
	 * @param excelFilePath excel文件路径
	 * @return Sheet数据类型
	 */
	public static Sheet getExcelSheet(String excelFilePath) {
		Sheet sheet = null;
		
		try {
			File file = new File(excelFilePath);
			Workbook wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0); //从工作区中获取sheet页（从0开始, 此方法的默认值）
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sheet;
	}
	
	/**
	 * 获取Excel中第sheetNum（从0开始）个sheet页。注意：返回为Sheet类型数据
	 * @param excelFilePath excel文件路径
	 * @param sheetNum 从0开始，从工作区中获取第几个sheet页
	 * @return Sheet数据类型
	 */
	public static Sheet getExcelSheet(String excelFilePath, int sheetNum) {
		Sheet sheet = null;
		
		try {
			File file = new File(excelFilePath);
			Workbook wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(sheetNum); //从工作区中获取sheet页（从0开始）
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sheet;
	}
}
