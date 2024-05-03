package com.bishe.service.impl;

import com.bishe.dao.VideoDAO;
import com.bishe.dataobject.VideoDO;
import com.bishe.service.VideoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoDAO videoDAO;

    @Override
    public String add(VideoDO videoDO) {

        if (videoDO == null) {
            return "传入的对象为空";
        }

        if (StringUtils.isBlank(videoDO.getVideoPath())) {
            return "视频路径为空";
        }

        if (StringUtils.isBlank(videoDO.getVideoName())) {
            return "视频名字为空";
        }

        if (videoDO.getFatherId() != null) {
            return "dynamicId为空";
        }

        videoDAO.add(videoDO);

        return "success";

    }

    @Override
    public List<VideoDO> searchByFatherId(Long fatherId) {
        return List.of();
    }

    @Override
    public VideoDO searchByImgName(String videoName) {
        return null;
    }
}
