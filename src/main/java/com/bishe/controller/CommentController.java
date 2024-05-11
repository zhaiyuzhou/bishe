package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.CommentDO;
import com.bishe.model.Comment;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.CommentService;
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
public class CommentController {

    @Resource
    private CommentService commentService;


    @PostMapping("/commentText")
    @ResponseBody
    public Result<Comment> dynamicText(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Comment> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = JSON.parseObject(body, HashMap.class);
        Comment comment = commentService.postComment(map, user).get();

        CommentDO commentDO = new CommentDO(comment);
        String message = commentService.add(commentDO).get();

        if ("success".equals(message)) {
            result.success(comment);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/getComment")
    @ResponseBody
    public Result<List<Comment>> getComment(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<List<Comment>> result = new Result<>();

        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));

        if (!StringUtils.isBlank(dynamicId)) {

            List<Comment> comments = commentService.findByDynamicId(Long.parseLong(dynamicId)).get();

            if (!comments.isEmpty()) {
                result.success(comments);
                return result;
            }
        }


        result.error("检索失败");
        return result;
    }

}
