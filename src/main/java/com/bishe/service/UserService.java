package com.bishe.service;

import com.bishe.dataobject.UserDO;

public interface UserService {


    /**
     * 登陆服务
     *
     * @param userName 用户名
     * @param password 密码
     * @return 成功则返回用户ID
     **/
    String login(String userName, String password);

    /**
     * 注册服务
     * @param userDO 用户名
     * @return 返回信息
     * **/
    String sign(UserDO userDO);

    /**
     * 更新用户信息
     *
     * @param userDO 用户名
     * @return 返回信息
     **/
    String update(UserDO userDO);

    /**
     * 更具id查找用户
     *
     * @param id id
     * @return 返回UserDO
     **/
    UserDO findById(Long id);


    /**
     * 更具userName查找用户
     *
     * @param userName 用户名
     * @return 返回UserDO
     **/
    UserDO findByName(String userName);
}
