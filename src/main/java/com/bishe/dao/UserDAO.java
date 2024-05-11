package com.bishe.dao;

import com.bishe.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    int add(UserDO userDO);

    UserDO selectById(Long id);

    UserDO selectByUserName(String userName);

    UserDO selectByEmail(String email);

    UserDO selectByPhone(String phone);

    int update(UserDO userDO);

    int deleteById(Long id);

    int deleteByUserName(String userName);

    int addLikeNum(Long id);

    int decLikeNum(Long id);

}
