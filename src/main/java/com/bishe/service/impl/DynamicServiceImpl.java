package com.bishe.service.impl;

import com.bishe.dao.DynamicDAO;
import com.bishe.dataobject.DynamicDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Dynamic;
import com.bishe.service.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private DynamicDAO dynamicDAO;

    @Resource
    private UserService userService;

    @Resource
    private ImgService imgService;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private CommentService commentService;

    @Override
    public String add(DynamicDO dynamicDO) {

        if (dynamicDO == null) {
            return "dynamicDO为空";
        }

        if (dynamicDO.getId() == null) {
            return "dynamicId为空";
        }

        if (dynamicDO.getId() == 0) {
            return "dynamicId为0";
        }

        if (StringUtils.isEmpty(dynamicDO.getContent())) {
            return "content为空";
        }

        if (StringUtils.isEmpty(dynamicDO.getTag())) {
            return "tag为空";
        }

        if (dynamicDO.getPostedDate() == null) {
            return "postedDate为空";
        }

        int a = dynamicDAO.add(dynamicDO);

        if (a != 1) {
            return "插入数据库错误";
        }

        return "success";
    }

    @Override
    public DynamicDO findById(Long dynamicId) {

        if (dynamicId != null) {
            return dynamicDAO.findById(dynamicId);
        }
        return null;
    }

    @Override
    public List<Dynamic> findLimit(int times) {
        return listToDynamic(dynamicDAO.findByPage(times, times + 10));
    }

    @Override
    public List<Dynamic> findByTag(String tag, int times) {

        if (StringUtils.isEmpty(tag)) {
            return null;
        }
        return listToDynamic(dynamicDAO.findByTag(tag, times, times + 10));
    }

    @Override
    public List<Dynamic> findByAuthor(Long authorId, int times) {

        if (authorId == null) {
            return null;
        }

        return listToDynamic(dynamicDAO.findByAuthorId(authorId, times, times + 10));

    }

    @Override
    public List<Dynamic> search(String searchDate, int times) {

        if (StringUtils.isBlank(searchDate)) {
            return List.of();
        }

        return listToDynamic(dynamicDAO.search(searchDate, times, times + 10));
    }

    @Override
    public List<Dynamic> listToDynamic(List<DynamicDO> dynamicDOS) {
        if (dynamicDOS == null || dynamicDOS.isEmpty()) {
            return List.of();
        }

        List<Dynamic> dynamics = new ArrayList<>();

        dynamicDOS.forEach(dynamicDO -> {
            Dynamic dynamic = dynamicDO.toModel();
            dynamic.setAuthor(userService.findById(dynamicDO.getAuthorId()).toModel());
            List<ImgDO> imgDOS = imgService.searchByFatherId(dynamicDO.getId());
            List<VideoDO> videoDOS = videoService.searchByFatherId(dynamicDO.getId());
            List<MusicDO> musicDOS = musicService.searchByFatherId(dynamicDO.getId());

            if (imgDOS != null) {
                imgDOS.forEach(imgDO -> {
                    dynamic.addImg(imgDO.getImgPath());
                });
            }
            if (videoDOS != null) {
                videoDOS.forEach(videoDO -> {
                    dynamic.addVideo(videoDO.getVideoPath());
                });
            }
            if (musicDOS != null) {
                musicDOS.forEach(musicDO -> {
                    dynamic.addMusic(musicDO.getMusicPath());
                });
            }

            dynamic.setComments(commentService.findByDynamicId(dynamic.getId()));

            dynamics.add(dynamic);
        });

        if (!dynamics.isEmpty()) {
            return dynamics;
        }

        return List.of();
    }

    @Override
    public int update(DynamicDO dynamicDO) {

        if (dynamicDO != null) {
            return dynamicDAO.update(dynamicDO);
        }

        return 0;
    }

    @Override
    public String addLikeNum(Long dynamicId) {

        if (dynamicId != null) {
            if (dynamicDAO.addLikeNum(dynamicId) != 0) {
                return "success";
            }
        }
        return "失败";
    }
}
