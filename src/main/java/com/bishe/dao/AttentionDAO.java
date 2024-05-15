package com.bishe.dao;

import com.bishe.dataobject.AttentionDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttentionDAO {

    int add(AttentionDO attentionDO);

    int delete(AttentionDO attentionDO);
}
