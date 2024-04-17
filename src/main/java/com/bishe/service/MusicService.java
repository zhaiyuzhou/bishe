package com.bishe.service;

import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;

import java.util.List;

public interface MusicService {

    /**
     * 增加music
     *
     * @param musicDO musicDO对象
     * @return 返回1成功，0失败
     **/
    String add(MusicDO musicDO);

    /**
     * 根据dynamicId搜索服务
     *
     * @param dynamicId 动态id
     * @return 返回信息
     **/
    List<MusicDO> searchByDynamicId(Long dynamicId);

    /**
     * 根据musicName搜索服务(musicName内包含日期)
     *
     * @param musicName 音乐文件名
     * @return 返回信息
     **/
    VideoDO searchByImgName(String musicName);

}
