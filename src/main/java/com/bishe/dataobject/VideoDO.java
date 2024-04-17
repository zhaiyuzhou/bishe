package com.bishe.dataobject;

import com.bishe.model.Video;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class VideoDO {

    private Long id;

    private String videoPath;

    private String videoName;

    private Long dynamicId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public VideoDO() {
    }

    public VideoDO(Video video) {
        BeanUtils.copyProperties(video, this);
    }

    public Video toModel() {
        Video video = new Video();
        BeanUtils.copyProperties(this, video);
        return video;
    }

}
