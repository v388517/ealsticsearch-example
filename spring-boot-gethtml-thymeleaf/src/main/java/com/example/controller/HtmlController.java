package com.example.controller;

import com.example.utils.StaticUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/html")
public class HtmlController {
    @RequestMapping("/getHtml")
    public String getHtml() {
        StaticUtil staticUtil = new StaticUtil();
        try {
            staticUtil.getHtml();

            return "生成成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "生成失败";

        }


    }

}
