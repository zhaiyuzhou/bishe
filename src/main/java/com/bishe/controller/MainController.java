package com.bishe.controller;

import com.alibaba.fastjson.JSON;
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
import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @Resource
    private UserService userService;

    @Resource
    private DynamicService dynamicService;

    @Resource
    private CommentService commentService;

    @GetMapping("/cook")
    public void cook(@CookieValue(value = "username", required = false) String username,
                     HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            Cookie cookie = new Cookie("isLogin", "false");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

    }

    @PostMapping("/addLikeNum")
    @ResponseBody
    public Result<Boolean> addLikeNum(@RequestBody String body,
                                      @CookieValue(value = "username") String username,
                                      HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Boolean> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));
        String commentId = String.valueOf(JSON.parseObject(body, HashMap.class).get("commentId"));

        if (!StringUtils.isBlank(dynamicId) && !"null".equals(dynamicId)) {
            if ("success".equals(dynamicService.addLikeNum(user.getId(), Long.valueOf(dynamicId)).get())) {
                result.success(true);
                return result;
            }
        }

        if (!StringUtils.isBlank(commentId) && !"null".equals(commentId)) {
            if ("success".equals(commentService.addLikeNum(Long.valueOf(commentId)).get())) {
                result.success(true);
                return result;
            }
        }

        result.error("失败");
        return result;
    }

    @PostMapping("/delLikeNum")
    @ResponseBody
    public Result<Boolean> delLikeNum(@RequestBody String body,
                                      @CookieValue(value = "username") String username,
                                      HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Boolean> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));
        String commentId = String.valueOf(JSON.parseObject(body, HashMap.class).get("commentId"));

        if (!StringUtils.isBlank(dynamicId) && !"null".equals(dynamicId)) {
            if ("success".equals(dynamicService.decLikeNum(user.getId(), Long.valueOf(dynamicId)).get())) {
                result.success(true);
                return result;
            }
        }

        if (!StringUtils.isBlank(commentId) && !"null".equals(commentId)) {
            if ("success".equals(commentService.decLikeNum(Long.valueOf(commentId)).get())) {
                result.success(true);
                return result;
            }
        }

        result.error("失败");
        return result;
    }

}
