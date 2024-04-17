package com.bishe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Dynamic {

    private Long id;

    private String content;

    private String imgUrl;

    private String videoUrl;

    private String musicUrl;

    private String tag;

    private String authorId;

    private long likeNum;

    private long transPondNum;

    private LocalDateTime postedDate;

    private List<String> commentIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
}
