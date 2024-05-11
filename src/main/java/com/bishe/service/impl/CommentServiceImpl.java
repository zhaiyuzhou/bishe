package com.bishe.service.impl;

import com.bishe.dao.CommentDAO;
import com.bishe.dataobject.CommentDO;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.MusicDO;
import com.bishe.dataobject.VideoDO;
import com.bishe.model.Comment;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Resource
    private UserService userService;

    @Resource
    private ImgService imgService;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private Long commentId() {
        //格式化格式为年月日
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //获取当前时间
        String now = LocalDate.now().format(dateTimeFormatter);
        //通过redis的自增获取序号
        RAtomicLong atomicLong = redissonClient.getAtomicLong("comment");
        long number = atomicLong.incrementAndGet();
        //拼装
        String orderId = now + number;
        return Long.parseLong(orderId);
    }

    @Override
    @Async("async")
    public CompletableFuture<String> add(CommentDO commentDO) {

        if (commentDO == null) {
            return CompletableFuture.completedFuture("传递的CommentDO对象为空");
        }

        if (commentDO.getId() == null) {
            return CompletableFuture.completedFuture("commentID为空");
        }

        if (commentDO.getAuthorId() == null) {
            return CompletableFuture.completedFuture("AuthorId为空");
        }

        if (StringUtils.isBlank(commentDO.getContent())) {
            return CompletableFuture.completedFuture("Content为空");
        }

        if (commentDO.getDynamicId() == null) {
            return CompletableFuture.completedFuture("评论的id为空");
        }

        if (StringUtils.isBlank(commentDO.getFatherName())) {
            return CompletableFuture.completedFuture("父名字为空");
        }

        int a = commentDAO.add(commentDO);

        if (a == -1) {
            return CompletableFuture.completedFuture("插入数据库失败");
        }

        return CompletableFuture.completedFuture("success");
    }

    @Override
    @Async("async")
    public CompletableFuture<CommentDO> findById(Long commentId) {
        if (commentId != null) {
            return CompletableFuture.completedFuture(commentDAO.findById(commentId));
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Comment>> findByDynamicId(Long dynamicId) throws ExecutionException, InterruptedException {

        if (dynamicId == null) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(listToComment(commentDAO.findByDynamicId(dynamicId, 0, 10)).get());
    }

    @Override
    @Async("async")
    public CompletableFuture<List<Comment>> listToComment(List<CommentDO> commentDOS) {

        if (commentDOS == null || commentDOS.isEmpty()) {
            return CompletableFuture.completedFuture(List.of());
        }

        List<Comment> comments = new ArrayList<>();
        commentDOS.forEach(commentDO -> {

            Comment comment = commentDO.toModel();
            try {
                comment.setAuthor(userService.findById(commentDO.getAuthorId()).get().toModel());
                List<ImgDO> imgDOS = imgService.searchByFatherId(commentDO.getId()).get();
                if (imgDOS != null && !imgDOS.isEmpty()) {
                    imgDOS.forEach(imgDO -> {
                        comment.addImg(imgDO.getImgPath());
                    });
                }

                List<VideoDO> videoDOS = videoService.searchByFatherId(commentDO.getId()).get();
                if (videoDOS != null && !videoDOS.isEmpty()) {
                    videoDOS.forEach(videoDO -> {
                        comment.addVideo(videoDO.getVideoPath());
                    });
                }
                List<MusicDO> musicDOS = musicService.searchByFatherId(commentDO.getId()).get();
                if (musicDOS != null && !musicDOS.isEmpty()) {
                    musicDOS.forEach(musicDO -> {
                        comment.addMusic(musicDO.getMusicPath());
                    });
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            comments.add(comment);

        });

        if (!comments.isEmpty()) {
            return CompletableFuture.completedFuture(comments);
        }

        return CompletableFuture.completedFuture(List.of());
    }

    @Override
    @Async("async")
    public CompletableFuture<String> addLikeNum(Long commentId) {

        if (commentId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (commentDAO.addLikeNum((commentId)) != 0)
            return CompletableFuture.completedFuture("success");

        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<String> decLikeNum(Long commentId) {
        if (commentId == null) {
            return CompletableFuture.completedFuture("传入ID为空");
        }

        if (commentDAO.decLikeNum(commentId) != 0)
            return CompletableFuture.completedFuture("success");

        return CompletableFuture.completedFuture("失败");
    }

    @Override
    @Async("async")
    public CompletableFuture<Integer> update(CommentDO commentDO) {

        if (commentDO != null) {
            return CompletableFuture.completedFuture(commentDAO.update(commentDO));
        }

        return CompletableFuture.completedFuture(0);
    }

    @Override
    @Async("async")
    public CompletableFuture<Comment> postComment(HashMap<String, Object> map, User user) {

        Comment comment = new Comment();
        // ID创建
        Long id = commentId();
        comment.setId(id);

        comment.setContent(map.get("content").toString());
        comment.setAuthor(user);

        comment.setDynamicId(Long.valueOf(map.get("dynamicId").toString()));
        if (map.get("father") == null || StringUtils.isBlank(map.get("father").toString())) {
            comment.setFatherName("none");
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
                comment.addImg(imgDO.getImgPath());
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
                comment.addVideo(videoDO.getVideoPath());
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
                comment.addMusic(musicDO.getMusicPath());
            }
            redisTemplate.delete(musicName);
        });


        return CompletableFuture.completedFuture(comment);
    }


}
