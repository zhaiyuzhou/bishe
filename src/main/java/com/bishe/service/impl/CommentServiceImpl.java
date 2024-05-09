package com.bishe.service.impl;

import com.bishe.dao.CommentDAO;
import com.bishe.dataobject.CommentDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Comment;
import com.bishe.service.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Resource
    private UserService userService;

    @Resource
    private ImgService imgService;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Override
    public String add(CommentDO commentDO) {

        if (commentDO == null) {
            return "传递的CommentDO对象为空";
        }

        if (commentDO.getId() == null) {
            return "commentID为空";
        }

        if (commentDO.getAuthorId() == null) {
            return "AuthorId为空";
        }

        if (StringUtils.isBlank(commentDO.getContent())) {
            return "Content为空";
        }

        if (commentDO.getDynamicId() == null) {
            return "评论的id为空";
        }

        if (StringUtils.isBlank(commentDO.getFatherName())) {
            return "父名字为空";
        }

        int a = commentDAO.add(commentDO);

        if (a == -1) {
            return "插入数据库失败";
        }

        return "success";
    }

    @Override
    public CommentDO findById(Long commentId) {
        if (commentId != null) {
            return commentDAO.findById(commentId);
        }
        return null;
    }

    @Override
    public List<Comment> findByDynamicId(Long dynamicId) {

        if (dynamicId == null) {
            return null;
        }

        return listToComment(commentDAO.findByDynamicId(dynamicId, 0, 10));
    }

    @Override
    public List<Comment> listToComment(List<CommentDO> commentDOS) {

        if (commentDOS == null || commentDOS.isEmpty()) {
            return List.of();
        }

        List<Comment> comments = new ArrayList<>();
        commentDOS.forEach(commentDO -> {

            Comment comment = commentDO.toModel();
            comment.setAuthor(userService.findById(commentDO.getAuthorId()).toModel());

            List<ImgDO> imgDOS = imgService.searchByFatherId(commentDO.getId());
            if (imgDOS != null && !imgDOS.isEmpty()) {
                imgDOS.forEach(imgDO -> {
                    comment.addImg(imgDO.getImgPath());
                });
            }

            List<VideoDO> videoDOS = videoService.searchByFatherId(commentDO.getId());
            if (videoDOS != null && !videoDOS.isEmpty()) {
                videoDOS.forEach(videoDO -> {
                    comment.addVideo(videoDO.getVideoPath());
                });
            }
            List<MusicDO> musicDOS = musicService.searchByFatherId(commentDO.getId());
            if (musicDOS != null && !musicDOS.isEmpty()) {
                musicDOS.forEach(musicDO -> {
                    comment.addMusic(musicDO.getMusicPath());
                });
            }

            comments.add(comment);

        });

        if (!comments.isEmpty()) {
            return comments;
        }

        return List.of();
    }

    @Override
    public String addLikeNum(Long commentId) {

        if (commentId != null) {
            if (commentDAO.addLikeNum((commentId)) != 0)
                return "success";
        }
        return "失败";
    }

    @Override
    public int update(CommentDO commentDO) {

        if (commentDO != null) {
            return commentDAO.update(commentDO);
        }

        return 0;
    }


}
