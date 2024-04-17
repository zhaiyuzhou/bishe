package com.bishe.dao;

import com.bishe.dataobject.ImgDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDAO {

    int add(ImgDO imgDO);

    List<ImgDO> selectAllByDynamicId(Long dynamicId);

    ImgDO selectByImgName(String imgName);

    int DeleteByDynamicId(Long dynamicId);

}
