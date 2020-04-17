package com.example.thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("")
    public String index(ModelMap map){
        map.put("title","hello word");
        return "index"; //不要写成 "/index"
    }
}
