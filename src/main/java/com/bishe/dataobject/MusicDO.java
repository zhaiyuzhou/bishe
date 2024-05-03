package com.bishe.dataobject;

import com.bishe.model.Music;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class MusicDO {

    private Long id;

    private String musicPath;

    private String musicName;

    private Long fatherId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public MusicDO() {
    }

    public MusicDO(Music music) {
        BeanUtils.copyProperties(music, this);
    }

    public Music toModel() {
        Music music = new Music();
        BeanUtils.copyProperties(this, music);
        return music;
    }
}
