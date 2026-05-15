package com.restock.communications.application.internal.queryservices;

import com.restock.communications.domain.model.Notification;
import com.restock.communications.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationQueryService {

    private final NotificationRepository repository;

    public NotificationQueryService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<Notification> findAll() { return repository.findAll(); }
    public Optional<Notification> findById(String id) { return repository.findById(id); }
    public List<Notification> findByBusinessId(String businessId) {
        return repository.findByBusinessIdOrderByCreatedAtDesc(businessId);
    }
    public List<Notification> findUnreadByBusinessId(String businessId) {
        return repository.findByBusinessIdAndReadFalse(businessId);
    }
}
