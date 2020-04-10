package pl.jee.klos.utils;

import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.service2.PlanningMemberService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RegistrationListener implements
  ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private PlanningMemberService service;
  
    @Autowired
    private MessageSource messages;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        PlanningMember planningMember = event.getPlanningMember();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(planningMember, token);
         
        String recipientAddress = "spring.app.user.klos@gmail.com";
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String message = "Hello! Your activation link is: ";
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}