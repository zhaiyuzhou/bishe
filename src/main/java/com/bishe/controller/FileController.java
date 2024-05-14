package com.bishe.controller;


import com.alibaba.fastjson.JSON;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Img;
import com.bishe.model.Music;
import com.bishe.model.Video;
import com.bishe.result.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@Slf4j
public class FileController {

    private static final String PATH = new File("").getAbsolutePath();

    //声明需要格式化的格式(日期加时间)
    private final DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/updateImg")
    @ResponseBody
    public Result<Img> updateImg(@RequestParam("file") MultipartFile file) {
        Result<Img> result = new Result<>();
        try {

            Img img = new Img();

            //文件原名字
            String OriginalFilename = file.getOriginalFilename();
            if (OriginalFilename != null && redisTemplate.opsForValue().get(OriginalFilename) != null) {
                result.error("文件重名");
                return result;
            }

            // 文件名字
            String fileName = "img-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./imgs/" + fileName);

            img.setImgPath("../api/file/imgs/" + fileName);
            img.setImgName(fileName);

            // 存入缓存
            ImgDO imgDO = new ImgDO(img);
            if (OriginalFilename != null) {
                redisTemplate.opsForValue().set(OriginalFilename, imgDO);
            }

            // 将文件保存到指定的位置
            file.transferTo(dest);

            result.success(img);

            return result;
        } catch (IOException e) {
            log.error("捕获异常", e);
            result.error();
            return result;
        }
    }

    @PostMapping("/updateVideo")
    @ResponseBody
    public Result<Video> updateVideo(@RequestParam("file") MultipartFile file) {
        Result<Video> result = new Result<>();
        try {

            Video video = new Video();

            //文件原名字
            String OriginalFilename = file.getOriginalFilename();
            if (OriginalFilename != null && redisTemplate.opsForValue().get(OriginalFilename) != null) {
                result.error("文件重名");
                return result;
            }

            // 文件名字
            String fileName = "video-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./video/" + fileName);

            video.setVideoPath("../api/file/video/" + fileName);
            video.setVideoName(fileName);

            VideoDO videoDO = new VideoDO(video);
            if (OriginalFilename != null) {
                redisTemplate.opsForValue().set(OriginalFilename, videoDO);
            }

            // 将文件保存到指定的位置
            file.transferTo(dest);
            result.success(video);
            return result;
        } catch (Exception e) {
            log.error("捕获异常", e);
            result.error();
            return result;
        }
    }

    @PostMapping("/updateMusic")
    @ResponseBody
    public Result<Music> updateMusic(@RequestParam("file") MultipartFile file) {
        Result<Music> result = new Result<>();
        try {

            Music music = new Music();

            //文件原名字
            String OriginalFilename = file.getOriginalFilename();
            if (OriginalFilename != null && redisTemplate.opsForValue().get(OriginalFilename) != null) {
                result.error("文件重名");
                return result;
            }

            // 文件名字
            String fileName = "music-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./music/" + fileName);

            music.setMusicPath("../api/file/music/" + fileName);
            music.setMusicName(fileName);

            MusicDO musicDO = new MusicDO(music);
            if (OriginalFilename != null) {
                redisTemplate.opsForValue().set(OriginalFilename, musicDO);
            }

            // 将文件保存到指定的位置
            file.transferTo(dest);

            result.success(music);

            return result;
        } catch (IOException e) {
            log.error("捕获异常", e);
            result.error();
            return result;
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public Result<Boolean> removeImg(@RequestBody String body) {
        Result<Boolean> result = new Result<>();

        String fileName = JSON.parseObject(body, HashMap.class).get("fileName").toString();
        if (fileName != null) {
            redisTemplate.delete(fileName);
            result.success(true);
            return result;
        }
        result.error("失败");
        return result;
    }

    @GetMapping("/file/{pathVariable}/{filename}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getVideo(@PathVariable("pathVariable") String pathVariable,
                                                                         @PathVariable("filename") String filename) {
        Path path = Paths.get(pathVariable + "/" + filename);
        org.springframework.core.io.Resource resource = null;
        try {
            resource = new FileSystemResource(path.toFile());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                    .body(resource);
        } catch (IOException e) {
            log.error("发现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
