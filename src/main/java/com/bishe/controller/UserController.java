package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dao.UserDAO;
import com.bishe.dataobject.UserDO;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/signhtml")
    public String signHtml() {
        return "sign/index";
    }

    @PostMapping("/sign")
    @ResponseBody
    public Result<User> sign(@RequestBody String body) {
        Result<User> result = new Result<>();

        Map<String, Object> map = JSON.parseObject(body, HashMap.class);

        UserDO userDO = new UserDO();

        userDO.setEmail(map.get("email").toString());
        userDO.setGender(map.get("gender").toString());
        userDO.setUserName(map.get("username").toString());
        userDO.setNickName(map.get("nickname").toString());
        userDO.setPhone(map.get("phone").toString());
        userDO.setPassword(map.get("password").toString());


        String message = userService.sign(userDO);

        if (!("success".equals(message))) {
            result.error(message);
            return result;
        }

        User user = userDO.toModel();

        result.success(user);

        return result;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@RequestParam("username") String userName,
                        @RequestParam("password") String password,
                              @RequestParam("remember") Boolean remember,
                              HttpServletRequest request, HttpServletResponse response
    ) {
        Result<User> result = new Result<>();
        String message = userService.login(userName, password);

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        if (remember == null) {
            message = "remember为空";
        } else {
            // 写session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("islogin", "true");
            response.addCookie(cookie);
        }

        result.success(user);

        return result;
    }

}
