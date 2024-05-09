package com.bishe.dataobject;

import com.bishe.model.Comment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class CommentDO {

    private Long id;

    private String content;

    private Long authorId;

    private String fatherName;

    private Long dynamicId;

    private Long likeNum;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public CommentDO() {

    }

    public CommentDO(Comment comment) {
        BeanUtils.copyProperties(comment, this);
        this.setAuthorId(comment.getAuthor().getId());
    }

    public Comment toModel() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }

}
