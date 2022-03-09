package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplatController {
    
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/course")
    public String getCoursePage(){
        return "course";
    }
}
