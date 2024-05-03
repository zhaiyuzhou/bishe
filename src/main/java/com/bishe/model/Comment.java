package com.bishe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Comment {

    private Long id;

    private String content;

    private User author;

    private String fatherName;

    private Long dynamicId;

    private Long likeNum = 0L;

    private List<String> imgPaths = new ArrayList<>();

    private List<String> videoPaths = new ArrayList<>();

    private List<String> musicPaths = new ArrayList<>();

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

}
