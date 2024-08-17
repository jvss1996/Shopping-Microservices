package com.shekhawat.notification_service.service;

import com.shekhawat.notification_service.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received notification for order - {}", orderPlacedEvent);
        // send email to user
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("orders@shoppingapp.com");
            messageHelper.setTo("test@gmail.com");
            messageHelper.setSubject(String.format("Order Confirmation - %s", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText("""
                    Hi
                                            
                    Your order with order number %s has been successfully placed.
                                            
                    Best regards
                    Shopping App
                    """, orderPlacedEvent.getOrderNumber());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order notification email sent.");
        } catch (MailException e) {
            log.error("Error while sending order notification email", e);
            throw new RuntimeException("Error while sending order notification email", e);
        }
    }
}
