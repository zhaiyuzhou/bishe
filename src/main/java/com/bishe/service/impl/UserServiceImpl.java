package com.bishe.service.impl;

import com.bishe.dao.UserDAO;
import com.bishe.dataobject.UserDO;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDAO userDAO;

    @Override
    public String login(String userName, String password) {

        if (StringUtils.isEmpty(userName)) {
            return "username为空";
        }
        if (StringUtils.isEmpty(password)) {
            return "password为空";
        }
        UserDO userDO = userDAO.selectByUserName(userName);

        if (userDO == null) {
            return "没有该用户";
        }

        if (userDO.getPassword().equals(password)) {
            return "success";
        }

        return "密码不正确";
    }

    @Override
    public String sign(UserDO userDO) {

        if (userDO == null) {
            return "传入参数为空";
        }

        String username = userDO.getUserName();
        if (StringUtils.isEmpty(username)) {
            return "传入用户名为空";
        }

        if (StringUtils.isEmpty(userDO.getPassword())) {
            return "传入密码为空";
        }

        String email = userDO.getEmail();
        if (StringUtils.isEmpty(email)) {
            return "传入邮箱为空";
        }

        if (StringUtils.isEmpty(userDO.getGender())) {
            return "传入性别为空";
        }

        String phone = userDO.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return "传入电话为空";
        }

        if (StringUtils.isEmpty(userDO.getNickName())) {
            return "传入昵称为空";
        }

        if (userDAO.selectByUserName(username) != null) {
            return "用户名已被注册";
        }

        if (userDAO.selectByEmail(email) != null) {
            return "邮箱已被注册";
        }

        if (userDAO.selectByPhone(phone) != null) {
            return "电话已被注册";
        }

        userDAO.add(userDO);

        return "success";
    }

    @Override
    public String update(UserDO userDO) {

        if (userDO == null) {
            return "userDO为空";
        }

        int a = userDAO.update(userDO);

        if (a == 1) {
            return "success";
        }
        return "失败";
    }

    @Override
    public UserDO findById(Long id) {
        if (id == null) {
            return null;
        }
        return userDAO.selectById(id);
    }

    @Override
    public UserDO findByName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        return userDAO.selectByUserName(userName);
    }
}
