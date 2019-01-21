package com.youe.cd.bddtest.pageobject.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	private static WebElement element = null;
	
/*	public static WebElement getUserNameElement(WebDriver driver) {
		element = driver.findElement(By.id("TANGRAM__PSP_10__userName"));
		return element;
	}
	
	public static WebElement getPasswordElement(WebDriver driver) {
		element = driver.findElement(By.id("TANGRAM__PSP_10__password"));
		return element;
	}

	public static WebElement getVerifyCodeElement(WebDriver driver) {
		element = driver.findElement(By.id("TANGRAM__PSP_10__verifyCode"));
		return element;
	}
	
	public static WebElement getVerifyCodeImgElement(WebDriver driver) {
		element = driver.findElement(By.id("TANGRAM__PSP_10__verifyCodeImg"));
		return element;
	}
	
	public static WebElement getSubmitElement(WebDriver driver) {
		element = driver.findElement(By.id("TANGRAM__PSP_10__submit"));
		return element;
	}*/
	
	public static WebElement getUserNameElement(WebDriver driver) {
		element = driver.findElement(By.id("username"));
		return element;
	}
	
	public static WebElement getPasswordElement(WebDriver driver) {
		element = driver.findElement(By.id("password"));
		return element;
	}

	public static WebElement getVerifyCodeElement(WebDriver driver) {
		element = driver.findElement(By.id("vcode"));
		return element;
	}
	
	public static WebElement getVerifyCodeImgElement(WebDriver driver) {
		element = driver.findElement(By.id("valicodeImg"));
		return element;
	}
	
	//获取输入错误验证后的错误提示Element
	public static WebElement getErrorHintElement(WebDriver driver) {
		element = driver.findElement(By.className("errorHint"));
		return element;
	}
	
	public static WebElement getSubmitElement(WebDriver driver) {
		element = driver.findElement(By.xpath(".//*[@id='login_form']/div/div[2]/div[3]/button"));
		return element;
	}
	
}
