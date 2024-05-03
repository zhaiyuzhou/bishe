package com.bishe.dataobject;

import com.bishe.model.Dynamic;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class DynamicDO {

    @Resource
    private UserService userService;

    private Long id;

    private String content;

    private String tag;

    private Long authorId;

    private Long likeNum = 0L;

    private Long transPondNum = 0L;

    private LocalDateTime postedDate;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public DynamicDO() {

    }

    public DynamicDO(Dynamic dynamic) {
        BeanUtils.copyProperties(dynamic, this);
        this.setAuthorId(dynamic.getAuthor().getId());
    }

    public Dynamic toModel() {
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(this, dynamic);
        dynamic.setAuthor(userService.findById(this.getAuthorId()).toModel());
        return dynamic;
    }

}
