package com.bishe.bishe.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginApi {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String body) {
        System.out.println(body);
        return body;
    }

}
