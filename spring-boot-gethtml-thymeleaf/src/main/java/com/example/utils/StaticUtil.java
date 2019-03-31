package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;


/**
 * 使用thymeleaf生成静态页面
 */
public class StaticUtil {
    private static final String TEMPLATE_PREFIX = "templates/";
    private static final String TEMPLATE_SUFFIX = ".html";



    public void getHtml() throws IOException {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(TEMPLATE_PREFIX);
        resolver.setSuffix(TEMPLATE_SUFFIX);
        TemplateEngine templateEngine=new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //填充模板
        Context context = new Context();
        context.setVariable("name", "小明");


        //模板生成位置，和html模板的名字
        FileWriter writer = new FileWriter("d:/index.html");
        //"temp":模板名称
        templateEngine.process("temp", context, writer);
        writer.close();




    }


}
