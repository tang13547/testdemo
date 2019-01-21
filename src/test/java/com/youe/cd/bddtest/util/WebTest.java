package com.youe.cd.bddtest.util;

import com.youe.cd.bddtest.dao.CsvDao;
import com.youe.cd.bddtest.dao.EasyOcrDao;
import com.youe.cd.bddtest.dao.ExcelDao;
import com.youe.cd.bddtest.pageobject.login.LoginPage;
import jxl.Sheet;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

//7 import org.junit.*;
//7 import static org.junit.Assert.*;
//import java.util.HashSet;


public class WebTest {
  //2 protected static WebDriver driver;
  //2 private String baseUrl;
  private boolean acceptNextAlert = true;
  private static StringBuffer verificationErrors = new StringBuffer();

  //@BeforeTest
  //@Parameters({"testEnv","testBrowser"}) //只需在最上层加入，此处不要
  public static WebDriver setUp(WebDriver driver, String testEnv, String testBrowser) throws Exception {  //必须返回 Webdriver,否则无法传递给主类中的属性
	if(testBrowser.equals("firefox")) {
		//ProfilesIni allProfiles =new ProfilesIni();
		//FirefoxProfile profile = allProfiles.getProfile("SeleniumProfile"); //修改值为: SeleniumProfile
		System.setProperty("webdriver.gecko.driver", Config.firefoxdriverPath);
		driver = new FirefoxDriver(); //处理取消每日提醒加入了profile类对象
		driver.manage().window().maximize(); //最大化窗口
	} else if (testBrowser.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", Config.chromedriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments(new String[] {"-test-type"}); //去掉Chrome上的yellow alarm
		options.addArguments(new String[] {"-start-maximized"}); //最大化窗口	
		//options.setExperimentalOption("excludeSwitches","ignore-certificate-errors"); //此方法未验证通过
		driver = new ChromeDriver(options);
	}	
	
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    return driver;
  }

  //@Test
  public static void login(WebDriver driver,String baseUrl) throws Exception {
    driver.get(baseUrl);  //打开网页首页
    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click(); //点击"登录”链接打开登录窗口
    
    LoginPage.getUserNameElement(driver).clear();
    LoginPage.getUserNameElement(driver).sendKeys("monkey1");
    
    LoginPage.getPasswordElement(driver).clear();;
    LoginPage.getPasswordElement(driver).sendKeys("monkey1");
    
    LoginPage.getSubmitElement(driver).click();
    
    Thread.sleep(5000);
    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
  }
  
  public static void loginWithVerifyCode(WebDriver driver,String baseUrl) throws Exception {
   	    driver.get(baseUrl);  //打开网页首页
	    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click(); //点击"登录”链接打开登录窗口
	    	    
	    LoginPage.getUserNameElement(driver).clear();
	    LoginPage.getUserNameElement(driver).sendKeys("monkey1");
	    
	    LoginPage.getPasswordElement(driver).clear();;
	    LoginPage.getPasswordElement(driver).sendKeys("monkey1");
	    
        WebElement element = LoginPage.getVerifyCodeImgElement(driver);
	    EasyOcrDao.getAndInputCorrectVerifyCode(driver, element, Config.picFilePath, Config.picFileDirAfterClean); //通过Dao层（EasyOcrDao）获取并输入"正确的"验证码
	    
	    Thread.sleep(2000);
	    
	    LoginPage.getSubmitElement(driver).click();	    
	    Thread.sleep(5000);
	    
	  }
  
  public static void loginByCsv(WebDriver driver,String baseUrl,String csvFilePath) throws Exception {        
  	  	ArrayList<String[]> csvList = new ArrayList<String[]>();
	  	csvList = CsvDao.getCsvList(csvFilePath);  //通过Dao层读取csv文件
	    
	    driver.get(baseUrl);  //打开网页首页
	    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click(); //点击"登录”链接打开登录窗口
	    
	    LoginPage.getUserNameElement(driver).clear();
	    LoginPage.getUserNameElement(driver).sendKeys(csvList.get(0)[0]);
	    
	    LoginPage.getPasswordElement(driver).clear();
	    LoginPage.getPasswordElement(driver).sendKeys(csvList.get(0)[1]);
	    
	    LoginPage.getSubmitElement(driver).click();	    
	    Thread.sleep(5000);
	    
  }
  
  public static void loginWithVerifyCodeByCsv(WebDriver driver,String baseUrl,String csvFilePath) throws Exception {        
	  	ArrayList<String[]> csvList = new ArrayList<String[]>();
	  	csvList = CsvDao.getCsvList(csvFilePath);  //通过Dao层读取csv文件
	 	    
	    driver.get(baseUrl);  //打开网页首页
	    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click();
        
	    LoginPage.getUserNameElement(driver).clear();
	    LoginPage.getUserNameElement(driver).sendKeys(csvList.get(0)[0]);
	    
	    LoginPage.getPasswordElement(driver).clear();
	    LoginPage.getPasswordElement(driver).sendKeys(csvList.get(0)[1]);
  
        WebElement element = LoginPage.getVerifyCodeImgElement(driver);
	    EasyOcrDao.getAndInputCorrectVerifyCode(driver, element, Config.picFilePath, Config.picFileDirAfterClean); //通过Dao层（EasyOcrDao）获取并输入"正确的"验证码
	    
	    Thread.sleep(2000);
	    
	    LoginPage.getSubmitElement(driver).click();	    
	    Thread.sleep(5000);
	    
  }
  
  public static void loginByExcel(WebDriver driver,String baseUrl) throws Exception {        
	  	Sheet excelSheet = null;
	  	excelSheet = ExcelDao.getExcelSheet(Config.excelFilePath);  //通过Dao层读取excel文件
	 	    
	    driver.get(baseUrl);  //打开网页首页
	    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click();
    
	    LoginPage.getUserNameElement(driver).clear();
	    LoginPage.getUserNameElement(driver).sendKeys(excelSheet.getCell(0,1).getContents());
	    
	    LoginPage.getPasswordElement(driver).clear();
	    LoginPage.getPasswordElement(driver).sendKeys(excelSheet.getCell(1,1).getContents());
	    
	    LoginPage.getSubmitElement(driver).click();	    
	    Thread.sleep(5000);
	    
}
  
  public static void loginWithVerifyCodeByExcel(WebDriver driver,String baseUrl,String excelFilePath) throws Exception {        
	  	Sheet excelSheet = null;
	  	excelSheet = ExcelDao.getExcelSheet(excelFilePath, 0);  //通过Dao层读取excel文件
	 	    
	    driver.get(baseUrl);  //打开网页首页
	    //driver.findElement(By.xpath(".//*[@id='u1']/a[7]")).click();
      
	    LoginPage.getUserNameElement(driver).clear();
	    LoginPage.getUserNameElement(driver).sendKeys(excelSheet.getCell(0,1).getContents());
	    
	    LoginPage.getPasswordElement(driver).clear();
	    LoginPage.getPasswordElement(driver).sendKeys(excelSheet.getCell(1,1).getContents());

	    WebElement element = LoginPage.getVerifyCodeImgElement(driver);
	    EasyOcrDao.getAndInputCorrectVerifyCode(driver, element, Config.picFilePath, Config.picFileDirAfterClean); //通过Dao层（EasyOcrDao）获取并输入"正确的"验证码
	    
	    Thread.sleep(2000);
	    
	    LoginPage.getSubmitElement(driver).click();	    
	    Thread.sleep(5000);
	    
  }
  
  /**
   * 通过Cookie登陆
   * @param driver
   * @param baseUrl
   * @param cookieName
   * @param cookieValue
   * @param cookieName2
   * @param cookieValue2
   * @throws Exception
   */
  public static void loginByCookie(WebDriver driver,String baseUrl,String cookieName,String cookieValue, String cookieName2, String cookieValue2) throws Exception {
	    driver.get(baseUrl);  //打开网页首页

	    driver.manage().deleteAllCookies();
	    
		Cookie cookie = new Cookie(cookieName, cookieValue);  //在F12->存储->Cookie中去找
		driver.manage().addCookie(cookie);
		Cookie cookie2 = new Cookie(cookieName2, cookieValue2);  //在F12->存储->Cookie中去找
		driver.manage().addCookie(cookie2);
		driver.navigate().refresh();
	    Thread.sleep(5000);
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	    
	    //return driver;
  }
  
  //搜索，再打开链接页面中的登出
  public static void logoutBaidu(WebDriver driver) throws Exception {
	  Actions a = new Actions(driver);	
	  a.moveToElement(driver.findElement(By.id("user-name"))).perform();//要用a.必须放在一个类的方法中。
	  driver.findElement(By.className("sub-list")).findElement(By.id("userbar-logout")).click();
	  Thread.sleep(3000);
  }

  public static void logoutBaiduInSearchPage(WebDriver driver) throws Exception {
	  Actions a = new Actions(driver);	
	  a.moveToElement(driver.findElement(By.id("user"))).perform();//要用a.必须放在一个类的方法中。
	  driver.findElement(By.className("usermenu")).findElement(By.className("logout")).click();
	  Thread.sleep(3000);
  }
  
 //有dialog对话框的登出
  public static void logoutBaiduInHomePage(WebDriver driver) throws Exception {
	  Actions a = new Actions(driver);	
	  a.moveToElement(driver.findElement(By.id("s_username_top"))).perform();//要用a.必须放在一个类的方法中。
	  //去掉一级定位findElement(By.id("s_user_name_menu"))后，还是可以正常运行
	  driver.findElement(By.className("quit")).click();
	  Thread.sleep(3000);	
	  driver.findElement(By.xpath(".//*[@id='tip_con_wrap']/div[3]/a[3]")).click(); //验证用xpath成功
	  //driver.findElement(By.id("tip_con_wrap")).findElement(By.className("s-btn btn-ok")).click(); //不成功
	  Thread.sleep(3000);	  
  }
  
  //@AfterTest
  public static void tearDown(WebDriver driver) throws Exception {
    driver.quit();
    //静态方法中，默认verificationErrorString为static,所以必须将verificationErrors定义为类中全局staic类型
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  /**
   * 切换到最后一个窗口
   * @param driver
   */
  public static void switchToLastWindow(WebDriver driver) {
	    Set<String> allhandles = driver.getWindowHandles();
	    Iterator<String> it = allhandles.iterator();        
	    while(it.hasNext()) {    	
	    	String currenthandle = it.next(); //首次使用指向第一个String handle,即从0开始
	    	System.out.println("currenthandle is " + currenthandle);	
  	        //if (!homehandle.equals(currenthandle)) {
	        	driver.switchTo().window(currenthandle);
	    		//break;
	    	//}        
	    } 
  }

  private boolean isElementPresent(WebDriver driver,By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent(WebDriver driver) {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText(WebDriver driver) {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
}

