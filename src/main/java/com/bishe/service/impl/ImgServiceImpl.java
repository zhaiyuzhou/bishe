package com.bishe.service.impl;

import com.bishe.dao.ImgDAO;
import com.bishe.dataobject.ImgDO;
import com.bishe.service.ImgService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ImgServiceImpl implements ImgService {

    @Resource
    private ImgDAO imgDAO;

    @Override
    @Async("async")
    public CompletableFuture<String> add(ImgDO imgDO) {

        if (imgDO == null) {
            return CompletableFuture.completedFuture("传入的对象为空");
        }

        if (StringUtils.isEmpty(imgDO.getImgPath())) {
            return CompletableFuture.completedFuture("图片路径为空");
        }

        if (StringUtils.isEmpty(imgDO.getImgName())) {
            return CompletableFuture.completedFuture("图片名字为空");
        }

        if (imgDO.getFatherId() != null) {
            return CompletableFuture.completedFuture("FatherId为空");
        }

        imgDAO.add(imgDO);

        return CompletableFuture.completedFuture("success");
    }

    @Override
    @Async("async")
    public CompletableFuture<List<ImgDO>> searchByFatherId(Long fatherId) {

        if (fatherId == null) {
            return CompletableFuture.completedFuture(null);
        }
        List<ImgDO> imgDOS = imgDAO.selectAllByFatherId(fatherId);

        return CompletableFuture.completedFuture(imgDOS);
    }

    @Override
    @Async("async")
    public CompletableFuture<ImgDO> searchByImgName(String imgName) {

        if (StringUtils.isNotBlank(imgName)) {
            return CompletableFuture.completedFuture(null);
        }

        ImgDO imgDO = imgDAO.selectByImgName(imgName);

        return CompletableFuture.completedFuture(imgDO);
    }
}
