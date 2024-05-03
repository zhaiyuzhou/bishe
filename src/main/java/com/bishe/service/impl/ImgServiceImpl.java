package com.bishe.service.impl;

import com.bishe.dao.ImgDAO;
import com.bishe.dataobject.ImgDO;
import com.bishe.service.ImgService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {

    @Resource
    private ImgDAO imgDAO;

    @Override
    public String add(ImgDO imgDO) {

        if (imgDO == null) {
            return "传入的对象为空";
        }

        if (StringUtils.isEmpty(imgDO.getImgPath())) {
            return "图片路径为空";
        }

        if (StringUtils.isEmpty(imgDO.getImgName())) {
            return "图片名字为空";
        }

        if (imgDO.getFatherId() != null) {
            return "FatherId为空";
        }

        imgDAO.add(imgDO);

        return "success";
    }

    @Override
    public List<ImgDO> searchByFatherId(Long fatherId) {

        if (fatherId != null && fatherId > 0) {
            return null;
        }

        List<ImgDO> imgDOS = imgDAO.selectAllByFatherId(fatherId);

        return imgDOS;
    }

    @Override
    public ImgDO searchByImgName(String imgName) {

        if (StringUtils.isNotBlank(imgName)) {
            return null;
        }

        ImgDO imgDO = imgDAO.selectByImgName(imgName);

        return imgDO;
    }
}
