package com.finalproject.breeding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


@Service
@EnableAsync
@RequiredArgsConstructor
public class VerificationEmailSenderService {
    private final JavaMailSender javaMailSender;
    @Async
    public void send(String email, String authToken){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("anyZoo 회원가입 이메일 인증");

//        String msg="";
//        msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"![](../../../../../resources/logo/anyZoo.jpg)\" alt=\"\" loading=\"lazy\">";
//        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
//        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 링크를 누르면 자동으로 인증되고 홈페이지로 돌아갑니다.</p>";
//        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
//        msg += "</td></tr></tbody></table></div>";
//        msg += "<a href=\"무\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">anyZoo Technologies, Inc</a>";
//
//        //simpleMailMessage.setText("http://localhost:8090/confirm-email?email="+email+"&authToken="+authToken);
//        simpleMailMessage.setText(msg);
        simpleMailMessage.setText("http://localhost:8090/user/confirm/emailVerification?email="+email+"&authToken="+authToken);


        javaMailSender.send(simpleMailMessage);
    }

//    public void createMessage(String email, String authToken)throws Exception{
//        MimeMessage message = javaMailSender.createMimeMessage();
//            message.addRecipients(Message.RecipientType.TO, email);
//            message.setSubject("anyZoo 회원가입 이메일 인증링크가 도착했습니다");
//
//            String msgg="";
//            msgg+= "<div style='margin:100px;'>";
//            msgg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"![](../../../../../resources/logo/anyZoo.jpg)\" alt=\"\" loading=\"lazy\">";
//            msgg+= "<h1> 안녕하세요 anyZoo입니다!!! </h1>";
//            msgg+= "<br>";
//            msgg+= "<p>아래 링크를 누르면 이메일 인증이 완료되고, 회원가입 창으로 돌아갑니다<p>";
//            msgg+= "<br>";
//            msgg+= "<p>감사합니다!<p>";
//            msgg+= "<br>";
//            msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
//            msgg+= "<div style='font-size:130%'>";
//            msgg+= "</div>";
//            message.setText(msgg, "utf-8", "html");
//            message.setText("http://localhost:8090/confirm-email?email="+email+"&authToken="+authToken);
//
//        javaMailSender.send(message);
//    }
}
