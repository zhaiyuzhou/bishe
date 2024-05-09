package com.bishe.service;

import com.bishe.dataobject.DynamicDO;
import com.bishe.model.Dynamic;

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
     * id查找dynamic
     *
     * @return 返回dynamicDO列表
     **/
    DynamicDO findById(Long dynamicId);

    /**
     * 无参数查找dynamic
     *
     * @return 返回dynamic列表
     **/
    List<Dynamic> findLimit(int times);

    /**
     * 更具tag查找dynamic
     *
     * @param tag tag
     * @return 返回dynamic列表
     **/
    List<Dynamic> findByTag(String tag, int times);

    /**
     * 更具authorId查找dynamic
     *
     * @param authorId Long
     * @return 返回dynamic列表
     **/
    List<Dynamic> findByAuthor(Long authorId, int times);

    /**
     * 更具关键字查找dynamic
     *
     * @param searchDate String
     * @return 返回dynamic列表
     **/
    List<Dynamic> search(String searchDate, int times);

    /**
     * 包装Dynamic
     *
     * @param dynamicDOList dynamicDOList
     * @return 返回dynamic列表
     **/
    List<Dynamic> listToDynamic(List<DynamicDO> dynamicDOList);

    /**
     * 更新Dynamic
     *
     * @param dynamicDO dynamicDO
     * @return 返回dynamic列表
     **/
    int update(DynamicDO dynamicDO);

    /**
     * 增加喜欢数
     *
     * @param dynamicId dynamicId
     * @return 返回信息
     **/
    String addLikeNum(Long dynamicId);


}
