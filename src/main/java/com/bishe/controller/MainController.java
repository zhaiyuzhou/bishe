package com.bishe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index/index";
    }

    @GetMapping("/signal")
    public String signHtml() {
        return "sign/index";
    }

    @GetMapping("/pencen")
    public String pencHtml() {
        return "pencenH/index";
    }

}
