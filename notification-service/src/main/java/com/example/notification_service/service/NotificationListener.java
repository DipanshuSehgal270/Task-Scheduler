package com.example.notification_service.service;

import com.example.notification_service.dto.InvitationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @RabbitListener(queues = "invitationQueue")
    public void handleInvitation(InvitationEvent event) {
        // Logic to "send" the email
        System.out.println("==================================================");
        System.out.println("📧 EMAIL SENT");
        System.out.println("To: " + event.getUserEmail());
        System.out.println("Subject: You're invited!");
        System.out.println("Body: You have been added to Task List ID: " + event.getTaskListId());
        System.out.println("==================================================");
    }
}
