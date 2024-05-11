package com.bishe.service;

import com.bishe.dataobject.DynamicDO;
import com.bishe.model.Dynamic;
import com.bishe.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface DynamicService {

    /**
     * 增加dynamic
     *
     * @param dynamicDO DynamicDO对象
     * @return 返回服务信息
     **/
    CompletableFuture<String> add(DynamicDO dynamicDO);

    /**
     * id查找dynamic
     *
     * @return 返回dynamicDO列表
     **/
    CompletableFuture<DynamicDO> findById(Long dynamicId);

    /**
     * 无参数查找dynamic
     *
     * @return 返回dynamic列表
     **/
    CompletableFuture<List<Dynamic>> findLimit(int times) throws ExecutionException, InterruptedException;

    /**
     * 更具tag查找dynamic
     *
     * @param tag tag
     * @return 返回dynamic列表
     **/
    CompletableFuture<List<Dynamic>> findByTag(String tag, int times) throws ExecutionException, InterruptedException;

    /**
     * 更具authorId查找dynamic
     *
     * @param authorId Long
     * @return 返回dynamic列表
     **/
    CompletableFuture<List<Dynamic>> findByAuthor(Long authorId, int times) throws ExecutionException, InterruptedException;

    /**
     * 更具关键字查找dynamic
     *
     * @param searchDate String
     * @return 返回dynamic列表
     **/
    CompletableFuture<List<Dynamic>> search(String searchDate, int times) throws ExecutionException, InterruptedException;

    /**
     * 包装Dynamic
     *
     * @param dynamicDOList dynamicDOList
     * @return 返回dynamic列表
     **/
    CompletableFuture<List<Dynamic>> listToDynamic(List<DynamicDO> dynamicDOList);

    /**
     * 更新Dynamic
     *
     * @param dynamicDO dynamicDO
     * @return 返回dynamic列表
     **/
    CompletableFuture<Integer> update(DynamicDO dynamicDO);

    /**
     * 增加喜欢数
     *
     * @param dynamicId dynamicId
     * @return 返回信息
     **/
    CompletableFuture<String> addLikeNum(Long dynamicId);

    /**
     * 减少喜欢数
     *
     * @param dynamicId dynamicId
     * @return 返回信息
     **/
    CompletableFuture<String> decLikeNum(Long dynamicId);

    /**
     * 删除动态
     *
     * @param dynamicId dynamicId
     * @return 返回信息
     **/
    CompletableFuture<String> delDynamic(Long dynamicId);

    /**
     * 分装dynamic
     *
     * @param map  前端传入的信息
     * @param user 发布动态的用户
     * @return 返回信息
     **/
    CompletableFuture<Dynamic> postDynamic(HashMap<String, Object> map, User user);
}
