package com.youe.cd.test.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TxtDao {
	/**
	 * 通过读取txt文件生成并返回ArrayList<String>类型数据
	 * @param txtFilePath
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> getTxtList(String txtFilePath) throws Exception {
		String encoding="UTF-8";
		ArrayList<String> txtList = new ArrayList<String>();
		String oneLine;
		File file=new File(txtFilePath);
		if(file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			/*while((lineTxt = bufferedReader.readLine()) != null) {
				//System.out.println(lineTxt);
				//paramSearchBaidu(driver, lineTxt);				
			}*/
			while((oneLine = bufferedReader.readLine()) != null) {
				txtList.add(oneLine);
			}			
			read.close();			
		} else {
			System.out.println("找不到指定的文件:" + txtFilePath);			
		}
		
		return txtList;
	}
	
}
