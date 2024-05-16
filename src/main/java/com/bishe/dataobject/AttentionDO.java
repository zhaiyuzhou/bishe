package com.bishe.dataobject;

import com.bishe.model.Attention;
import lombok.Data;

@Data
public class AttentionDO {

    private Long id;

    private Long userId;

    private Long otherId;

    public AttentionDO(Long id, Long userId, Long otherId) {
        this.id = id;
        this.userId = userId;
        this.otherId = otherId;
    }

    public AttentionDO(Long userId, Long otherId) {
        this.userId = userId;
        this.otherId = otherId;
    }

    public AttentionDO(Attention attention) {
        this.id = attention.getId();
        this.userId = attention.getUserId();
        this.otherId = attention.getOtherId();
    }

    public Attention toModel() {
        Attention attention = new Attention();
        attention.setId(id);
        attention.setUserId(userId);
        attention.setOtherId(otherId);
        return attention;
    }

    public void reversal() {
        Long tmp = this.userId;
        this.userId = otherId;
        this.otherId = tmp;
    }
}
