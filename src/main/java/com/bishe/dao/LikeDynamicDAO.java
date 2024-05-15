package com.bishe.dao;

import com.bishe.dataobject.LikeDynamicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeDynamicDAO {

    int add(LikeDynamicDO likeDynamicDO);

    int delete(LikeDynamicDO likeDynamicDO);

    List<Long> findLikeDynamicIDsByUserId(Long userId);

}
