package com.restock.communications.infrastructure.persistence;

import com.restock.communications.domain.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoNotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByBusinessIdOrderByCreatedAtDesc(String businessId);
    List<Notification> findByBusinessIdAndReadFalse(String businessId);
}
