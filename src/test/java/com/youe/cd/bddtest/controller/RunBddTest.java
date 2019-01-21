package com.youe.cd.bddtest.controller;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)  //这是一个运行器 ，指用Cucumber来运行测试
@CucumberOptions(
        features = {"src/test/java/com/youe/cd/bddtest/features"},  //指定我们项目中要运行的feature的目录或具体.feature文件
        format = {"pretty", "html:target/cucumber", "json:target/cucumber/cucumber.json"}, //指定我们项目中要运行时生成的报告，并指定之后可以在target目录中找到对应的测试报告
        glue = {"com.youe.cd.bddtest.controller"},  //指定项目运行时查找实现step定义文件的目录
        tags = {"@test,@testfeature"}  //选择feature文件中对应的标签执行, "@test,~@test"其中~表示非

)

public class RunBddTest {
}
