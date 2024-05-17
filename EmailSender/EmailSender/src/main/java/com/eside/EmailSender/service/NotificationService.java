package com.eside.EmailSender.service;

import com.eside.EmailSender.model.Notification;
import com.eside.EmailSender.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Sinks.Many<ServerSentEvent<Notification>> sink;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void generateAndSendNotification(Integer notificationId, String message) {
        Notification notification = new Notification(null, notificationId, message);
        notificationRepository.save(notification); // Save notification to the database

        ServerSentEvent<Notification> event = ServerSentEvent.builder(notification).build();
        sink.tryEmitNext(event);
    }

    public Flux<ServerSentEvent<Notification>> subscribe(Integer id) {
        Flux<ServerSentEvent<Notification>> oldNotifications = Flux.fromIterable(notificationRepository.findByNotificationId(id))
                .map(notification -> ServerSentEvent.builder(notification).build());

        Flux<ServerSentEvent<Notification>> newNotifications = keepAlive(Duration.ofSeconds(3), sink.asFlux()
                .filter(notification -> notification.data() == null || notification.data().getNotificationId().equals(id)), id);

        return Flux.concat(oldNotifications, newNotifications);
    }

    private <T> Flux<ServerSentEvent<T>> keepAlive(Duration duration, Flux<ServerSentEvent<T>> data, Integer id) {
        Flux<ServerSentEvent<T>> heartBeat = Flux.interval(duration)
                .map(e -> ServerSentEvent.<T>builder()
                        .comment("keep alive for: " + id)
                        .build())
                .doFinally(signalType -> System.out.println("Heartbeat closed for id: " + id));
        return Flux.merge(heartBeat, data);
    }

    public List<Notification> getNotificationsBySubscriberId(Integer id) {
        return notificationRepository.findByNotificationId(id);
    }
}
