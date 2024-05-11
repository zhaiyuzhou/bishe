package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.DynamicDO;
import com.bishe.model.Dynamic;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.DynamicService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Controller
public class DynamicController {

    @Resource
    private DynamicService dynamicService;

    @PostMapping("/dynamicText")
    @ResponseBody
    public Result<Dynamic> dynamicText(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Dynamic> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = JSON.parseObject(body, HashMap.class);
        Dynamic dynamic = dynamicService.postDynamic(map, user).get();

        DynamicDO dynamicDO = new DynamicDO(dynamic);
        String message = dynamicService.add(dynamicDO).get();

        if ("success".equals(message)) {
            result.success(dynamic);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/refreshPyn")
    @ResponseBody
    public Result<String> refreshPyn(@RequestBody String body) {
        Result<String> result = new Result<>();
        String times = JSON.parseObject(body, HashMap.class).get("times").toString();
        result.success(times);
        return result;
    }

    @PostMapping("/getDynamic")
    @ResponseBody
    public Result<List<Dynamic>> getDynamic(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<List<Dynamic>> result = new Result<>();

        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = JSON.parseObject(body, HashMap.class);

        String tag = null;
        if (map.get("tag") != null) {
            tag = String.valueOf(map.get("tag"));
        }

        int time = map.get("time") == null ? 0 : Integer.parseInt(map.get("time").toString());

        if (!StringUtils.isBlank(tag) && !tag.equals("null")) {
            List<Dynamic> dynamics = dynamicService.findByTag(tag, time).get();
            if (!dynamics.isEmpty()) {
                result.success(dynamics);
                return result;
            }
        }

        String authorId = null;
        if (map.get("authorId") != null) {
            authorId = String.valueOf(map.get("authorId"));
        }

        if (!StringUtils.isBlank(authorId) && !authorId.equals("null")) {
            List<Dynamic> dynamics = dynamicService.findByAuthor(Long.valueOf(authorId), time).get();
            if (!dynamics.isEmpty()) {
                result.success(dynamics);
                return result;
            }
        }
        String searchDate = null;
        if (map.get("searchDate") != null) {
            searchDate = String.valueOf(map.get("searchDate"));
        }
        if (!StringUtils.isBlank(searchDate) && !searchDate.equals("null")) {
            List<Dynamic> dynamics = dynamicService.search(searchDate, time).get();
            if (!dynamics.isEmpty()) {
                result.success(dynamics);
                return result;
            }
        }

        List<Dynamic> dynamics = dynamicService.findLimit(time).get();
        if (!dynamics.isEmpty()) {
            result.success(dynamics);
            return result;
        }
        result.error("检索失败");
        return result;
    }

    @PostMapping("/delDynamic")
    @ResponseBody
    public Result<Dynamic> delDynamic(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<Dynamic> result = new Result<>();

        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = JSON.parseObject(body, HashMap.class);
        String dynamicId = String.valueOf(map.get("dynamicId"));

        if (StringUtils.isBlank(dynamicId) || "mull".equals(dynamicId)) {
            result.error("传入ID为空");
        }

        String message = dynamicService.delDynamic(Long.valueOf(dynamicId)).get();
        if ("success".equals(message)) {
            result.success();
            return result;
        }
        result.error(message);
        return result;
    }

}
