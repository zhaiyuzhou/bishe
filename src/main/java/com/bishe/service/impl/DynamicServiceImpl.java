package com.bishe.service.impl;

import com.bishe.dao.DynamicDAO;
import com.bishe.dataobject.DynamicDO;
import com.bishe.service.DynamicService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private DynamicDAO dynamicDAO;

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
    public List<DynamicDO> findLimit() {
        return dynamicDAO.findByPage(0, 10);
    }

    @Override
    public List<DynamicDO> findByTag(String tag) {

        if (StringUtils.isEmpty(tag)) {
            return null;
        }

        return dynamicDAO.findByTag(tag, 0, 10);
    }

    @Override
    public List<DynamicDO> findByAuthor(Long authorId) {

        if (authorId == null) {
            return null;
        }

        return dynamicDAO.findByAuthorId(authorId, 0, 10);

    }
}
