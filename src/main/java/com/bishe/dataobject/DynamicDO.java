package com.bishe.dataobject;

import com.bishe.model.Dynamic;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class DynamicDO {

    private Long id;

    private String content;

    private String tag;

    private Long authorId;

    private long likeNum = 0;

    private long transPondNum = 0;

    private LocalDateTime postedDate;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public DynamicDO() {

    }

    public DynamicDO(Dynamic dynamic) {
        BeanUtils.copyProperties(dynamic, this);
    }

    public Dynamic toModel() {
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(this, dynamic);
        return dynamic;
    }

}
