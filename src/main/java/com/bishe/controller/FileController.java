package com.bishe.controller;


import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Img;
import com.bishe.model.Music;
import com.bishe.model.Video;
import com.bishe.result.Result;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class FileController {

    private static final String PATH = "E:/IntelliJ IDEA Community Edition 2023.2.4/project/bishe/src/main";

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
            File dest = new File(PATH + "./resources/static/imgs/" + fileName);

            img.setImgPath("./imgs/" + fileName);
            img.setImgName(fileName);
            System.out.println(img.getImgPath());

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
            e.printStackTrace();
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
            File dest = new File(PATH + "./resources/static/video/" + fileName);

            video.setVideoPath("./video/" + fileName);
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
            e.printStackTrace();
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
            File dest = new File(PATH + "./resources/static/music/" + fileName);

            music.setMusicPath("./music/" + fileName);
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
            e.printStackTrace();
            result.error();
            return result;
        }
    }

}
