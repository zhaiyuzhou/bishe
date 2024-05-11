package com.bishe.service;

import com.bishe.dataobject.CommentDO;
import com.bishe.model.Comment;
import com.bishe.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface CommentService {


    /**
     * 增加dynamic
     *
     * @param commentDO CommentDO对象
     * @return 返回服务信息
     **/
    CompletableFuture<String> add(CommentDO commentDO);

    /**
     * Id查找comment
     *
     * @param commentId Long
     * @return 返回comment
     **/
    CompletableFuture<CommentDO> findById(Long commentId);

    /**
     * 更具dynamicId查找comment
     *
     * @param dynamicId Long
     * @return 返回comment列表
     **/
    CompletableFuture<List<Comment>> findByDynamicId(Long dynamicId) throws ExecutionException, InterruptedException;

    /**
     * 包装Dynamic
     *
     * @param commentDOS commentDOS
     * @return 返回Comment列表
     **/
    CompletableFuture<List<Comment>> listToComment(List<CommentDO> commentDOS);

    /**
     * 增加喜欢数
     *
     * @param commentId commentId
     * @return 返回信息
     **/
    CompletableFuture<String> addLikeNum(Long commentId);

    /**
     * 减少喜欢数
     *
     * @param commentId commentId
     * @return 返回信息
     **/
    CompletableFuture<String> decLikeNum(Long commentId);

    /**
     * 更新
     *
     * @param commentDO commentDO
     * @return 返回信息
     **/
    CompletableFuture<Integer> update(CommentDO commentDO);

    /**
     * 分装comment
     *
     * @param map  前端传入的信息
     * @param user 发布评论的用户
     * @return 返回信息
     **/
    CompletableFuture<Comment> postComment(HashMap<String, Object> map, User user);

}
