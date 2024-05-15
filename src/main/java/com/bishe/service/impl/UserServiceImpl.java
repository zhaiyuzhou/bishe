package com.bishe.service.impl;

import com.bishe.dao.AttentionDAO;
import com.bishe.dao.UserDAO;
import com.bishe.dataobject.AttentionDO;
import com.bishe.dataobject.UserDO;
import com.bishe.model.Attention;
import com.bishe.model.User;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDAO userDAO;

    @Resource
    AttentionDAO attentionDAO;

    @Override
    @Async("async")
    public CompletableFuture<String> login(String userName, String password) {

        if (StringUtils.isEmpty(userName)) {
            return CompletableFuture.completedFuture("username为空");
        }
        if (StringUtils.isEmpty(password)) {
            return CompletableFuture.completedFuture("password为空");
        }
        UserDO userDO = userDAO.selectByUserName(userName);

        if (userDO == null) {
            return CompletableFuture.completedFuture("没有该用户");
        }

        if (userDO.getPassword().equals(password)) {
            return CompletableFuture.completedFuture("success");
        }

        return CompletableFuture.completedFuture("密码不正确");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> sign(UserDO userDO) {

        if (userDO == null) {
            return CompletableFuture.completedFuture("传入参数为空");
        }

        String username = userDO.getUserName();
        if (StringUtils.isEmpty(username)) {
            return CompletableFuture.completedFuture("传入用户名为空");
        }

        if (StringUtils.isEmpty(userDO.getPassword())) {
            return CompletableFuture.completedFuture("传入密码为空");
        }

        String email = userDO.getEmail();
        if (StringUtils.isEmpty(email)) {
            return CompletableFuture.completedFuture("传入邮箱为空");
        }

        if (StringUtils.isEmpty(userDO.getGender())) {
            return CompletableFuture.completedFuture("传入性别为空");
        }

        String phone = userDO.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return CompletableFuture.completedFuture("传入电话为空");
        }

        if (StringUtils.isEmpty(userDO.getNickName())) {
            return CompletableFuture.completedFuture("传入昵称为空");
        }

        if (userDAO.selectByUserName(username) != null) {
            return CompletableFuture.completedFuture("用户名已被注册");
        }

        if (userDAO.selectByEmail(email) != null) {
            return CompletableFuture.completedFuture("邮箱已被注册");
        }

        if (userDAO.selectByPhone(phone) != null) {
            return CompletableFuture.completedFuture("电话已被注册");
        }

        userDAO.add(userDO);

        return CompletableFuture.completedFuture("success");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> update(UserDO userDO) {

        if (userDO == null) {
            return CompletableFuture.completedFuture("userDO为空");
        }

        int a = userDAO.update(userDO);

        if (a == 1) {
            return CompletableFuture.completedFuture("success");
        }
        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<UserDO> findById(Long id) {
        if (id == null) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(userDAO.selectById(id));
    }

    @Override
    public CompletableFuture<UserDO> findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(userDAO.selectByEmail(email));
    }

    @Override
    @Async("async")
    public CompletableFuture<UserDO> findByName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(userDAO.selectByUserName(userName));
    }

    @Override
    @Async("async")
    public CompletableFuture<String> addLikeNum(Long userId) {
        if (userId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (userDAO.addLikeNum(userId) != 0) {
            return CompletableFuture.completedFuture("success");
        }

        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> decLikeNum(Long userId) {
        if (userId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (userDAO.decLikeNum(userId) != 0) {
            return CompletableFuture.completedFuture("success");
        }

        return CompletableFuture.completedFuture("失败");
    }

    @Override
    public CompletableFuture<List<User>> findLimit(int time) {
        List<UserDO> userDOS = userDAO.selectLimit(time * 10, 10);
        List<User> users = new ArrayList<>();
        userDOS.forEach(userDO -> {
            users.add(userDO.toModel());
        });
        return CompletableFuture.completedFuture(users);
    }

    @Override
    public CompletableFuture<List<User>> findByKeyword(String keyword) {
        List<UserDO> userDOS = userDAO.searchIdByKeywordForUsername(keyword);
        List<User> users = new ArrayList<>();
        userDOS.forEach(userDO -> {
            users.add(userDO.toModel());
        });
        return CompletableFuture.completedFuture(users);
    }

    @Override
    public CompletableFuture<String> attention(Attention attention) {
        if (attention == null) {
            return CompletableFuture.completedFuture("传入对象为空");
        }
        AttentionDO attentionDO = new AttentionDO(attention);
        attentionDAO.add(attentionDO);

        return CompletableFuture.completedFuture("success");
    }

    @Override
    public CompletableFuture<String> calAttention(Attention attention) {
        if (attention == null) {
            return CompletableFuture.completedFuture("传入对象为空");
        }
        AttentionDO attentionDO = new AttentionDO(attention);
        attentionDAO.delete(attentionDO);
        return CompletableFuture.completedFuture("success");
    }
}
