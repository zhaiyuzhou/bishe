package com.bishe.service.impl;

import com.bishe.dao.VideoDAO;
import com.bishe.dataobject.VideoDO;
import com.bishe.service.VideoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoDAO videoDAO;

    @Override
    @Async("async")
    public CompletableFuture<String> add(VideoDO videoDO) {
        if (videoDO == null) {
            return CompletableFuture.completedFuture("传入的对象为空");
        }

        if (StringUtils.isBlank(videoDO.getVideoPath())) {
            return CompletableFuture.completedFuture("视频路径为空");
        }

        if (StringUtils.isBlank(videoDO.getVideoName())) {
            return CompletableFuture.completedFuture("视频名字为空");
        }

        if (videoDO.getFatherId() == null) {
            return CompletableFuture.completedFuture("dynamicId为空");
        }

        videoDAO.add(videoDO);
        return CompletableFuture.completedFuture("success");

    }

    @Override
    @Async("async")
    public CompletableFuture<List<VideoDO>> searchByFatherId(Long fatherId) {
        if (fatherId == null) {
            return CompletableFuture.completedFuture(null);
        }
        List<VideoDO> videoDOS = videoDAO.selectAllByFatherId(fatherId);
        return CompletableFuture.completedFuture(videoDOS);
    }

    @Override
    @Async("async")
    public CompletableFuture<VideoDO> searchByImgName(String videoName) {
        return CompletableFuture.completedFuture(null);
    }
}
