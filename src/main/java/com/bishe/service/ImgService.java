package com.bishe.service;

import com.bishe.dataobject.ImgDO;

import java.util.List;

public interface ImgService {

    /**
     * 增加img
     *
     * @param imgDO ImgDO对象
     * @return 返回服务信息
     **/
    String add(ImgDO imgDO);

    /**
     * 根据fatherId搜索服务
     *
     * @param fatherId Long
     * @return 返回ImgDO列表
     **/
    List<ImgDO> searchByFatherId(Long fatherId);

    /**
     * 根据imgName搜索服务(imgName内包含日期)
     *
     * @param imgName 图片名字
     * @return 返回ImgDO
     **/
    ImgDO searchByImgName(String imgName);

}
