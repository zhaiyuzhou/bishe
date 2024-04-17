package com.bishe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private String nickName;

    private String avatar;

    private String phone;

    private String gender;

    private Long likeNum = 0L;

    private List<String> dynamicIds;

    private List<String> commentIds;

    private List<String> friendIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

}
