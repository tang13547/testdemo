package com.youe.cd.bddtest.dao;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.youe.cd.bddtest.pageobject.login.LoginPage;
import com.youe.cd.bddtest.util.Config;

import cn.easyproject.easyocr.EasyOCR;
import cn.easyproject.easyocr.ImageType;

public class EasyOcrDao {
	public static void screenshotForElement(WebDriver driver, WebElement element, String picFilePath) throws InterruptedException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Point p = element.getLocation();
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            Rectangle rect = new Rectangle(width-4, height-4);
            BufferedImage img = ImageIO.read(scrFile);
            BufferedImage dest = img.getSubimage(p.getX()+2, p.getY()+2, rect.width, rect.height);
            ImageIO.write(dest, "png", scrFile);
            Thread.sleep(1000);
            FileUtils.copyFile(scrFile, new File(picFilePath));
            Thread.sleep(1000);
        } catch (IOException e) {
            //e.printStackTrace();
        }
	}
	
	
	public static void cleanLinesInImage(File sfile, String destDir)  throws IOException{  
        File destF = new File(destDir);  
        if (!destF.exists())  
        {  
            destF.mkdirs();  
        }  

        BufferedImage bufferedImage = ImageIO.read(sfile);  
        int h = bufferedImage.getHeight();  
        int w = bufferedImage.getWidth();  

        // 灰度化  
        int[][] gray = new int[w][h];  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                int argb = bufferedImage.getRGB(x, y);  
                // 图像加亮（调整亮度识别率非常高）  
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);  
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);  
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);  
                if (r >= 255)  
                {  
                    r = 255;  
                }  
                if (g >= 255)  
                {  
                    g = 255;  
                }  
                if (b >= 255)  
                {  
                    b = 255;  
                }  
                gray[x][y] = (int) Math  
                        .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)  
                                * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);  
            }  
        }  

        // 二值化  
        int threshold = ostu(gray, w, h);  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                if (gray[x][y] > threshold)  
                {  
                    gray[x][y] |= 0x00FFFF;  
                } else  
                {  
                    gray[x][y] &= 0xFF0000;  
                }  
                binaryBufferedImage.setRGB(x, y, gray[x][y]);  
            }  
        }  

      

        // 矩阵打印  
        for (int y = 0; y < h; y++)  
        {  
            for (int x = 0; x < w; x++)  
            {  
                if (isBlack(binaryBufferedImage.getRGB(x, y)))  
                {  
                    //System.out.print("*");  
                } else  
                {  
                    //System.out.print(" ");  
                }  
            }  
            //System.out.println();  
        }  

        ImageIO.write(binaryBufferedImage, "jpg", new File(destDir, sfile  
                .getName()));  
    }  

    public static boolean isBlack(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300)  
        {  
            return true;  
        }  
        return false;  
    }  

    public static boolean isWhite(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 300)  
        {  
            return true;  
        }  
        return false;  
    }  

    public static int isBlackOrWhite(int colorInt)  
    {  
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730)  
        {  
            return 1;  
        }  
        return 0;  
    }  

    public static int getColorBright(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        return color.getRed() + color.getGreen() + color.getBlue();  
    }  

    public static int ostu(int[][] gray, int w, int h)  
    {  
        int[] histData = new int[w * h];  
        // Calculate histogram  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                int red = 0xFF & gray[x][y];  
                histData[red]++;  
            }  
        }  

        // Total number of pixels  
        int total = w * h;  

        float sum = 0;  
        for (int t = 0; t < 256; t++)  
            sum += t * histData[t];  

        float sumB = 0;  
        int wB = 0;  
        int wF = 0;  

        float varMax = 0;  
        int threshold = 0;  

        for (int t = 0; t < 256; t++)  
        {  
            wB += histData[t]; // Weight Background  
            if (wB == 0)  
                continue;  

            wF = total - wB; // Weight Foreground  
            if (wF == 0)  
                break;  

            sumB += (float) (t * histData[t]);  

            float mB = sumB / wB; // Mean Background  
            float mF = (sum - sumB) / wF; // Mean Foreground  

            // Calculate Between Class Variance  
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);  

            // Check if new maximum found  
            if (varBetween > varMax)  
            {  
                varMax = varBetween;  
                threshold = t;  
            }  
        }  

        return threshold;  
    }  

	
	public static String getTempVerifyCode(WebDriver driver, WebElement element, String picFilePath, String picFileDirAfterClean) throws Exception {
		screenshotForElement(driver, element, picFilePath);
		cleanLinesInImage(new File(picFilePath), picFileDirAfterClean);
		
		EasyOCR ocr = new EasyOCR();
		ocr.setTesseractOptions("-l eng -psm 10 digits");
		String tempVerifyCode = ocr.discernAndAutoCleanImage(picFileDirAfterClean + "\\test.png", ImageType.CAPTCHA_INTERFERENCE_LINE); //picFileDirAfterClean只是不个目录，所以要加上\\test.png路径
		
		return tempVerifyCode;
	}
	
	/**
	 * 通过循环获取并输入"正确的"验证码
	 * @param driver
	 * @param element
	 * @param picFilePath
	 * @return
	 * @throws Exception
	 */
	public static String getAndInputCorrectVerifyCode(WebDriver driver, WebElement element, String picFilePath, String picFilePathAfterClean) throws Exception {
	    String verifyCode = null;
	    boolean flag = true;

	    while(flag) {
		    //String verifyCode = TesseractOcrDao.getVerifyCode(driver, element, Config.picFilePath, Config.txtResultFilePath); //通过Dao层(TesseractOcrDao)获取验证码。注意必须在打开登录页面后使用
		    verifyCode = getTempVerifyCode(driver, element, Config.picFilePath, Config.picFileDirAfterClean); //通过Dao层(EasyOcrDao)获取验证码。注意必须在打开登录页面后使用
		    
		    LoginPage.getVerifyCodeElement(driver).clear();
		    LoginPage.getVerifyCodeElement(driver).sendKeys(verifyCode);
		    if(LoginPage.getErrorHintElement(driver).getText().equals(Config.errorHintStr)) {
		    	flag = true;
		    	element.click();
		    	System.out.println("获取到错误的验证码，刷新验证码并重新获取中...");
		    } else {
		    	flag = false;
		    	System.out.println("已获取到正确的验证码： " + verifyCode);
		    }
	    }
	    
	    return verifyCode;
	}
	
}
