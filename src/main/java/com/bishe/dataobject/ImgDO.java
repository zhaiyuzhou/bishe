package com.bishe.dataobject;

import com.bishe.model.Img;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class ImgDO {

    private Long id;

    private String imgName;

    private String imgPath;

    private Long fatherId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public ImgDO() {

    }

    public ImgDO(Img img) {
        BeanUtils.copyProperties(img, this);
    }

    public Img toModel() {
        Img img = new Img();
        BeanUtils.copyProperties(this, img);
        return img;
    }

}
