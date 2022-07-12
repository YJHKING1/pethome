package org.yjhking.pethome.org;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yjhking.pethome.PethomeApplication;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author YJH
 */
@SpringBootTest(classes = PethomeApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMail {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Test
    public void send1() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("910480155@qq.com");
        //邮件主题
        mailMessage.setSubject("新型冠状病毒防护指南");
        //邮件内容：普通文件无法解析html标签
        mailMessage.setText("好好在家待着.....");
        //收件人
        mailMessage.setTo("yjhking@163.com");
        //发送邮件
        javaMailSender.send(mailMessage);
    }
    
    @Test
    public void send2() throws MessagingException {
        //创建复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom("910480155@qq.com");
        helper.setSubject("店铺激活邮件");
        helper.setText("<h1>你的店铺已经注册!!!</h1><img src='http://123.207.27.208/group1/M00/00/76/CgAIC2LNCqOAPD28AABlZ--sXNY062.jpg'>", true);
        //添加附件
        helper.addAttachment("源码时代2022年04月02日Java精英班", new File("C:\\Users\\yjhki\\OneDrive\\Documents\\YJH\\Java\\20220402\\源码时代2022年04月02日Java精英班-v8.1.xls"));
        //收件人
        helper.setTo("yjhking@163.com");
        javaMailSender.send(mimeMessage);
    }
}