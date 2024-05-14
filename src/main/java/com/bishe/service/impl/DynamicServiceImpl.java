package com.bishe.service.impl;

import com.bishe.dao.DynamicDAO;
import com.bishe.dataobject.DynamicDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Dynamic;
import com.bishe.model.User;
import com.bishe.service.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private DynamicDAO dynamicDAO;

    @Resource
    private UserService userService;

    @Resource
    private ImgService imgService;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private CommentService commentService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private Long dynamicId() {
        //格式化格式为年月日
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //获取当前时间
        String now = LocalDate.now().format(dateTimeFormatter);
        //通过redis的自增获取序号
        RAtomicLong atomicLong = redissonClient.getAtomicLong("dynamic");
        long number = atomicLong.incrementAndGet();
        //拼装
        String orderId = now + number;
        return Long.parseLong(orderId);
    }

    @Override
    @Async("async")
    public CompletableFuture<String> add(DynamicDO dynamicDO) {

        if (dynamicDO == null) {
            return CompletableFuture.completedFuture("dynamicDO为空");
        }

        if (dynamicDO.getId() == null) {
            return CompletableFuture.completedFuture("dynamicId为空");
        }

        if (dynamicDO.getId() == 0) {
            return CompletableFuture.completedFuture("dynamicId为0");
        }

        if (StringUtils.isEmpty(dynamicDO.getContent())) {
            return CompletableFuture.completedFuture("content为空");
        }

        if (StringUtils.isEmpty(dynamicDO.getTag())) {
            return CompletableFuture.completedFuture("tag为空");
        }

        if (dynamicDO.getPostedDate() == null) {
            return CompletableFuture.completedFuture("postedDate为空");
        }

        int a = dynamicDAO.add(dynamicDO);

        if (a != 1) {
            return CompletableFuture.completedFuture("插入数据库错误");
        }

        return CompletableFuture.completedFuture("success");
    }

    @Override
    @Async("async")
    public CompletableFuture<DynamicDO> findById(Long dynamicId) {

        if (dynamicId != null) {
            return CompletableFuture.completedFuture(dynamicDAO.findById(dynamicId));
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Dynamic>> findLimit(int times) throws ExecutionException, InterruptedException {
        return CompletableFuture.completedFuture(listToDynamic(dynamicDAO.findByPage(times * 10, 10)).get());
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Dynamic>> findByTag(String tag, int times) throws ExecutionException, InterruptedException {

        if (StringUtils.isEmpty(tag)) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(listToDynamic(dynamicDAO.findByTag(tag, times * 10, 10)).get());
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Dynamic>> findByAuthor(Long authorId, int times) throws ExecutionException, InterruptedException {

        if (authorId == null) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(listToDynamic(dynamicDAO.findByAuthorId(authorId, times * 10, 10)).get());

    }

    @Override
    @Async("async")
    public CompletableFuture<List<Dynamic>> search(String searchDate, int times) throws ExecutionException, InterruptedException {

        if (StringUtils.isBlank(searchDate)) {
            return CompletableFuture.completedFuture(List.of());
        }

        return CompletableFuture.completedFuture(listToDynamic(dynamicDAO.search(searchDate, times * 10, 10)).get());
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Dynamic>> listToDynamic(List<DynamicDO> dynamicDOS) {
        if (dynamicDOS == null || dynamicDOS.isEmpty()) {
            return CompletableFuture.completedFuture(List.of());
        }

        List<Dynamic> dynamics = new ArrayList<>();

        dynamicDOS.forEach(dynamicDO -> {
            Dynamic dynamic = dynamicDO.toModel();
            try {
                dynamic.setAuthor(userService.findById(dynamicDO.getAuthorId()).get().toModel());
                if (dynamicDO.getTransmitId() != null) {
                    List<DynamicDO> dynamicDOS1 = new ArrayList<>();
                    dynamicDOS1.add(findById(dynamicDO.getTransmitId()).get());
                    dynamic.setTransmit(listToDynamic(dynamicDOS1).get().getFirst());
                }
                List<ImgDO> imgDOS = imgService.searchByFatherId(dynamicDO.getId()).get();
                List<VideoDO> videoDOS = videoService.searchByFatherId(dynamicDO.getId()).get();
                List<MusicDO> musicDOS = musicService.searchByFatherId(dynamicDO.getId()).get();

                if (imgDOS != null) {
                    imgDOS.forEach(imgDO -> {
                        dynamic.addImg(imgDO.getImgPath());
                    });
                }
                if (videoDOS != null) {
                    videoDOS.forEach(videoDO -> {
                        dynamic.addVideo(videoDO.getVideoPath());
                    });
                }
                if (musicDOS != null) {
                    musicDOS.forEach(musicDO -> {
                        dynamic.addMusic(musicDO.getMusicPath());
                    });
                }

                dynamic.setComments(commentService.findByDynamicId(dynamic.getId()).get());

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            dynamics.add(dynamic);
        });

        if (!dynamics.isEmpty()) {
            return CompletableFuture.completedFuture(dynamics);
        }

        return CompletableFuture.completedFuture(List.of());
    }

    @Override
    @Async("async")
    public CompletableFuture<Integer> update(DynamicDO dynamicDO) {

        if (dynamicDO != null) {
            return CompletableFuture.completedFuture(dynamicDAO.update(dynamicDO));
        }

        return CompletableFuture.completedFuture(0);
    }

    @Override
    @Async("async")
    public CompletableFuture<String> addLikeNum(Long dynamicId) {

        if (dynamicId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (dynamicDAO.addLikeNum(dynamicId) != 0) {
            return CompletableFuture.completedFuture("success");
        }
        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> decLikeNum(Long dynamicId) {

        if (dynamicId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (dynamicDAO.decLikeNum(dynamicId) != 0) {
            return CompletableFuture.completedFuture("success");
        }
        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> delDynamic(Long dynamicId) {

        if (dynamicDAO.deleteById(dynamicId) != 0) {
            return CompletableFuture.completedFuture("success");
        }
        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<Dynamic> postDynamic(HashMap<String, Object> map, User user) throws ExecutionException, InterruptedException {
        Dynamic dynamic = new Dynamic();
        Long id = dynamicId();
        dynamic.setId(id);
        dynamic.setContent(map.get("content").toString());
        dynamic.setTag(map.get("tag").toString());
        dynamic.setPostedDate(LocalDateTime.now());
        dynamic.setAuthor(user);
        if (map.get("transmitId") != null) {
            Long transmitId = Long.parseLong(map.get("transmitId").toString());
            dynamic.setTransmit(findById(transmitId).get().toModel());
            addLikeNum(transmitId);
        }

        List<String> names;

        names = (List<String>) map.get("imgName");
        names.forEach(imgName -> {
            ImgDO imgDO = (ImgDO) redisTemplate.opsForValue().get(imgName);
            if (imgDO != null) {
                imgDO.setFatherId(id);
            }
            imgService.add(imgDO);
            if (imgDO != null) {
                dynamic.addImg(imgDO.getImgPath());
            }
            redisTemplate.delete(imgName);
        });

        names = (List<String>) map.get("videoName");
        names.forEach(videoName -> {
            VideoDO videoDO = (VideoDO) redisTemplate.opsForValue().get(videoName);
            if (videoDO != null) {
                videoDO.setFatherId(id);
            }
            videoService.add(videoDO);
            if (videoDO != null) {
                dynamic.addVideo(videoDO.getVideoPath());
            }
            redisTemplate.delete(videoName);
        });

        names = (List<String>) map.get("musicName");
        names.forEach(musicName -> {
            MusicDO musicDO = (MusicDO) redisTemplate.opsForValue().get(musicName);
            if (musicDO != null) {
                musicDO.setFatherId(id);
            }
            musicService.add(musicDO);
            if (musicDO != null) {
                dynamic.addMusic(musicDO.getMusicPath());
            }
            redisTemplate.delete(musicName);
        });

        return CompletableFuture.completedFuture(dynamic);
    }
}
