package com.bishe.service.impl;

import com.bishe.dao.MusicDAO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.service.MusicService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MusicServiceImpl implements MusicService {

    @Resource
    private MusicDAO musicDAO;

    @Override
    @Async("async")
    public CompletableFuture<String> add(MusicDO musicDO) {

        if (musicDO == null) {
            return CompletableFuture.completedFuture("传入的对象为空");
        }

        if (StringUtils.isEmpty(musicDO.getMusicPath())) {
            return CompletableFuture.completedFuture("音乐路径为空");
        }

        if (StringUtils.isEmpty(musicDO.getMusicName())) {
            return CompletableFuture.completedFuture("音乐名字为空");
        }

        if (musicDO.getFatherId() == null) {
            return CompletableFuture.completedFuture("父ID为空");
        }

        musicDAO.add(musicDO);

        return CompletableFuture.completedFuture("success");
    }

    @Override
    @Async("async")
    public CompletableFuture<List<MusicDO>> searchByFatherId(Long fatherId) {
        if (fatherId == null) {
            return CompletableFuture.completedFuture(null);
        }

        List<MusicDO> musicDOS = musicDAO.selectAllByFatherId(fatherId);
        return CompletableFuture.completedFuture(musicDOS);
    }

    @Override
    @Async("async")
    public CompletableFuture<VideoDO> searchByImgName(String musicName) {
        return CompletableFuture.completedFuture(null);
    }
}
