package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.CommentDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Comment;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.CommentService;
import com.bishe.service.ImgService;
import com.bishe.service.MusicService;
import com.bishe.service.VideoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class CommentController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ImgService imgService;

    @Resource
    private VideoService videoService;

    @Resource
    private MusicService musicService;

    @Resource
    private CommentService commentService;

    private Long commentId() {
        //格式化格式为年月日
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //获取当前时间
        String now = LocalDate.now().format(dateTimeFormatter);
        //通过redis的自增获取序号
        RAtomicLong atomicLong = redissonClient.getAtomicLong("comment");
        atomicLong.expire(1, TimeUnit.DAYS);
        long number = atomicLong.incrementAndGet();
        //拼装
        String orderId = now + number;
        return Long.parseLong(orderId);
    }

    @PostMapping("/commentText")
    @ResponseBody
    public Result<Comment> dynamicText(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) {
        Result<Comment> result = new Result();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        HashMap map = JSON.parseObject(body, HashMap.class);

        // 封装comment
        Comment comment = new Comment();
        Long id = commentId();
        comment.setId(id);
        comment.setContent(map.get("content").toString());
        comment.setAuthor(user);
        comment.setDynamicId(Long.valueOf(map.get("dynamicId").toString()));
        if (map.get("father") == null || StringUtils.isBlank(map.get("father").toString())) {
            comment.setFatherName("none");
        }

        List<String> names;
        names = (List<String>) map.get("imgName");
        names.forEach(imgName -> {
            ImgDO imgDO = (ImgDO) redisTemplate.opsForValue().get(imgName);
            if (imgDO != null) {
                imgDO.setFatherId(id);
            }
            imgService.add(imgDO);
            if (imgDO != null) {
                comment.addImg(imgDO.getImgPath());
            }
            redisTemplate.delete(imgName);
        });

        names = (List<String>) map.get("videoName");
        names.forEach(videoName -> {
            VideoDO videoDO = (VideoDO) redisTemplate.opsForValue().get(videoName);
            if (videoDO != null) {
                videoDO.setFatherId(id);
            }
            videoService.add(videoDO);
            if (videoDO != null) {
                comment.addVideo(videoDO.getVideoPath());
            }
            redisTemplate.delete(videoName);
        });

        names = (List<String>) map.get("musicName");
        names.forEach(musicName -> {
            MusicDO musicDO = (MusicDO) redisTemplate.opsForValue().get(musicName);
            if (musicDO != null) {
                musicDO.setFatherId(id);
            }
            musicService.add(musicDO);
            if (musicDO != null) {
                comment.addMusic(musicDO.getMusicPath());
            }
            redisTemplate.delete(musicName);
        });

        CommentDO commentDO = new CommentDO(comment);
        String message = commentService.add(commentDO);

        if ("success".equals(message)) {
            result.success(comment);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/getComment")
    @ResponseBody
    public Result<List<Comment>> getComment(@RequestBody String body) {
        Result<List<Comment>> result = new Result<>();

        String dynamicId = String.valueOf(JSON.parseObject(body, HashMap.class).get("dynamicId"));
        String commentId = String.valueOf(JSON.parseObject(body, HashMap.class).get("commentId"));

        if (!StringUtils.isBlank(dynamicId)) {

            List<Comment> comments = commentService.findByDynamicId(Long.parseLong(dynamicId));

            System.out.println(comments);
            if (!comments.isEmpty()) {
                result.success(comments);
                System.out.println(result);
                return result;
            }
        }


        result.error("检索失败");
        return result;
    }

}
