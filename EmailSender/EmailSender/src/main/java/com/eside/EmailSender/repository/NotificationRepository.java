package com.eside.EmailSender.repository;

import com.eside.EmailSender.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
        List<Notification> findByNotificationId(Integer notificationId);
        }