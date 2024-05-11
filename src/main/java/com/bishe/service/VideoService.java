package com.bishe.service;

import com.bishe.dataobject.VideoDO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VideoService {

    /**
     * 增加video
     *
     * @param videoDO VideoDO对象
     * @return 返回服务信息
     **/
    CompletableFuture<String> add(VideoDO videoDO);

    /**
     * 根据fatherId搜索服务
     *
     * @param fatherId 动态id
     * @return 返回信息
     **/
    CompletableFuture<List<VideoDO>> searchByFatherId(Long fatherId);

    /**
     * 根据videoName搜索服务(videoName内包含日期)
     *
     * @param videoName 视频文件名
     * @return 返回信息
     **/
    CompletableFuture<VideoDO> searchByImgName(String videoName);

}
