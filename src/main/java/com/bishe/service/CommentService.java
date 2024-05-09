package com.bishe.service;

import com.bishe.dataobject.CommentDO;
import com.bishe.model.Comment;

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
     * Id查找comment
     *
     * @param commentId Long
     * @return 返回comment
     **/
    CommentDO findById(Long commentId);

    /**
     * 更具dynamicId查找comment
     *
     * @param dynamicId Long
     * @return 返回comment列表
     **/
    List<Comment> findByDynamicId(Long dynamicId);

    /**
     * 包装Dynamic
     *
     * @param commentDOS commentDOS
     * @return 返回Comment列表
     **/
    List<Comment> listToComment(List<CommentDO> commentDOS);

    /**
     * 增加喜欢数
     *
     * @param commentId commentId
     * @return 返回信息
     **/
    String addLikeNum(Long commentId);

    /**
     * 更新
     *
     * @param commentDO commentDO
     * @return 返回信息
     **/

    int update(CommentDO commentDO);

}
