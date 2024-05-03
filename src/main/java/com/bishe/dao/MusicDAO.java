package com.bishe.dao;

import com.bishe.dataobject.MusicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicDAO {

    int add(MusicDO musicDO);

    List<MusicDO> selectAllByFatherId(Long fatherId);

    MusicDO selectByMusicName(String musicName);

    int DeleteByFatherId(Long fatherId);

}
