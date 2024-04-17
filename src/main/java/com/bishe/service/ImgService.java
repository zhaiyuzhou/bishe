package com.bishe.service;

import com.bishe.dataobject.ImgDO;

import java.util.List;

public interface ImgService {

    /**
     * 增加img
     *
     * @param imgDO imgDO对象
     * @return 返回1成功，0失败
     **/
    String add(ImgDO imgDO);

    /**
     * 根据dynamicId搜索服务
     *
     * @param dynamicId 用户名
     * @return 返回信息
     **/
    List<ImgDO> searchByDynamicId(Long dynamicId);

    /**
     * 根据imgName搜索服务(imgName内包含日期)
     *
     * @param imgName 用户名
     * @return 返回信息
     **/
    ImgDO searchByImgName(String imgName);

}
