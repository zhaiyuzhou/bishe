package com.bishe.service.impl;

import com.bishe.dao.CommentDAO;
import com.bishe.dataobject.CommentDO;
import com.bishe.service.CommentService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

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
    public List<CommentDO> findByDynamicId(Long dynamicId) {

        if (dynamicId == null) {
            return null;
        }

        return commentDAO.findByDynamicId(dynamicId, 0, 10);
    }

}
