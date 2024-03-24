package com.bishe.bishe.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class loginApi {

    @RequestMapping("/")
    public String index() {
        return "index/index";
    }

    @GetMapping("/signhtml")
    public String signHtml() {
        return "sign/index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String body) {
        System.out.println(body);
        return body;
    }

}
