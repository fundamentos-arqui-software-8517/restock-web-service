package com.restock.communications.interfaces.rest.transform;

import com.restock.communications.domain.model.Notification;
import com.restock.communications.interfaces.rest.resources.CreateNotificationResource;
import com.restock.communications.interfaces.rest.resources.NotificationResource;
import org.springframework.stereotype.Component;

@Component
public class NotificationAssembler {

    public NotificationResource toResource(Notification n) {
        return new NotificationResource(n.getId(), n.getBusinessId(), n.getTitle(),
            n.getMessage(), n.getType(), n.isRead(), n.getCreatedAt(), n.getRelatedEntityId());
    }

    public Notification toEntity(CreateNotificationResource resource) {
        return Notification.builder()
            .businessId(resource.businessId()).title(resource.title())
            .message(resource.message()).type(resource.type() != null ? resource.type() : "INFO")
            .read(false).relatedEntityId(resource.relatedEntityId()).build();
    }
}
