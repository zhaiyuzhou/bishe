package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.DynamicDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Dynamic;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class DynamicController {

    @Resource
    private ImgService imgService;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private DynamicService dynamicService;

    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;

    private int times;

    private Long dynamicId() {
        //格式化格式为年月日
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //获取当前时间
        String now = LocalDate.now().format(dateTimeFormatter);
        //通过redis的自增获取序号
        RAtomicLong atomicLong = redissonClient.getAtomicLong("dynamic");
        atomicLong.expire(1, TimeUnit.DAYS);
        long number = atomicLong.incrementAndGet();
        //拼装
        String orderId = now + number;
        return Long.parseLong(orderId);
    }

    @PostMapping("/dynamicText")
    @ResponseBody
    public Result<Dynamic> dynamicText(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) {
        Result<Dynamic> result = new Result();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        Dynamic dynamic = new Dynamic();
        HashMap map = JSON.parseObject(body, HashMap.class);

        Long id = dynamicId();
        dynamic.setId(id);
        dynamic.setContent(map.get("content").toString());
        dynamic.setTag(map.get("tag").toString());
        dynamic.setPostedDate(LocalDateTime.now());
        dynamic.setAuthor(user);

        List<String> names;

        names = (List<String>) map.get("imgName");
        names.forEach(imgName -> {
            ImgDO imgDO = (ImgDO) redisTemplate.opsForValue().get(imgName);
            if (imgDO != null) {
                imgDO.setFatherId(id);
            }
            imgService.add(imgDO);
            if (imgDO != null) {
                dynamic.addImg(imgDO.getImgPath());
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
                dynamic.addVideo(videoDO.getVideoPath());
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
                dynamic.addMusic(musicDO.getMusicPath());
            }
            redisTemplate.delete(musicName);
        });

        DynamicDO dynamicDO = new DynamicDO(dynamic);
        String message = dynamicService.add(dynamicDO);

        if ("success".equals(message)) {
            result.success(dynamic);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/refreshPyn")
    @ResponseBody
    public Result<Boolean> refreshPyn(@RequestBody String body) {
        Result<Boolean> result = new Result<>();
        String times = JSON.parseObject(body, HashMap.class).get("times").toString();
        this.times = Integer.parseInt(times) * 10;
        System.out.println(this.times);
        result.setSuccess(true);
        return result;
    }

    @PostMapping("/getDynamic")
    @ResponseBody
    public Result<List<Dynamic>> getDynamic(@RequestBody String body) {
        Result<List<Dynamic>> result = new Result<>();

        HashMap map = JSON.parseObject(body, HashMap.class);

        String tag = null;
        if (map.get("tag") != null) {
            tag = String.valueOf(map.get("tag"));
        }

        if (!StringUtils.isBlank(tag) && !tag.equals("null")) {
            List<Dynamic> dynamics = dynamicService.findByTag(tag, this.times);
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
            List<Dynamic> dynamics = dynamicService.findByAuthor(Long.valueOf(authorId), this.times);
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
            List<Dynamic> dynamics = dynamicService.search(searchDate, this.times);
            if (!dynamics.isEmpty()) {
                result.success(dynamics);
                return result;
            }
        }

        List<Dynamic> dynamics = dynamicService.findLimit(this.times);
        if (!dynamics.isEmpty()) {
            result.success(dynamics);
            return result;
        }
        result.error("检索失败");
        return result;
    }

}
