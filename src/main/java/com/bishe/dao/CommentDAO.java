package com.bishe.dao;

import com.bishe.dataobject.CommentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDAO {

    int add(CommentDO commentDO);

    int update(CommentDO commentDO);

    int deleteById(Long id);

    CommentDO findById(Long id);

    List<CommentDO> findAll();

    List<CommentDO> findByPage(Long start, int limit);

    List<CommentDO> findByAuthorId(Long authorId, int start, int limit);

    List<CommentDO> findByDynamicId(Long dynamicId, int start, int limit);

    int addLikeNum(Long commentId);

    int decLikeNum(Long commentId);
}
