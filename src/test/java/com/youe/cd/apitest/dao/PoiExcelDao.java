package com.youe.cd.apitest.dao;

import java.io.*;

import com.youe.cd.apitest.util.Config;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelDao {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void checkExcelValid(String excelFilePath) throws Exception{
        File file = new File(excelFilePath);

        if(!file.exists()) {
            throw new Exception("文件不存在: " + excelFilePath);
        }

        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel: " + excelFilePath);
        }
    }

    public static Workbook getWorkBook(String excelFilePath) {
        Workbook wb = null;
        //Sheet sheet = null;

        try {
            checkExcelValid(excelFilePath);

            File file = new File(excelFilePath);
            FileInputStream fis = new FileInputStream(file);

            if(file.getName().endsWith(EXCEL_XLS)) {
                wb = new HSSFWorkbook(fis);
            } else if(file.getName().endsWith(EXCEL_XLSX)) {
                wb = new XSSFWorkbook(fis);
            }

            //sheet = wb.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wb;
    }

    public static Sheet getExcelSheet(String excelFilePath) {
        Workbook wb = null;
        Sheet sheet = null;

        try {
            checkExcelValid(excelFilePath);

            File file = new File(excelFilePath);
            FileInputStream fis = new FileInputStream(file);

            if(file.getName().endsWith(EXCEL_XLS)) {
                wb = new HSSFWorkbook(fis);
            } else if(file.getName().endsWith(EXCEL_XLSX)) {
                wb = new XSSFWorkbook(fis);
            }

            sheet = wb.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public static Sheet getExcelSheet(String excelFilePath, int sheetNum) {
        Workbook wb = null;
        Sheet sheet = null;

        try {
            checkExcelValid(excelFilePath);

            File file = new File(excelFilePath);
            FileInputStream fis = new FileInputStream(file);

            if(file.getName().endsWith(EXCEL_XLS)) {
                wb = new HSSFWorkbook(fis);  //打开workbook
            } else if(file.getName().endsWith(EXCEL_XLSX)) {
                wb = new XSSFWorkbook(fis);  //打开workbook
            }

            sheet = wb.getSheetAt(sheetNum);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sheet;
    }

    /**
     * 行数(从0开始）,注意：包括所有有使用的行（包括表格等）
     * @param excelFilePath
     * @return
     */
    public static int getExcelSheetRowsSize(String excelFilePath) {
        Sheet sheet = getExcelSheet(excelFilePath);
        int rowsSize = sheet.getLastRowNum();

        return rowsSize;
    }

    public static String getCellContent(String excelFilePath, int x, int y) {
        String cellContentStr = null;

        Sheet sheet = getExcelSheet(excelFilePath, 0);
        Row row = sheet.getRow(y);
        Cell cell = row.getCell(x);
        cell.setCellType(CellType.STRING);  //重要：设置cell类型。有number类型的数据时，需要把它转化为纯String类型
        cellContentStr = cell.getStringCellValue();

        return cellContentStr;
    }

    public static void addContentToExcel(String excelFilePath, int x, int  y, String cellContentStr) {
        Workbook wb = getWorkBook(excelFilePath);
        Sheet sheet = wb.getSheetAt(0);

        CellStyle cellStyle = wb.createCellStyle();

        Row row = sheet.getRow(y);
        if(row == null) {
            row = sheet.createRow(y);
        }
        Cell cell = row.createCell(x);  //先创建cell后，再修改type, value和style
        cell.setCellType(CellType.STRING);  //重要：设置cell类型。比如：有number类型的数据时，需要把它转化为纯String类型

        //设置默认样式及字体
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment((VerticalAlignment.CENTER));

        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);

        switch(x) {
            case Config.actualResponseBodyColumnNum:
                cellStyle.setAlignment(HorizontalAlignment.LEFT);  //只有此处修改为左对齐
                cellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case Config.actualResponseCodeColumnNum:
                cellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case Config.runTimeColumnNum:
                cellStyle.setAlignment(HorizontalAlignment.LEFT);  //此处也修改为左对齐
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
        }

        if (cellContentStr.equals("Pass")) {
            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (cellContentStr.equals("Fail")) {
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        cell.setCellValue(cellContentStr);
        cell.setCellStyle(cellStyle);

        try {
            FileOutputStream fos = new FileOutputStream(excelFilePath);
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取有效行数（比如：测试用例为4条，则sizeY为4)
     * @param excelFilePath
     * @return
     */
    public static int getSizeY(String excelFilePath) {
        int sizeY = 0;
        int allRows = getExcelSheetRowsSize(excelFilePath);
        //System.out.println("allRows including table or form is :" + allRows);

        for(int i=1; i<=allRows; i++){
            if(!getCellContent(excelFilePath, 0, i).equals("") && getCellContent(excelFilePath, 0, i) != null) {
                sizeY++;
            }
        }

        //System.out.println("sizeY is : " + sizeY);
        return sizeY;
    }

    public static void main(String[] args) {
        //Sheet sheet = getExcelSheet(Config.excelFilePath);
        String contentStr = getCellContent(Config.excelFilePath, 2, 1);
        int rowsSize = getExcelSheetRowsSize(Config.excelFilePath);
        int sizeY = getSizeY(Config.excelFilePath);

        System.out.println("contentStr为：" + contentStr);
        System.out.println("rowsSize为：" + rowsSize);
        System.out.println("sizeY为：" + sizeY);

        addContentToExcel(Config.excelFilePath, 11, 1, "aass");
        addContentToExcel(Config.excelFilePath, 12, 1, "200");
        addContentToExcel(Config.excelFilePath, 15, 1, "Pass");
    }
}
