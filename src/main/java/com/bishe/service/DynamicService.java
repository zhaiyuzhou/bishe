package com.bishe.service;

import com.bishe.dataobject.DynamicDO;

import java.util.List;

public interface DynamicService {

    /**
     * 增加dynamic
     *
     * @param dynamicDO DynamicDO对象
     * @return 返回服务信息
     **/
    String add(DynamicDO dynamicDO);

    /**
     * 无参数查找dynamic
     *
     * @return 返回dynamic列表
     **/
    List<DynamicDO> findLimit();

    /**
     * 更具tag查找dynamic
     *
     * @param tag tag
     * @return 返回dynamic列表
     **/
    List<DynamicDO> findByTag(String tag);

    /**
     * 更具authorId查找dynamic
     *
     * @param authorId authorId
     * @return 返回dynamic列表
     **/
    List<DynamicDO> findByAuthor(Long authorId);
}
