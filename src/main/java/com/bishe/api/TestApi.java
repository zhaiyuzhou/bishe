package com.bishe.api;

import com.alibaba.fastjson.JSON;
import com.bishe.dao.UserDAO;
import com.bishe.dataobject.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class TestApi {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping("/")
    public String index() {
        return "index/index";
    }

    @GetMapping("/signhtml")
    public String signHtml() {
        return "sign/index";
    }

    @PostMapping("/sign")
    @ResponseBody
    public UserDO sign(@RequestBody String body) {

        Map<String, Object> map = JSON.parseObject(body, HashMap.class);

        UserDO userDO = new UserDO();

        userDO.setEmail(map.get("email").toString());
        userDO.setGender(map.get("gender").toString());
        userDO.setUserName(map.get("username").toString());
        userDO.setNickName(map.get("nickname").toString());
        userDO.setPhone(map.get("phone").toString());
        userDO.setPassword(map.get("password").toString());

        userDAO.add(userDO);

        return userDO;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("remember") Boolean remember

    ) {
        if (StringUtils.isEmpty(userName)) {
            return "username为空";
        }
        if (StringUtils.isEmpty(password)) {
            return "password为空";
        }
        if (remember == null) {
            return "remember为空";
        }

        UserDO userDO = userDAO.selectByUserName(userName);
        if (userDO.getPassword().equals(password)) {
            return "true";
        }
        return "false";
    }

}
