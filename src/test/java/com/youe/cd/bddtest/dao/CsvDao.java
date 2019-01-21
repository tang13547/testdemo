package com.youe.cd.bddtest.dao;

import java.nio.charset.Charset;
import java.util.ArrayList;
import com.csvreader.CsvReader;

public class CsvDao {
	/**
	 * 通过读取csv文件生成并返回ArrayList<String[])类型数据
	 * @param csvFilePath
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String[]> getCsvList(String csvFilePath) throws Exception {
	    ArrayList<String[]> csvList = new ArrayList<String[]>();
	    CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("SJIS"));
	    reader.readHeaders(); // 跳过表头,如果需要表头的话，不要写这句
	    
	    while(reader.readRecord()) {
	    	csvList.add(reader.getValues());
	    }
	    reader.close();
	    
	    return csvList;
	}

}
