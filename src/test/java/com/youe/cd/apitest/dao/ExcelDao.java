package com.youe.cd.apitest.dao;

import com.youe.cd.apitest.util.Config;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;

import java.io.*;

import java.io.File;
import java.io.IOException;

//import jxl.Cell;
import jxl.write.*;


public class ExcelDao extends PoiExcelDao {
    /**
     * 获取Excel中第1个sheet页。注意：返回为Sheet类型数据
     * @param excelFilePath excel文件路径
     * @return Sheet数据类型
     */
    /*public static Sheet getExcelSheet(String excelFilePath) {
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
    }*/

    /**
     * 获取Excel中第sheetNum（从0开始）个sheet页。注意：返回为Sheet类型数据
     * @param excelFilePath excel文件路径
     * @param //sheetNum 从0开始，从工作区中获取第几个sheet页
     * @return Sheet数据类型
     */
    /*public static Sheet getExcelSheet(String excelFilePath, int sheetNum) {
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
    }*/

    /*2public static int getExcelSheetRowsSize(String excelFilePath) {
        Sheet sheet = getExcelSheet(excelFilePath);
        int rowsSize = sheet.getRows();
        return rowsSize;
    }

    public static String getCellContent(String excelFilePath, int x, int y) {
        String cellContentStr = null;

        try {
            File file = new File(excelFilePath);
            Workbook wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0); //从工作区中获取sheet页（从0开始, 此方法的默认值）
            cellContentStr = sheet.getCell(x, y).getContents();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellContentStr;
    }*/

    /*public static String getCellContent(String excelFilePath, String excelCoordinate) {
        String cellContentStr = null;

        try {
            File file = new File(excelFilePath);
            Workbook wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0); //从工作区中获取sheet页（从0开始, 此方法的默认值）
            cellContentStr = sheet.getCell(excelCoordinate).getContents();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellContentStr;
    }*/

    /*2public static void addContentToExcel(String excelFilePath, int x, int  y, String cellContentStr) {
        Label label = null;
        WritableCellFormat wcf = new WritableCellFormat();

        try {

            label = new Label(x, y, cellContentStr);

            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcf.setAlignment(Alignment.CENTRE);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
            wcf.setFont(new WritableFont(WritableFont.createFont("宋体"), 12));

            if (x == Config.actualResponseBodyColumnNum) {
                wcf.setAlignment(Alignment.LEFT); //只有此处修改为左对齐
                wcf.setBackground(Colour.GOLD);
                label = new Label(x, y, cellContentStr, wcf);
            } else if (x == Config.actualResponseCodeColumnNum) {
                wcf.setBackground(Colour.GOLD);
                label = new Label(x, y, cellContentStr, wcf);
            } else if (x == Config.runTimeColumnNum) {
                wcf.setAlignment(Alignment.LEFT); //此处也修改为左对齐
                wcf.setBackground(Colour.GRAY_25);
                label = new Label(x, y, cellContentStr, wcf);
            }

            if (cellContentStr.equals("Pass")) {
                wcf.setBackground(Colour.GREEN);
                label = new Label(x, y, cellContentStr, wcf);
            } else if (cellContentStr.equals("Fail")) {
                wcf.setBackground(Colour.RED);
                label = new Label(x, y, cellContentStr, wcf);
            }

            //OutputStream os = new FileOutputStream(excelFilePath);

            File file = new File(excelFilePath);
            Workbook wb = Workbook.getWorkbook(file);
            WritableWorkbook wwb = Workbook.createWorkbook(file, wb);
            //WritableSheet wSheet = wwb.createSheet("Sheet1", 0);
            WritableSheet wSheet = wwb.getSheet(0);
            wSheet.addCell(label);
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*2public static void addContentToExcel(String excelFilePath, int x, int  y, int cellContentInt) {
        //Label label = new Label(x, y, cellContentStr);
        jxl.write.Number number = new jxl.write.Number(x, y, cellContentInt);

        try {
            //OutputStream os = new FileOutputStream(excelFilePath);

            File file = new File(excelFilePath);
            Workbook wb = Workbook.getWorkbook(file);
            WritableWorkbook wwb = Workbook.createWorkbook(file, wb);
            WritableSheet wSheet = wwb.getSheet(0);
            wSheet.addCell(number);
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*public static void addContentToExcel(String excelFilePath, int sheetNum, int x, int  y, String cellContentStr) {
        Label label = new Label(x, y, cellContentStr);

        try {
            //OutputStream os = new FileOutputStream(excelFilePath);

            File file = new File(excelFilePath);
            Workbook wb = Workbook.getWorkbook(file);
            WritableWorkbook wwb = Workbook.createWorkbook(file, wb);
            //WritableSheet wSheet = wwb.createSheet("Sheet1", 0);
            WritableSheet wSheet = wwb.getSheet(sheetNum);
            wSheet.addCell(label);
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*2public static int getSizeY(String excelFilePath) {
        int sizeY = 0;
        int allRows = getExcelSheetRowsSize(excelFilePath);
        //System.out.println("allRows including table or form is :" + allRows);

        for(int i=1; i<allRows; i++){
            if(!getCellContent(excelFilePath, 0, i).equals("") && getCellContent(excelFilePath, 0, i) != null) {
                sizeY++;
            }
        }

        //System.out.println("sizeY is : " + sizeY);
        return sizeY;
    }*/


}

