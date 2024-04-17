package com.bishe.dao;

import com.bishe.dataobject.MusicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicDAO {

    int add(MusicDO musicDO);

    List<MusicDO> selectAllByDynamicId(Long dynamicId);

    MusicDO selectByMusicName(String musicName);

    int DeleteByDynamicId(Long dynamicId);

}
