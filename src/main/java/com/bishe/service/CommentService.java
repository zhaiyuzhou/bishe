package com.bishe.service;

import com.bishe.dataobject.CommentDO;

import java.util.List;

public interface CommentService {


    /**
     * 增加dynamic
     *
     * @param commentDO CommentDO对象
     * @return 返回服务信息
     **/
    String add(CommentDO commentDO);

    /**
     * 更具tag查找dynamic
     *
     * @param dynamicId Long
     * @return 返回comment列表
     **/
    List<CommentDO> findByDynamicId(Long dynamicId);

}
