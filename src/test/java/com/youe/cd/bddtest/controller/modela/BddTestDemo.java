package com.youe.cd.bddtest.controller.modela;

import com.youe.cd.bddtest.util.Config;
import com.youe.cd.bddtest.util.WebTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

public class BddTestDemo {
    public String keyword = null;
    WebDriver driver;

    @Given("^I go to \"(.*?)\"$")
    public void step_openWeb(String url) {
        try {
            driver = WebTest.setUp(driver, "test", "chrome");
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.get(url);
    }

    @When("^I fill in field with id \"(.*?)\" with \"(.*?)\"$")
    public void step_inputContent(String inputBoxId, String searchKey) {
        driver.findElement(By.id(inputBoxId)).clear();
        driver.findElement(By.id(inputBoxId)).sendKeys(searchKey);
    }

    @And("^I click id \"(.*?)\" with baidu once$")
    public void step_clickSearchButton(String searchButtonId) throws Exception {
        driver.findElement(By.id(searchButtonId)).click();

        Thread.sleep(3000); //非常重要，要等页面打开后再断言

    }

    @Then("^I should see \"(.*?)\" within 2 second$")
    public void step_assert(String expectedValue) {
        Assert.assertTrue(driver.getPageSource().contains(expectedValue));
        //Assert.assertEquals(driver.getTitle(), expectedValue);
    }

    @Then("^I close browser$")
    public void step_tearDown() {
        try {
            WebTest.tearDown(driver);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
