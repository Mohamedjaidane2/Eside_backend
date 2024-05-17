package com.eside.EmailSender.controller;

import com.eside.EmailSender.model.Notification;
import com.eside.EmailSender.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/subscribe/{id}")
    public Flux<ServerSentEvent<Notification>> subscribe(@PathVariable Integer id) {
        return notificationService.subscribe(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getNotificationsBySubscriberId(@PathVariable Integer id) {
        List<Notification> notifications = notificationService.getNotificationsBySubscriberId(id);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/generate")
    public void generateNotification(@RequestParam Integer id, @RequestParam String message) {
        notificationService.generateAndSendNotification(id, message);
    }
}
