package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private HttpServletResponse response;


    @RequestMapping("/show")
    @CrossOrigin(origins ="http://localhost:8081",allowCredentials ="true")
    public String show() {
        System.out.println("请求完成......");
        return "java....";
    }


    @RequestMapping(value="/find")
    public String findUserWithJsonP(String callback) throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        map.put("username", "Tom");
        map.put("age", "20"); //{"username":"Tom","age":"20"}

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(map);//{"username":"Tom","age":"20"}

        result = callback + "(" + result + ")"; // jquery18230243({"username":"Tom","age":"20"})
        return result;
    }

}






