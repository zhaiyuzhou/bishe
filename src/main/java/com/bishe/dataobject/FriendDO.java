package com.bishe.dataobject;

import lombok.Data;

@Data
public class FriendDO {

    private Long id;

    private Long userId;

    private Long friendId;

    public FriendDO() {
    }

    public FriendDO(Long id, Long userId, Long friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }

    public FriendDO(AttentionDO attentionDO) {
        this.userId = attentionDO.getUserId();
        this.friendId = attentionDO.getOtherId();
    }

    public void reversal() {
        Long tmp = this.userId;
        this.userId = this.friendId;
        this.friendId = tmp;
    }
}
