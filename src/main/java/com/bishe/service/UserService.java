package com.bishe.service;

import com.bishe.dataobject.UserDO;

public interface UserService {

    String login(String userName, String password);

    String sign(UserDO userDO);

}
