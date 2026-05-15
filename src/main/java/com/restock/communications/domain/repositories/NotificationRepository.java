package com.restock.communications.domain.repositories;

import com.restock.communications.domain.model.Notification;

import java.util.List;
import java.util.Optional;

/** Port — domain boundary for notification persistence. */
public interface NotificationRepository {
    List<Notification> findAll();
    Optional<Notification> findById(String id);
    List<Notification> findByBusinessIdOrderByCreatedAtDesc(String businessId);
    List<Notification> findByBusinessIdAndReadFalse(String businessId);
    Notification save(Notification notification);
    void deleteById(String id);
}
