package com.bishe.model;

import lombok.Data;

@Data
public class Attention {

    private Long id;

    private Long userId;

    private Long otherId;

    public Attention() {

    }

    public Attention(Long userId, Long otherId) {
        this.userId = userId;
        this.otherId = otherId;
    }
}
