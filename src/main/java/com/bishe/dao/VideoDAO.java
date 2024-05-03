package com.bishe.dao;

import com.bishe.dataobject.VideoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDAO {

    int add(VideoDO videoDO);

    List<VideoDO> selectAllByFatherId(Long fatherId);

    VideoDO selectByVideoName(String videoName);

    int DeleteByFatherId(Long fatherId);

}
