package com.youe.cd.apitest.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.io.IOException;

public class ApiTestDemo {
    OkHttpClient client = new OkHttpClient();

    public String runHttpGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if(response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code: " + response);
        }
    }

    @Test
    public void testApiByHttpGet() {
        String url = "http://www.baidu.com";

        try {
            runHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
