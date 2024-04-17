package com.bishe.dao;

import com.bishe.dataobject.VideoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDAO {

    int add(VideoDO videoDO);

    List<VideoDO> selectAllByDynamicId(Long dynamicId);

    VideoDO selectByVideoName(String videoName);

    int DeleteByDynamicId(Long dynamicId);

}
