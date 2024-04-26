package com.bishe.dataobject;

import com.bishe.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class UserDO {
    private Long id;

    private String userName;

    private String password;

    private String email;

    private String nickName;

    private String gender;

    private String avatar;

    private String phone;

    private Long likeNum = 0L;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public UserDO() {
    }

    public UserDO(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toModel() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
