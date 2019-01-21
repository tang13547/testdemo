package com.youe.cd.apitest.util;

import com.youe.cd.apitest.dao.ExcelDao;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
//import org.testng.internal.annotations.IAnnotationTransformer;
//import org.testng.internal.annotations.ITest;
//import org.testng.ITest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SetTestAnnotationValue implements IAnnotationTransformer {
    //@SuppressWarnings("rawtypes")
    //@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        int sizeY = ExcelDao.getSizeY(Config.excelFilePath);
        //int concurrentNum = 10; //注：也可以是一个变量

        if ("searchDataNode".equals(testMethod.getName())) {  //设置要判断使用该listener的方法名或类名或构造方法名
            annotation.setInvocationCount(sizeY);  //设置注解中的执行次数
            //annotation.setThreadPoolSize(concurrentNum);  //设置注解中的并发数
        }
    }

}
