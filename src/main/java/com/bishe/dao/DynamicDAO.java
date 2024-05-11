package com.bishe.dao;

import com.bishe.dataobject.DynamicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DynamicDAO {

    int add(DynamicDO dynamicDO);

    int update(DynamicDO dynamicDO);

    int deleteById(Long id);

    DynamicDO findById(Long id);

    List<DynamicDO> findAll();

    List<DynamicDO> findByPage(int start, int limit);

    List<DynamicDO> findByAuthorId(Long authorId, int start, int limit);

    List<DynamicDO> findByTag(String tag, int start, int limit);

    List<DynamicDO> search(String keyword, int start, int limit);

    int addLikeNum(Long dynamicId);

    int decLikeNum(Long dynamicId);

}
