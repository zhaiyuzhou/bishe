package com.bishe.service;

import com.bishe.dataobject.UserDO;

public interface UserService {


    /**
     * 登陆服务
     *
     * @param userName 用户名
     * @param password 密码
     * @return 返回信息
     **/
    String login(String userName, String password);

    /**
     * 注册服务
     * @param userDO 用户名
     * @return 返回信息
     * **/
    String sign(UserDO userDO);

}
