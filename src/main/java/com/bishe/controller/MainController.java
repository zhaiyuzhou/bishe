package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.CommentDO;
import com.bishe.dataobject.DynamicDO;
import com.bishe.dataobject.UserDO;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.CommentService;
import com.bishe.service.DynamicService;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MainController {

    @Resource
    private UserService userService;

    @Resource
    private DynamicService dynamicService;

    @Resource
    private CommentService commentService;

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

    @GetMapping("/cook")
    public void cook(@CookieValue(value = "username", required = false) String username,
                     HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            Cookie cookie = new Cookie("isLogin", "false");
            response.addCookie(cookie);
        }

    }

    @PostMapping("/addLikeNum")
    @ResponseBody
    public Result<Boolean> addLikeNum(@RequestBody String body) {
        Result<Boolean> result = new Result<>();

        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));
        String commentId = String.valueOf(JSON.parseObject(body, HashMap.class).get("commentId"));
        String userId = String.valueOf(JSON.parseObject(body, HashMap.class).get("userId"));

        if (!StringUtils.isBlank(dynamicId) && !"null".equals(dynamicId)) {
            if ("success".equals(dynamicService.addLikeNum(Long.valueOf(dynamicId)))) {
                result.success(true);
                return result;
            }
        }

        if (!StringUtils.isBlank(commentId) && !"null".equals(commentId)) {
            if ("success".equals(commentService.addLikeNum(Long.valueOf(commentId)))) {
                result.success(true);
                return result;
            }
        }

        if (!StringUtils.isBlank(userId) && !"null".equals(userId)) {
            UserDO userDO = userService.findById(Long.valueOf(userId));
            userDO.setLikeNum(userDO.getLikeNum() + 1);
            userService.update(userDO);
            result.success(true);
            return result;
        }

        result.error("失败");
        return result;
    }

    @PostMapping("/delLikeNum")
    @ResponseBody
    public Result<Boolean> delLikeNum(@RequestBody String body) {
        Result<Boolean> result = new Result<>();
        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));
        String commentId = String.valueOf(JSON.parseObject(body, HashMap.class).get("commentId"));
        String userId = String.valueOf(JSON.parseObject(body, HashMap.class).get("userId"));

        if (!StringUtils.isBlank(dynamicId) && !"null".equals(dynamicId)) {
            DynamicDO dynamicDO = dynamicService.findById(Long.valueOf(dynamicId));
            if (dynamicDO.getLikeNum() > 0) {
                dynamicDO.setLikeNum(dynamicDO.getLikeNum() - 1);
            }
            dynamicService.update(dynamicDO);
            result.success(true);
            return result;
        }

        if (!StringUtils.isBlank(commentId) && !"null".equals(commentId)) {
            CommentDO commentDO = commentService.findById(Long.valueOf(commentId));
            if (commentDO.getLikeNum() > 0) {
                commentDO.setLikeNum(commentDO.getLikeNum() - 1);
            }
            commentService.update(commentDO);
            result.success(true);
            return result;
        }

        if (!StringUtils.isBlank(userId) && !"null".equals(userId)) {
            UserDO userDO = userService.findById(Long.valueOf(userId));
            if (userDO.getLikeNum() > 0) {
                userDO.setLikeNum(userDO.getLikeNum() - 1);
            }
            userService.update(userDO);
            result.success(true);
            return result;
        }


        result.error("失败");
        return result;
    }

}
