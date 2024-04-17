package com.bishe.controller;


import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Img;
import com.bishe.model.Music;
import com.bishe.model.Video;
import com.bishe.result.Result;
import com.bishe.service.ImgService;
import com.bishe.service.MusicService;
import com.bishe.service.VideoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ImgService imgService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private VideoService videoService;

    //声明需要格式化的格式(日期加时间)
    private DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    @PostMapping("/updateImg")
    @ResponseBody
    public Result<Img> updateImg(@RequestParam("file") MultipartFile file) {
        Result<Img> result = new Result<>();
        try {

            Img img = new Img();

            // 文件名字
            String fileName = "img-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./resources/static/imgs/" + fileName);

            img.setImgPath("./static/video/" + fileName);
            img.setImgName(fileName);

            ImgDO imgDO = new ImgDO(img);
            imgService.add(imgDO);

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

            // 文件名字
            String fileName = "video-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./resources/static/video/" + fileName);

            video.setVideoPath("./static/video/" + fileName);
            video.setVideoName(fileName);

            VideoDO videoDO = new VideoDO(video);
            String message = videoService.add(videoDO);
            System.out.println(message);

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

            // 文件名字
            String fileName = "music-" + LocalDateTime.now().format(dfDateTime) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // 指定文件保存的位置
            File dest = new File(PATH + "./resources/static/music/" + fileName);

            music.setMusicPath("./static/video/" + fileName);
            music.setMusicName(fileName);

            musicService.add(new MusicDO(music));

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
