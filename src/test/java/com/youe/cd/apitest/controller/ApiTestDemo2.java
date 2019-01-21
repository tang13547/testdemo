package com.youe.cd.apitest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youe.cd.apitest.dao.ExcelDao;
import com.youe.cd.apitest.util.Config;
import okhttp3.*;
//import okhttp3.internal.http2.Header;
//import org.apache.logging.log4j.Logger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
//import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.youe.cd.apitest.util.ApiTest;
import static com.youe.cd.apitest.util.Config.BASE_URL;
import static com.youe.cd.apitest.util.Config.MEDIA_TYPE_JSON;


public class ApiTestDemo2 {
    String token;
    OkHttpClient client = new OkHttpClient();

    Logger logger = Logger.getLogger(ApiTestDemo2.class);

    int y = 0;

    @BeforeClass
    public void runGetToken() {
        token = ApiTest.getToken(client, Config.TOKEN_URL, Config.USERNAME, Config.PASSWORD);
    }

    @Test(priority = 1, enabled = true)  //通过listener动态设置了注解中（下述方法）执行次数invocationCount的值
    public void searchDataNode() {
        try {
            y++;
            System.out.println("CurrentLine is : " + y);
            ApiTest.runTemplate(client, Config.excelFilePath, token, y);

        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("[logger] Excel中第" + y + "条测试用例, 执行异常信息为：", e); //将Java异常的完整堆栈内容打印到log4j日志
        }
    }

    @Test(priority = 2, enabled = false)
    public void createDataNode() {
        try {
            ApiTest.runTemplate(client, Config.excelFilePath, token, 2);

            /*logger.info("createDataNode log for info");
            logger.debug("createDataNode log for debug");
            logger.warn("createDataNode log for warn");
            logger.error("createDataNode log for error");*/

        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("createDataNode logerror!");
        }
    }

    @Test(priority = 3, enabled = false)
    public void alterDataNode() {

        try {
            ApiTest.runTemplate(client, Config.excelFilePath, token, 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4, enabled = false)
    public void dropDataNode() {
        try {
            ApiTest.runTemplate(client, Config.excelFilePath, token, 4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
