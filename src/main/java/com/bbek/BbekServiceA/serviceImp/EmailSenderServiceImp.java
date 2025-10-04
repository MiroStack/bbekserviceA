package com.bbek.BbekServiceA.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImp {
    @Autowired
    JavaMailSender javaMailSender;

    public String sendEmailForBaptism(String toEmail, String status, String baptism_date, String firstname, String lastname) {
        String subject = "BBEK CHURCH BAPTISM STATUS";
        String body = "Good Day " + firstname + " " + lastname + ", \n" +
                "Your Baptism Status is updated as " + status + "\n" +
                "Baptism Date : " + baptism_date;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("bbek@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body); // <- don’t forget to set the body!

            javaMailSender.send(message);
            return "Email sent successfully to " + toEmail;
        } catch (Exception e) {
            e.printStackTrace(); // log error for debugging
            return "Failed to send email: " + e.getMessage();
        }
    }

    public String sendEmailMessage(String toEmail, String messageBody, String subject) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mirosarte.work@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(messageBody);
            javaMailSender.send(message);
            return "Email sent successfully to " + toEmail;
        } catch (Exception e) {
            e.printStackTrace(); // log error for debugging
            return "Failed to send email: " + e.getMessage();
        }
    }

}
