package com.bishe.controller;

import com.alibaba.fastjson.JSON;
import com.bishe.component.MailUtil;
import com.bishe.dataobject.ImgDO;
import com.bishe.dataobject.UserDO;
import com.bishe.model.Attention;
import com.bishe.model.Mail;
import com.bishe.model.User;
import com.bishe.result.Result;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Controller
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MailUtil mailUtil;

    @PostMapping("/sign")
    @ResponseBody
    public Result<User> sign(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<User> result = new Result<>();

        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = JSON.parseObject(body, HashMap.class);

        User user = new User();
        user.setEmail(map.get("Email").toString());
        user.setGender(map.get("gender").toString());
        user.setUserName(map.get("username").toString());
        user.setNickName(map.get("nickname").toString());
        user.setPhone(map.get("phone").toString());
        user.setPassword(map.get("password").toString());

        UserDO userDO = new UserDO(user);
        String message = userService.sign(userDO).get();

        if (!("success".equals(message))) {
            result.error(message);
            return result;
        }

        result.success(user);
        result.setMessage("注册成功");

        return result;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@RequestParam("username") String userName,
                              @RequestParam("password") String password,
                              @RequestParam("remember") Boolean remember,
                              @CookieValue(value = "JSESSIONID", required = false) String jSessionId,
                              HttpServletRequest request, HttpServletResponse response
    ) throws ExecutionException, InterruptedException {
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(jSessionId);

        if (user != null) {
            result.success(user);
            return result;
        }

        String message = userService.login(userName, password).get();

        if (!"success".equals(message)) {
            result.error(message);
            return result;
        }

        UserDO userDO = userService.findByName(userName).get();
        user = userDO.toModel();

        if (remember == null) {
            message = "remember为空";
            result.setMessage(message);
        } else {
            session.setAttribute(userName, user);
            Cookie cookie = new Cookie("isLogin", "true");
            Cookie cookie1 = new Cookie("username", userName);
            cookie.setPath("/");
            cookie1.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookie1);
        }

        result.success(user);
        return result;
    }

    @GetMapping("/user")
    @ResponseBody
    public Result<User> user(HttpServletRequest request,
                             @CookieValue(value = "username", required = false) String username) {
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        result.success(user);
        return result;
    }

    @PostMapping("/avatarChange")
    @ResponseBody
    public Result<User> avatar(@RequestBody String body,
                               @CookieValue(value = "username", required = false) String username,
                               HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<User> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        String avatarName = JSON.parseObject(body, HashMap.class).get("avatar").toString();

        ImgDO imgDO = (ImgDO) redisTemplate.opsForValue().get(avatarName);
        if (imgDO == null) {
            result.error("图片上传失败");
            return result;
        }

        user.setAvatar(imgDO.getImgPath());
        redisTemplate.delete(avatarName);
        session.setAttribute("user", user);


        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setAvatar(user.getAvatar());

        // 更新
        String message = userService.update(userDO).get();

        if ("success".equals(message)) {
            result.success(user);
            return result;
        }

        result.error("更新失败");
        return result;
    }

    @PostMapping("/passwordChange")
    @ResponseBody
    public Result<User> passwordChange(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<User> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }


        String oldPassword = JSON.parseObject(body, HashMap.class).get("oldPassword").toString();
        String newPassword = JSON.parseObject(body, HashMap.class).get("newPassword").toString();

        if (!oldPassword.equals(user.getPassword())) {
            result.error("原密码错误");
            return result;
        }
        user.setPassword(newPassword);
        UserDO userDO = new UserDO(user);

        // 更新
        String message = userService.update(userDO).get();

        if ("success".equals(message)) {
            result.success(user);
            return result;
        }

        result.error("更新失败");
        return result;
    }

    @PostMapping("/authCode")
    @ResponseBody
    public Result<String> authCode(@RequestBody String body
    ) {
        Result<String> result = new Result<>();

        String oldEmail = JSON.parseObject(body, HashMap.class).get("Email").toString();

        Random random = new Random();
        String authCode = String.valueOf(random.nextInt(10000) + 1);

        // 发送验证码
        Mail mail = new Mail();
        mail.setRecipient(oldEmail);
        mail.setSubject("发送验证码");
        mail.setContent("亲爱的用户：您好！\n" +
                "\n" + "    您收到这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了修改邮箱。假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。\n" +
                "\n" +
                "   请使用以下验证码完成后续修改邮箱流程\n" + "\n  " +
                authCode + "\n\n" + "  注意：请您收到邮件的十分钟内（" +
                DateFormatUtils.format(new Date().getTime() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss") +
                "）前使用，否则验证码将会失效。"
        );

        String message = mailUtil.sendSimpleMail(mail);
        if (!"success".equals(message)) {
            result.error(message);
            return result;
        }

        result.success(authCode);
        return result;
    }

    @PostMapping("/emailChange")
    @ResponseBody
    public Result<User> emailChange(@RequestBody String body,
                                    @CookieValue(value = "username", required = false) String username,
                                    HttpServletRequest request) throws ExecutionException, InterruptedException {

        Result<User> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        // 获取新邮箱
        String newEmail = JSON.parseObject(body, HashMap.class).get("newEmail").toString();
        if (StringUtils.isEmpty(newEmail)) {
            result.error("新邮箱为空");
            return result;
        }

        user.setEmail(newEmail);
        UserDO userDO = new UserDO(user);
        session.setAttribute("user", user);

        // 更新
        String message = userService.update(userDO).get();

        if ("success".equals(message)) {
            result.success(user);
            return result;
        }

        result.error(message);
        return result;
    }

    @GetMapping("/loginout")
    @ResponseBody
    public Result<Boolean> loginOut(@CookieValue(value = "username", required = false) String username,
                                    HttpServletRequest request, HttpServletResponse response) {
        Result<Boolean> result = new Result<>();
        HttpSession session = request.getSession();
        session.removeAttribute(username);
        session.invalidate();
        Cookie cookie = new Cookie("username", "");
        Cookie cookie2 = new Cookie("isLogin", "false");
        cookie.setPath("/");
        cookie2.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie2);
        return result;
    }

    @PostMapping("/nickNameChange")
    @ResponseBody
    public Result<String> nickNameChange(@RequestBody String body,
                                         @CookieValue(value = "username", required = false) String username,
                                         HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<String> result = new Result<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        String nickName = JSON.parseObject(body, HashMap.class).get("nickName").toString();
        if (StringUtils.isBlank(nickName)) {
            result.error("昵称为空");
            return result;
        }
        user.setNickName(nickName);
        UserDO userDO = new UserDO(user);
        session.setAttribute(username, user);
        String message = userService.update(userDO).get();
        if ("success".equals(message)) {
            result.success(nickName);
            return result;
        }
        result.error(message);
        return result;
    }


    @PostMapping("/findPassword")
    @ResponseBody
    public Result<String> findPassword(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<String> result = new Result<>();

        String email = JSON.parseObject(body, HashMap.class).get("Email").toString();
        String password = userService.findByEmail(email).get().getPassword();

        // 发送验证码
        Mail mail = new Mail();
        mail.setRecipient(email);
        mail.setSubject("修改邮箱");
        mail.setContent("您的账户密码是" + password);

        String message = mailUtil.sendSimpleMail(mail);

        if (!"success".equals(message)) {
            result.error(message);
            return result;
        }
        result.success(email);
        return result;
    }

    @GetMapping("/getUserList")
    @ResponseBody
    public Result<List<User>> getUserList() throws ExecutionException, InterruptedException {
        Result<List<User>> result = new Result<>();
        result.success(userService.findLimit(0).get());
        return result;
    }

    @PostMapping("/attention")
    @ResponseBody
    public Result<Boolean> attention(@RequestBody String body,
                                     @CookieValue(value = "username", required = false) String username,
                                     HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Boolean> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        Long userId = user.getId();
        String otherId = String.valueOf(JSON.parseObject(body, HashMap.class).get("userId"));

        if (StringUtils.isEmpty(otherId) || "null".equals(otherId)) {
            result.error("传入参数为空");
            return result;
        }

        Attention attention = new Attention(userId, Long.valueOf(otherId));
        String message = userService.attention(attention).get();

        if ("success".equals(message) && "success".equals(userService.addLikeNum(Long.valueOf(otherId)).get())) {
            result.success(true);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/calAttention")
    @ResponseBody
    public Result<Boolean> calAttention(@RequestBody String body,
                                        @CookieValue(value = "username", required = false) String username,
                                        HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Boolean> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }

        Long userId = user.getId();
        String otherId = String.valueOf(JSON.parseObject(body, HashMap.class).get("userId"));
        if (StringUtils.isEmpty(otherId) || "null".equals(otherId)) {
            result.error("传入参数为空");
            return result;
        }

        Attention attention = new Attention(userId, Long.valueOf(otherId));
        String message = userService.calAttention(attention).get();
        if ("success".equals(message) && "success".equals(userService.decLikeNum(Long.valueOf(otherId)).get())) {
            result.success(true);
            return result;
        }

        result.error(message);
        return result;
    }

    @PostMapping("/ifAttention")
    @ResponseBody
    public Result<Boolean> ifAttention(@RequestBody String body,
                                       @CookieValue(value = "username", required = false) String username,
                                       HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<Boolean> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.success(false);
            return result;
        }
        Long userId = user.getId();

        String otherId = String.valueOf(JSON.parseObject(body, HashMap.class).get("userId"));

        if (!StringUtils.isEmpty(otherId) && !"null".equals(otherId)) {
            Attention attention = new Attention(userId, Long.valueOf(otherId));
            result.success(userService.ifAttention(attention).get());
            return result;
        }

        result.error("失败");
        return result;
    }

    @PostMapping("/ifAttentions")
    @ResponseBody
    public Result<List<Boolean>> ifAttentions(@RequestBody String body,
                                              @CookieValue(value = "username", required = false) String username,
                                              HttpServletRequest request) {
        Result<List<Boolean>> result = new Result<>();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.success(null);
            return result;
        }
        Long userId = user.getId();

        List<Long> otherIds = JSON.parseArray(JSON.parseObject(body, HashMap.class).get("userIds").toString(), Long.class);
        Map<Long, Boolean> map = new HashMap<>();
        List<Boolean> ifattentions = new ArrayList<>();

        if (!otherIds.isEmpty()) {
            otherIds.forEach(id -> {
                if (map.containsKey(id)) {
                    ifattentions.add(map.get(id));
                } else {
                    Attention attention = new Attention(userId, id);
                    Boolean ifa = false;
                    try {
                        ifa = userService.ifAttention(attention).get();
                        map.put(id, ifa);
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    ifattentions.add(ifa);
                }
            });

            result.success(ifattentions);
            return result;
        }

        result.error("失败");
        return result;
    }

    @GetMapping("/getFriend")
    @ResponseBody
    public Result<List<User>> getFriend(@CookieValue(value = "username", required = false) String username,
                                        HttpServletRequest request) throws ExecutionException, InterruptedException {
        Result<List<User>> result = new Result<>();

        // 获取当前登录的用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(username);
        if (user == null) {
            result.error("用户未登录");
            return result;
        }
        Long userId = user.getId();
        List<User> friends = userService.findFriend(userId).get();
        if (friends != null) {
            result.success(friends);
            return result;
        }
        result.error("请求失败");
        return result;
    }

    @PostMapping("/getUser")
    @ResponseBody
    public Result<User> getUser(@RequestBody String body) throws ExecutionException, InterruptedException {
        Result<User> result = new Result<>();

        String id = JSON.parseObject(body, HashMap.class).get("userId").toString();
        if (StringUtils.isEmpty(id) || "null".equals(id)) {
            result.error("id传入失败");
            return result;
        }
        User user = userService.findById(Long.valueOf(id)).get().toModel();
        result.success(user);
        return result;
    }

}
