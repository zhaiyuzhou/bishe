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

    private Long likeNum = 0L;

    private Long transPondNum = 0L;

    private Long transmitId;

    private LocalDateTime postedDate;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public DynamicDO() {

    }

    public DynamicDO(Dynamic dynamic) {
        BeanUtils.copyProperties(dynamic, this);
        this.setAuthorId(dynamic.getAuthor().getId());
        if (dynamic.getTransmit() != null) {
            this.setTransmitId(dynamic.getTransmit().getId());
        }
    }

    public Dynamic toModel() {
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(this, dynamic);
        return dynamic;
    }

}
