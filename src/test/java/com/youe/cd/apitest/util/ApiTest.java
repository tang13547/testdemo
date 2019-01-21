package com.youe.cd.apitest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youe.cd.apitest.dao.ExcelDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.youe.cd.apitest.util.Config.BASE_URL;
import static com.youe.cd.apitest.util.Config.MEDIA_TYPE_JSON;

public class ApiTest {
    //public static Logger logger = Logger.getLogger(ApiTest.class);
    public static Logger logger = LoggerFactory.getLogger(ApiTest.class);

    public static String getFullRequestUrl(String baseUrl, String actionUrl, Map<String, String> paramsMap) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrlPrefix = String.format("%s/%s", baseUrl, actionUrl);
            //生成参数
            String paramsStr = tempParams.toString();
            String requestUrl = String.format("%s?%s", requestUrlPrefix, paramsStr);
            return requestUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /*public static String getFullRequestUrl(String baseUrl, String actionUrl, String paramsStr) {
        //补全请求地址
        //String requestUrlPrefix = String.format("%s/%s", baseUrl, actionUrl);
        //String requestUrl = String.format("%s?%s", requestUrlPrefix, paramsStr);
        String requestUrl = String.format("%s/%s?%s", baseUrl, actionUrl, paramsStr);
        return requestUrl;
    }*/

    public static String getPathVariableUrlValue(String pathVariableUrl) {
   /*     if(pathVariableUrl.equals("{code}")) {
            return "abc";
        } if(pathVariableUrl.equals("{id}")) {
            return "12";
        } else {
            return "";
        }*/

        return pathVariableUrl;

    }

    public static String getFullRequestUrl(String baseUrl, String actionUrl, String paramsJsonStr) {
        try {
            JSONObject paramsJsonObj = JSON.parseObject(paramsJsonStr);
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsJsonObj.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsJsonObj.get(key).toString(), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrlPrefix = String.format("%s/%s", baseUrl, actionUrl);
            //生成参数
            String paramsStr = tempParams.toString();
            String requestUrl = String.format("%s?%s", requestUrlPrefix, paramsStr);
            return requestUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String runPostJson(OkHttpClient client, String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if(response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code: " + response);
        }
    }

    /**
     * 将JSONObject类型的数据转换成ArrayList<String[]>
     * @param jsonObj JSONObject类型的数据
     * @return 返回ArrayList<String[]>类型的数据
     */
    public static ArrayList<String[]> JsonObjectToList (JSONObject jsonObj) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        for ( String key : jsonObj.keySet()) {
            String[] tempS = {key, jsonObj.get(key).toString()};

            list.add(tempS);
        }
        return list;
    }

    //@Test
    public static String getToken(OkHttpClient client, String tokenUrl, String username, String password) {
        //String url = "http://192.168.30.212:22020/admin/auth/token";
        /*String json = "{\n" +
                "  \"name\":\"test\",\n" +
                "  \"password\":\"000000\"\n" +
                "}";*/
        JSONObject tokenJsonObj = new JSONObject();
        tokenJsonObj.put("name", username);
        tokenJsonObj.put("password", password);

        String tokenJsonStr = tokenJsonObj.toJSONString();

        try {
            String responseBodyStr = runPostJson(client, tokenUrl, tokenJsonStr);
            System.out.println("responseBody is: " + responseBodyStr);

            JSONObject responseBodyJsonObj = JSON.parseObject(responseBodyStr);
            System.out.println("responseBody's token is: " + responseBodyJsonObj.getJSONObject("data").get("token").toString());
            return responseBodyJsonObj.getJSONObject("data").get("token").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Response runGetBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String token) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .addHeader("token",token)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader("token",token)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .addHeader("token", token)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runGetBySelection(OkHttpClient client, String url, JSONObject headersJsonObj) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            //RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runPostBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json, String token) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .addHeader("token",token)
                        .post(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader("token",token)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .addHeader("token", token)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runPostBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .post(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runPutBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json, String token) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .addHeader("token",token)
                        .put(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader("token",token)
                    .put(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .addHeader("token", token)
                    .put(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runPutBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .put(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .put(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .put(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runDeleteBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json, String token) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .addHeader("token",token)
                        .delete(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader("token",token)
                    .delete(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .addHeader("token", token)
                    .delete(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static Response runDeleteBySelection(OkHttpClient client, String url, JSONObject headersJsonObj, String json) throws Exception {
        Response response = null;

        if (headersJsonObj.size() == 1) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            for (String key : headersJsonObj.keySet()) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader(key, headersJsonObj.get(key).toString())
                        .delete(body)
                        .build();
                response = client.newCall(request).execute();
            }
        } else if (headersJsonObj.size() == 2) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .delete(body)
                    .build();
            response = client.newCall(request).execute();
        } else if (headersJsonObj.size() == 3) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
            ArrayList<String[]> headersList = ApiTest.JsonObjectToList(headersJsonObj);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(headersList.get(0)[0], headersList.get(0)[1])
                    .addHeader(headersList.get(1)[0], headersList.get(1)[1])
                    .addHeader(headersList.get(2)[0], headersList.get(2)[1])
                    .delete(body)
                    .build();
            response = client.newCall(request).execute();
        }

        return response;
    }

    public static void runTemplate (OkHttpClient client, String excelFilePath, String token, int y) throws Exception {
        Response response = null;
        String json = null;
        String isTokenStr = ExcelDao.getCellContent(excelFilePath, Config.isTokenColumnNum, y);
        String type = ExcelDao.getCellContent(excelFilePath, Config.typeColumnNum, y);

        String tempParamsStr = ExcelDao.getCellContent(excelFilePath, Config.paramsColumnNum, y);
        String paramsJsonStr = "{" + tempParamsStr + "}";
        String baseUrl = ExcelDao.getCellContent(excelFilePath, Config.baseUrlColumnNum, y);
        String pathVariableUrl = ExcelDao.getCellContent(excelFilePath, Config.pathVariableUrlNum, y);
        String actionUrl = null;
        if(pathVariableUrl != null && !pathVariableUrl.equals("")) {
            actionUrl = ExcelDao.getCellContent(excelFilePath, Config.actionUrlColumnNum, y) + "/" + getPathVariableUrlValue(pathVariableUrl);
        } else {
            actionUrl = ExcelDao.getCellContent(excelFilePath, Config.actionUrlColumnNum, y);
        }

        String url = ApiTest.getFullRequestUrl(baseUrl, actionUrl, paramsJsonStr);

        if(!type.equals("Get")) {
            json = ExcelDao.getCellContent(excelFilePath, Config.jsonColumnNum, y);
        }


        String tempHeadersStr = ExcelDao.getCellContent(excelFilePath, Config.headersColumnNum, y);
        String headersJsonStr = "{" + tempHeadersStr + "}";
        JSONObject headersJsonObj = JSON.parseObject(headersJsonStr);
        //System.out.println("headers' size is: " + headersJsonObj.size());

        try {
            if (isTokenStr.equals("Yes")) {
                if (type.equals("Get")) {
                    response = runGetBySelection(client, url, headersJsonObj, token);
                } else if (type.equals("Post")) {
                    response = runPostBySelection(client, url, headersJsonObj, json, token);
                } else if (type.equals("Put")) {
                    response = runPutBySelection(client, url, headersJsonObj, json, token);
                } else if (type.equals("Delete")) {
                    response = runDeleteBySelection(client, url, headersJsonObj, json, token);
                } else {
                    //System.out.println("Excel中第" + y + "条测试用例输入了错误的请求类型");
                    logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 输入了错误的请求类型, 执行结果：Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
                    Assert.assertTrue(false);
                }
            } else if (isTokenStr.equals("No")) {
                if (type.equals("Get")) {
                    response = runGetBySelection(client, url, headersJsonObj);
                } else if (type.equals("Post")) {
                    response = runPostBySelection(client, url, headersJsonObj, json);
                } else if (type.equals("Put")) {
                    response = runPutBySelection(client, url, headersJsonObj, json);
                } else if (type.equals("Delete")) {
                    response = runDeleteBySelection(client, url, headersJsonObj, json);
                } else {
                    //System.out.println("Excel中第" + y + "条测试用例输入了错误的请求类型");
                    logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 输入了错误的请求类型，执行结果：Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
                    Assert.assertTrue(false);
                }
            } else {
                //System.out.println("Excel中第" + y + "条测试用例没有正确选择是否需要Token(或登录）");
                logger.warn("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 没有正确选择是否需要Token(或登录），执行结果：Fail");
                ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
                ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
                Assert.assertTrue(false);
            }
        } catch (Exception e) {
            logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果：Fail, 执行异常信息为：", e);
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            Assert.assertTrue(false);
        }

        //实际测试值
        String responseBodyStr = response.body().string();  //重要：如果下面还要使用，则应先保存响应body的str, 否则由于生命周期影响，下面代码再一次使用时，会报IllegalStateException异常
        String responseCodeStr = response.code() + ""; //转换成String类型

        //将实际测试值写入到excel文件中
        ExcelDao.addContentToExcel(excelFilePath, Config.actualResponseBodyColumnNum, y, responseBodyStr);
        ExcelDao.addContentToExcel(excelFilePath, Config.actualResponseCodeColumnNum, y, responseCodeStr); //注意：responseCodeInt为int类型

        //从excel文件中读取期望值
        String expectedBodyStr = ExcelDao.getCellContent(excelFilePath, Config.expectedResponseBodyColumnNum, y);
        String expectedCodeStr = ExcelDao.getCellContent(excelFilePath, Config.expectedResponseCodeColumnNum, y);

        //注意：要先将测试结果写入excel文件后，再断言
        if(responseCodeStr.equals(expectedCodeStr) && responseBodyStr.contains(expectedBodyStr)) {
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Pass");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            logger.info("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Pass");
            //System.out.println("Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Pass");
        } else {
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Fail \n实际响应code：" + responseCodeStr + "\n期望响应code: " + expectedCodeStr + "\n实际响应body: " + responseBodyStr + "\n期望响应body（包含): " + expectedBodyStr);
        }

        //断言
        Assert.assertTrue(responseCodeStr.equals(expectedCodeStr) && responseBodyStr.contains(expectedBodyStr));
        //Assert.assertTrue(responseBodyStr.contains(expectedBodyStr));
        //Assert.assertEquals(responseCodeStr, expectedCodeStr); //注意：responseCodeInt为int类型
    }

    public static void runTemplate (OkHttpClient client, String excelFilePath, int y) throws Exception {
        Response response = null;
        String json = null;
        String isTokenStr = ExcelDao.getCellContent(excelFilePath, Config.isTokenColumnNum, y);
        String type = ExcelDao.getCellContent(excelFilePath, Config.typeColumnNum, y);

        String tempParamsStr = ExcelDao.getCellContent(excelFilePath, Config.paramsColumnNum, y);
        String paramsJsonStr = "{" + tempParamsStr + "}";
        String baseUrl = ExcelDao.getCellContent(excelFilePath, Config.baseUrlColumnNum, y);
        //String actionUrl = ExcelDao.getCellContent(excelFilePath, Config.actionUrlColumnNum, y);
        String pathVariableUrl = ExcelDao.getCellContent(excelFilePath, Config.pathVariableUrlNum, y);
        String actionUrl = null;
        if(pathVariableUrl != null && !pathVariableUrl.equals("")) {
            actionUrl = ExcelDao.getCellContent(excelFilePath, Config.actionUrlColumnNum, y) + "/" + getPathVariableUrlValue(pathVariableUrl);
        } else {
            actionUrl = ExcelDao.getCellContent(excelFilePath, Config.actionUrlColumnNum, y);
        }

        String url = ApiTest.getFullRequestUrl(baseUrl, actionUrl, paramsJsonStr);

        if(!type.equals("Get")) {
            json = ExcelDao.getCellContent(excelFilePath, Config.jsonColumnNum, y);
        }


        String tempHeadersStr = ExcelDao.getCellContent(excelFilePath, Config.headersColumnNum, y);
        String headersJsonStr = "{" + tempHeadersStr + "}";
        JSONObject headersJsonObj = JSON.parseObject(headersJsonStr);
        //System.out.println("headers' size is: " + headersJsonObj.size());

        try {
            if (isTokenStr.equals("No")) {
                if (type.equals("Get")) {
                    response = runGetBySelection(client, url, headersJsonObj);
                } else if (type.equals("Post")) {
                    response = runPostBySelection(client, url, headersJsonObj, json);
                } else if (type.equals("Put")) {
                    response = runPutBySelection(client, url, headersJsonObj, json);
                } else if (type.equals("Delete")) {
                    response = runDeleteBySelection(client, url, headersJsonObj, json);
                } else {
                    //System.out.println("Excel中第" + y + "条测试用例输入了错误的请求类型");
                    logger.error("[logger] Excel中第" + y + "条测试用例:" + ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) +" 输入了错误的请求类型，执行结果：Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
                    ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
                    Assert.assertTrue(false);
                }
            } else {
                //System.out.println("Excel中第" + y + "条测试用例没有正确选择是否需要Token(或登录）");
                logger.warn("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) +" 没有正确选择是否需要Token(或登录），执行结果：Fail");
                ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
                ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
                Assert.assertTrue(false);
            }
        } catch (Exception e) {
            logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果：Fail, 执行异常信息为：", e);
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            Assert.assertTrue(false);
        }

        //实际测试值
        String responseBodyStr = response.body().string();  //重要：如果下面还要使用，则应先保存响应body的str, 否则由于生命周期影响，下面代码再一次使用时，会报IllegalStateException异常
        String responseCodeStr = response.code() + ""; //转换成String类型

        //将实际测试值写入到excel文件中
        ExcelDao.addContentToExcel(excelFilePath, Config.actualResponseBodyColumnNum, y, responseBodyStr);
        ExcelDao.addContentToExcel(excelFilePath, Config.actualResponseCodeColumnNum, y, responseCodeStr); //注意：responseCodeInt为int类型

        //从excel文件中读取期望值
        String expectedBodyStr = ExcelDao.getCellContent(excelFilePath, Config.expectedResponseBodyColumnNum, y);
        String expectedCodeStr = ExcelDao.getCellContent(excelFilePath, Config.expectedResponseCodeColumnNum, y);

        //注意：要先将测试结果写入excel文件后，再断言
        if(responseCodeStr.equals(expectedCodeStr) && responseBodyStr.contains(expectedBodyStr)) {
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Pass");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            logger.info("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Pass" );
            //System.out.println("Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Pass");
        } else {
            ExcelDao.addContentToExcel(excelFilePath, Config.testResultColumnNum, y, "Fail");
            ExcelDao.addContentToExcel(excelFilePath, Config.runTimeColumnNum, y, DateUtil.getDate());
            logger.error("[logger] Excel中第" + y + "条测试用例:" +  ExcelDao.getCellContent(excelFilePath, Config.testcaseNameColumnNum, y) + " 执行结果： Fail \n实际响应code：" + responseCodeStr + "\n期望响应code: " + expectedCodeStr + "\n实际响应body: " + responseBodyStr + "\n期望响应body(包含): " + expectedBodyStr );
        }

        //断言
        Assert.assertTrue(responseCodeStr.equals(expectedCodeStr) && responseBodyStr.contains(expectedBodyStr));
        //Assert.assertTrue(responseBodyStr.contains(expectedBodyStr));
        //Assert.assertEquals(responseCodeStr, expectedCodeStr); //注意：responseCodeInt为int类型

        if(response.isSuccessful()) {
            System.out.println(responseBodyStr);  //重要：使用变量, 原因见上
        }
    }



}
