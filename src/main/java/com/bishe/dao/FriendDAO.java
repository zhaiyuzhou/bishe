package com.bishe.dao;

import com.bishe.dataobject.FriendDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendDAO {

    int add(FriendDO friendDO);

    int delete(FriendDO friendDO);

    List<Long> findByUserId(Long userId);
}
