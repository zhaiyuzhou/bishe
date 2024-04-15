package com.bishe.dao;

import com.bishe.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    int add(UserDO userDO);

    UserDO selectByUserName(String userName);

    UserDO selectByEmail(String email);

    UserDO selectByPhone(String phone);

}
