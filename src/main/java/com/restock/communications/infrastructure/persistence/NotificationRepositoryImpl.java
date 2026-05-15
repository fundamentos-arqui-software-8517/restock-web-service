package com.restock.communications.infrastructure.persistence;

import com.restock.communications.domain.model.Notification;
import com.restock.communications.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final MongoNotificationRepository mongo;

    public NotificationRepositoryImpl(MongoNotificationRepository mongo) {
        this.mongo = mongo;
    }

    @Override public List<Notification> findAll() { return mongo.findAll(); }
    @Override public Optional<Notification> findById(String id) { return mongo.findById(id); }
    @Override public List<Notification> findByBusinessIdOrderByCreatedAtDesc(String businessId) { return mongo.findByBusinessIdOrderByCreatedAtDesc(businessId); }
    @Override public List<Notification> findByBusinessIdAndReadFalse(String businessId) { return mongo.findByBusinessIdAndReadFalse(businessId); }
    @Override public Notification save(Notification notification) { return mongo.save(notification); }
    @Override public void deleteById(String id) { mongo.deleteById(id); }
}
