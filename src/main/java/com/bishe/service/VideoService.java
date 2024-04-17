package com.bishe.service;

import com.bishe.dataobject.VideoDO;

import java.util.List;

public interface VideoService {

    /**
     * 增加video
     *
     * @param videoDO videoDO对象
     * @return 返回1成功，0失败
     **/
    String add(VideoDO videoDO);

    /**
     * 根据dynamicId搜索服务
     *
     * @param dynamicId 动态id
     * @return 返回信息
     **/
    List<VideoDO> searchByDynamicId(Long dynamicId);

    /**
     * 根据videoName搜索服务(videoName内包含日期)
     *
     * @param videoName 视频文件名
     * @return 返回信息
     **/
    VideoDO searchByImgName(String videoName);

}
