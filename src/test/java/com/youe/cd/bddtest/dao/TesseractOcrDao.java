package com.youe.cd.test.dao;


import java.awt.Rectangle; //需使用awt的Rectangle
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point; //需使用selenium的Point
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TesseractOcrDao {
	/**
	 * 通过element元素截图, 并将截取的图片保存在第三个参数picFilePath中 
	 * @param driver
	 * @param element
	 * @param picFilePath
	 * @throws InterruptedException
	 */
	public static void screenshotForElement(WebDriver driver, WebElement element, String picFilePath) throws InterruptedException {
	        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        try {
	            Point p = element.getLocation();
	            int width = element.getSize().getWidth();
	            int height = element.getSize().getHeight();
	            Rectangle rect = new Rectangle(width, height);
	            BufferedImage img = ImageIO.read(scrFile);
	            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
	            ImageIO.write(dest, "png", scrFile);
	            Thread.sleep(1000);
	            FileUtils.copyFile(scrFile, new File(picFilePath));
	            Thread.sleep(1000);
	        } catch (IOException e) {
	            //e.printStackTrace();
	        }
	}
	
	/**
	 * 通过Tesseract-OCR将pic转换成txt文件
	 * @param picFilePath pic图片存放的路径
	 * @param txtResultFilePath OCR工具转换pic为txt文件的存放路径，注意不带.txt后缀
	 * @throws Exception
	 */
	public static void convertPicToTxtByTesseractOCR(String picFilePath, String txtResultFilePath) throws Exception {
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd.exe /C start D:\\Tesseract-OCR\\tesseract.exe " + picFilePath + " " + txtResultFilePath + " -l eng");	 //eng或chi_sim	
	}
	
	/**
	 * 获取验证码，仅含：pic转换成txt + 从txt中提取文字，不包括第一步截图。若要截图，请先添加截图方法调用
	 * @param picFilePath
	 * @param txtResultFilePath
	 * @return
	 * @throws Exception
	 */
	public static String getVerifyCodeWithoutScreenshot(String picFilePath, String txtResultFilePath) throws Exception {
		//String verifyCode = null; //在有try和catch的情况下，返回的变量需要初使化?
		//screenshotForElement(driver, element, picFilePath);
		convertPicToTxtByTesseractOCR(picFilePath, txtResultFilePath);
		Thread.sleep(3000); //非常重要，上一步通过tesseract ocr转换pic为txt需要时间
		String verifyCode = TxtDao.getTxtList(txtResultFilePath + ".txt").get(0);  //注意：txtResultFilePath没有带.txt文件后缀，所以此处必须加上
		
		return verifyCode;
	}
	
	/**
	 * 直接（一步）获取验证码，含：截图 + pic转换成txt + 从txt中提取文字
	 * @param driver
	 * @param element
	 * @param picFilePath
	 * @param txtResultFilePath
	 * @return
	 * @throws Exception
	 */
	public static String getVerifyCode(WebDriver driver, WebElement element, String picFilePath, String txtResultFilePath) throws Exception {
		//String verifyCode = null; //在有try和catch的情况下，返回的变量需要初使化?
		screenshotForElement(driver, element, picFilePath);
		convertPicToTxtByTesseractOCR(picFilePath, txtResultFilePath);
		Thread.sleep(3000); //非常重要，上一步通过tesseract ocr转换pic为txt需要时间
		String verifyCode = TxtDao.getTxtList(txtResultFilePath + ".txt").get(0);  //注意：txtResultFilePath没有带.txt文件后缀，所以此处必须加上
		
		return verifyCode;
	}
	
	
}
