package com.bishe.service;

import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MusicService {

    /**
     * 增加music
     *
     * @param musicDO MusicDO对象
     * @return 返回服务信息
     **/
    CompletableFuture<String> add(MusicDO musicDO);

    /**
     * 根据fatherId搜索服务
     *
     * @param fatherId 动态id
     * @return 返回信息
     **/
    CompletableFuture<List<MusicDO>> searchByFatherId(Long fatherId);

    /**
     * 根据musicName搜索服务(musicName内包含日期)
     *
     * @param musicName 音乐文件名
     * @return 返回信息
     **/
    CompletableFuture<VideoDO> searchByImgName(String musicName);

}
