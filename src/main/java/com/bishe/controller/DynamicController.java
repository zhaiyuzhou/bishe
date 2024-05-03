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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                                       HttpServletRequest request) {
        Result<Dynamic> result = new Result();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
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

    @PostMapping("/getDynamic")
    @ResponseBody
    public Result<List<Dynamic>> getDynamic(@RequestBody String body) {
        Result<List<Dynamic>> result = new Result<>();

        String tag = String.valueOf(JSON.parseObject(body, HashMap.class).get("tag"));
        String authorId = String.valueOf(JSON.parseObject(body, HashMap.class).get("authorId"));


        if (!StringUtils.isBlank(tag) && !tag.equals("null")) {
            List<DynamicDO> dynamicDOS = dynamicService.findByTag(tag);
            if (toDynamic(result, dynamicDOS)) return result;
        }

        if (!StringUtils.isBlank(authorId) && !authorId.equals("null")) {
            List<DynamicDO> dynamicDOS = dynamicService.findByAuthor(Long.valueOf(authorId));
            if (toDynamic(result, dynamicDOS)) return result;
        }


        List<DynamicDO> dynamicDOS = dynamicService.findLimit();
        if (toDynamic(result, dynamicDOS)) return result;
        result.error("tag和authorId均为空");
        return result;
    }

    private boolean toDynamic(Result<List<Dynamic>> result, List<DynamicDO> dynamicDOS) {
        if (dynamicDOS == null || dynamicDOS.isEmpty()) {
            result.error("请求的动态为空");
            return true;
        }

        List<Dynamic> dynamics = new ArrayList<>();

        dynamicDOS.forEach(dynamicDO -> {
            Dynamic dynamic = dynamicDO.toModel();
            dynamic.setAuthor(userService.findById(dynamicDO.getAuthorId()).toModel());
            List<ImgDO> imgDOS = imgService.searchByFatherId(dynamicDO.getId());
            List<VideoDO> videoDOS = videoService.searchByFatherId(dynamicDO.getId());
            List<MusicDO> musicDOS = musicService.searchByFatherId(dynamicDO.getId());
            if (imgDOS != null) {
                imgDOS.forEach(imgDO -> {
                    dynamic.addImg(imgDO.getImgPath());
                });
            }
            if (videoDOS != null) {
                videoDOS.forEach(videoDO -> {
                    dynamic.addVideo(videoDO.getVideoPath());
                });
            }
            if (musicDOS != null) {
                musicDOS.forEach(musicDO -> {
                    dynamic.addMusic(musicDO.getMusicPath());
                });
            }
            dynamics.add(dynamic);
        });

        if (!dynamics.isEmpty()) {
            result.success(dynamics);
            return true;
        }
        return false;
    }

}
