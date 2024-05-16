package com.bishe.service;

import com.bishe.dataobject.UserDO;
import com.bishe.model.Attention;
import com.bishe.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {


    /**
     * 登陆服务
     *
     * @param userName 用户名
     * @param password 密码
     * @return 成功则返回用户ID
     **/
    CompletableFuture<String> login(String userName, String password);

    /**
     * 注册服务
     *
     * @param userDO 用户名
     * @return 返回信息
     **/
    CompletableFuture<String> sign(UserDO userDO);

    /**
     * 更新用户信息
     *
     * @param userDO 用户名
     * @return 返回信息
     **/
    CompletableFuture<String> update(UserDO userDO);

    /**
     * 更具id查找用户
     *
     * @param id Long
     * @return 返回UserDO
     **/
    CompletableFuture<UserDO> findById(Long id);

    /**
     * 更具邮箱查找用户
     *
     * @param email String
     * @return 返回UserDO
     **/
    CompletableFuture<UserDO> findByEmail(String email);

    /**
     * 更具userName查找用户
     *
     * @param userName 用户名
     * @return 返回UserDO
     **/
    CompletableFuture<UserDO> findByName(String userName);

    /**
     * 增加用户的关注值
     *
     * @param userId 用户ID
     * @return 返回信息
     **/
    CompletableFuture<String> addLikeNum(Long userId);

    /**
     * 减少用户的关注值
     *
     * @param userId 用户ID
     * @return 返回信息
     **/
    CompletableFuture<String> decLikeNum(Long userId);

    /**
     * 更新用户信息
     *
     * @param time int
     * @return 返回用户列表
     **/
    CompletableFuture<List<User>> findLimit(int time);

    /**
     * 更具关键字查询用户
     *
     * @param keyword String
     * @return 返回用户Id列表
     **/
    CompletableFuture<List<User>> findByKeyword(String keyword);

    /**
     * 关注用户
     *
     * @param attention Attention
     * @return 返回信息
     **/
    CompletableFuture<String> attention(Attention attention);

    /**
     * 取关用户
     *
     * @param attention Attention
     * @return 返回信息
     **/

    CompletableFuture<String> calAttention(Attention attention);

    /**
     * 检测用户是否被关注
     *
     * @param attention Attention
     * @return 返回信息
     **/
    CompletableFuture<Boolean> ifAttention(Attention attention);

    /**
     * 查找好友
     *
     * @param userId Long 用户ID
     * @return 返回好友列表
     **/
    CompletableFuture<List<User>> findFriend(Long userId);
}
