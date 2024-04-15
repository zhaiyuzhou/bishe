package com.bishe.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UpdateController {

    public static final String PATH = "E:/IntelliJ IDEA Community Edition 2023.2.4/project/bishe/src/main";

    @PostMapping("/updateImg")
    @ResponseBody
    public String updateImg(@RequestParam("file") MultipartFile file) {
        try {
            // 指定文件保存的位置
            File dest = new File(PATH + "./resources/static/imgs" + file.getOriginalFilename());
            // 将文件保存到指定的位置
            file.transferTo(dest);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    @PostMapping("/updateVideo")
    @ResponseBody
    public String updateVideo(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println(file);
            // 指定文件保存的位置
            File dest = new File(PATH + "./resources/static/video/" + file.getOriginalFilename());
            // 将文件保存到指定的位置
            file.transferTo(dest);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

}
