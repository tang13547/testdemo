package com.youe.cd.apitest.util;

import okhttp3.MediaType;

import java.util.Map;

import static com.youe.cd.apitest.dao.MysqlDao.getDBConnMap;

public class Config {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String BASE_URL = "http://192.168.30.211:22020";

    //配置获取token的url及用户名和密码
    public static final String TOKEN_URL = BASE_URL + "/admin/auth/token";
    public static final String USERNAME = "test";
    public static final String PASSWORD = "000000";

    //配置数据库连接信息
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_SERVER_URL = "172.16.0.241:3306";
    public static final String DB_NAME = "iep";
    public static final String DB_URL = "jdbc:mysql://" + DB_SERVER_URL + "/" + DB_NAME + "?characterEncoding=utf-8&useSSL=false";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1qaz@WSX";

    public static final Map<String, String> dbConnMap = getDBConnMap();


    //excle路径及及各列配置
    public static final String excelFilePath = "./src/test/resources/ApiTestCases_DaaS.xls";
    public static final int testcaseNameColumnNum = 2;
    public static final int isTokenColumnNum = 3;
    public static final int typeColumnNum = 4;
    public static final int paramsColumnNum = 8; //+1
    public static final int baseUrlColumnNum = 5;
    public static final int actionUrlColumnNum = 6;
    public static final int pathVariableUrlNum = 7;
    public static final int jsonColumnNum = 10;
    public static final int headersColumnNum = 9;
    public static final int actualResponseBodyColumnNum = 11;
    public static final int actualResponseCodeColumnNum = 12;
    public static final int expectedResponseBodyColumnNum = 13;
    public static final int expectedResponseCodeColumnNum = 14;
    public static final int testResultColumnNum = 15;
    public static final int runTimeColumnNum = 16;
}
