package com.bishe.service.impl;

import com.bishe.dao.MusicDAO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.service.MusicService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Resource
    private MusicDAO musicDAO;

    @Override
    public String add(MusicDO musicDO) {

        if (musicDO == null) {
            return "传入的对象为空";
        }

        if (StringUtils.isEmpty(musicDO.getMusicPath())) {
            return "音乐路径为空";
        }

        if (StringUtils.isEmpty(musicDO.getMusicName())) {
            return "音乐名字为空";
        }

//        if(musicDO.getDynamicId() != null){
//            return "dynamicId为空";
//        }

        musicDAO.add(musicDO);

        return "success";
    }

    @Override
    public List<MusicDO> searchByFatherId(Long dynamicId) {
        return List.of();
    }

    @Override
    public VideoDO searchByImgName(String musicName) {
        return null;
    }
}
