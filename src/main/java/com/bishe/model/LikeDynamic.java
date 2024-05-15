package com.bishe.model;

import lombok.Data;

@Data
public class LikeDynamic {

    private Long id;

    private Long userId;

    private Long dynamicId;

    public LikeDynamic() {
    }

    public LikeDynamic(Long id, Long userId, Long dynamicId) {
        this.id = id;
        this.userId = userId;
        this.dynamicId = dynamicId;
    }

}
