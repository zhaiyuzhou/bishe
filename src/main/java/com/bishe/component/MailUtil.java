package com.bishe.component;

import com.bishe.model.Mail;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailUtil {
    @Value("${spring.mail.username}")
    private String sender; //邮件发送者

    @Resource
    private JavaMailSender javaMailSender;


    /**
     * 发送文本邮件
     *
     * @param mail
     */
    public String sendSimpleMail(Mail mail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender); //邮件发送者
            mailMessage.setTo(mail.getRecipient()); // 邮件发给的人
            mailMessage.setSubject(mail.getSubject());  // 邮件主题
            mailMessage.setText(mail.getContent());  // 邮件内容

            javaMailSender.send(mailMessage);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "邮件发送失败" + e.getMessage();
        }
    }
}

