package com.youe.cd.apitest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("Current date is: " + sdf.format(date));

        return sdf.format(date);
    }

}
