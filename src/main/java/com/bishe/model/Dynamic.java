package com.bishe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Dynamic implements Serializable {

    private Long id;

    private String content;

    private List<String> imgPaths = new ArrayList<>();

    private List<String> videoPaths = new ArrayList<>();
    ;

    private List<String> musicPaths = new ArrayList<>();
    ;

    private String tag;

    private User author;

    private long likeNum;

    private long transPondNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime postedDate;

    private List<String> comments = new ArrayList<>();
    ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;

    public void addImg(String imgPath) {
        imgPaths.add(imgPath);
    }

    public void addVideo(String videoPath) {
        videoPaths.add(videoPath);
    }

    public void addMusic(String musicPath) {
        musicPaths.add(musicPath);
    }

    public void addComment(String commentPath) {
        comments.add(commentPath);
    }
}
