package com.bishe.dataobject;

import com.bishe.model.LikeDynamic;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LikeDynamicDO {

    private Long id;

    private Long userId;

    private Long dynamicId;

    public LikeDynamicDO() {

    }

    public LikeDynamicDO(LikeDynamic likeDynamic) {
        BeanUtils.copyProperties(likeDynamic, this);
    }

    public LikeDynamic toModel() {
        LikeDynamic likeDynamic = new LikeDynamic();
        BeanUtils.copyProperties(this, likeDynamic);
        return likeDynamic;
    }

}
