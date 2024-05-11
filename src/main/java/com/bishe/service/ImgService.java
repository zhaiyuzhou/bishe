package com.bishe.service;

import com.bishe.dataobject.ImgDO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImgService {

    /**
     * 增加img
     *
     * @param imgDO ImgDO对象
     * @return 返回服务信息
     **/
    CompletableFuture<String> add(ImgDO imgDO);

    /**
     * 根据fatherId搜索服务
     *
     * @param fatherId Long
     * @return 返回ImgDO列表
     **/
    CompletableFuture<List<ImgDO>> searchByFatherId(Long fatherId);

    /**
     * 根据imgName搜索服务(imgName内包含日期)
     *
     * @param imgName 图片名字
     * @return 返回ImgDO
     **/
    CompletableFuture<ImgDO> searchByImgName(String imgName);

}
