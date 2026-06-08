package com.uitopic.restock.platform.resources.application.internal.commandservices;

import com.uitopic.restock.platform.resources.domain.exception.CustomSupplyAlreadyExistsException;
import com.uitopic.restock.platform.resources.domain.exception.CustomSupplyNotFoundException;
import com.uitopic.restock.platform.resources.domain.exception.SupplyNotFoundException;
import com.uitopic.restock.platform.resources.domain.model.aggregates.CustomSupply;
import com.uitopic.restock.platform.resources.domain.model.commands.CreateCustomSupplyCommand;
import com.uitopic.restock.platform.resources.domain.model.commands.DeleteCustomSupplyCommand;
import com.uitopic.restock.platform.resources.domain.model.commands.UpdateCustomSupplyCommand;
import com.uitopic.restock.platform.resources.domain.model.entities.Supply;
import com.uitopic.restock.platform.resources.domain.model.events.CustomSupplyDeletedEvent;
import com.uitopic.restock.platform.resources.domain.repositories.CustomSupplyRepository;
import com.uitopic.restock.platform.resources.domain.repositories.SupplyRepository;
import com.uitopic.restock.platform.resources.domain.services.CustomSupplyCommandService;
import com.uitopic.restock.platform.resources.infrastructure.persistence.mongodb.repositories.CustomSupplyPersistenceRepository;
import com.uitopic.restock.platform.resources.infrastructure.persistence.mongodb.repositories.SupplyPersistenceRepository;
import com.uitopic.restock.platform.shared.application.internal.outboundservices.filestorage.ImageService;
import com.uitopic.restock.platform.shared.domain.exceptions.ImageUploadException;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.AccountId;
import com.uitopic.restock.platform.shared.domain.model.valueobjects.ImageURL;
import com.uitopic.restock.platform.shared.infrastructure.eventpublisher.spring.SpringDomainEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Application service for CustomSupply write operations.
 */
@Slf4j
@Service
public class CustomSupplyCommandServiceImpl implements CustomSupplyCommandService {

    private final CustomSupplyRepository customSupplyRepository;
    private final SupplyRepository supplyRepository;
    private final ImageService imageService;
    private final SpringDomainEventPublisher eventPublisher;

    public CustomSupplyCommandServiceImpl(
            CustomSupplyRepository customSupplyRepository,
            SupplyRepository supplyRepository,
            ImageService imageService,
            SpringDomainEventPublisher eventPublisher
    ) {
        this.customSupplyRepository = customSupplyRepository;
        this.supplyRepository = supplyRepository;
        this.imageService = imageService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "custom-supplies-all", allEntries = true),
            @CacheEvict(value = "custom-supplies-by-account", key = "#command.accountId()")
    })
    public CustomSupply handle(CreateCustomSupplyCommand command) {
        log.info("Creating custom supply: name='{}', accountId='{}'", command.name(), command.accountId());
        AccountId accountId = new AccountId(command.accountId());

        if (customSupplyRepository.existsByAccountIdAndName(accountId, command.name())) {
            throw new CustomSupplyAlreadyExistsException(
                    "Custom supply with name '" + command.name() + "' already exists for this account"
            );
        }

        if (customSupplyRepository.existsByAccountIdAndSupplyId(accountId, command.supplyId())) {
            throw new CustomSupplyAlreadyExistsException(
                    "Custom supply for supplyId '" + command.supplyId() + "' already exists for this account"
            );
        }

        Supply supply = findSupplyOrThrow(command.supplyId());
        ImageURL pictureUrl = uploadImageIfPresent(
                command.image(),
                command.photoFileName()
        );

        CustomSupply customSupply = new CustomSupply(command, supply, pictureUrl);
        CustomSupply savedCustomSupply = customSupplyRepository.save(customSupply);
        log.info("Custom supply created successfully: id='{}'", savedCustomSupply.getId());
        return savedCustomSupply;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "custom-supplies-all", allEntries = true),
            @CacheEvict(value = "custom-supplies-by-account", allEntries = true),
            @CacheEvict(value = "custom-supply-by-id", key = "#command.customSupplyId()")
    })
    public Optional<CustomSupply> handle(UpdateCustomSupplyCommand command) {
        log.info("Updating custom supply: id='{}'", command.customSupplyId());
        return customSupplyRepository.findById(command.customSupplyId()).map(existing -> {
            if (command.name() != null && !command.name().isBlank()) {
                boolean duplicatedName = customSupplyRepository.existsByAccountIdAndNameAndIdNot(
                        existing.getAccountId(),
                        command.name(),
                        command.customSupplyId()
                );

                if (duplicatedName) {
                    throw new CustomSupplyAlreadyExistsException(
                            "Custom supply with name '" + command.name() + "' already exists for this account"
                    );
                }
            }

            String oldPublicId = existing.getPictureUrl() != null
                    ? existing.getPictureUrl().publicId()
                    : null;
            ImageURL pictureUrl = existing.getPictureUrl();
            if (command.hasNewPhoto()) {
                pictureUrl = uploadImageIfPresent(
                        command.image(),
                        command.photoFileName()
                );
            }

            existing.update(command, pictureUrl);
            CustomSupply updatedCustomSupply = customSupplyRepository.save(existing);
            if (command.hasNewPhoto() && oldPublicId != null) {
                deleteOldImage(oldPublicId);
            }

            log.info("Custom supply updated successfully: id='{}'", updatedCustomSupply.getId());
            return updatedCustomSupply;
        });
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "custom-supplies-all", allEntries = true),
            @CacheEvict(value = "custom-supplies-by-account", allEntries = true),
            @CacheEvict(value = "custom-supply-by-id", key = "#command.customSupplyId()")
    })
    public void handle(DeleteCustomSupplyCommand command) {
        log.info("Deleting custom supply: id='{}'", command.customSupplyId());
        CustomSupply customSupply = customSupplyRepository.findById(command.customSupplyId())
                .orElseThrow(() -> new CustomSupplyNotFoundException("Custom supply not found: " + command.customSupplyId()));

        customSupplyRepository.deleteById(command.customSupplyId());
        eventPublisher.publish(new CustomSupplyDeletedEvent(
                customSupply.getId(),
                customSupply.getAccountId().getAccountId()
        ));
        log.info("Custom supply deleted successfully: id='{}'", command.customSupplyId());
    }

    private Supply findSupplyOrThrow(String supplyId) {
        return supplyRepository.findById(supplyId)
                .orElseThrow(() -> new SupplyNotFoundException("Supply not found: " + supplyId));
    }

    private ImageURL uploadImageIfPresent(byte[] image, String photoFileName) {
        if (image == null || image.length == 0) {
            return null;
        }

        if (photoFileName == null || photoFileName.isBlank()) {
            throw new ImageUploadException("Photo file name must be provided when uploading an image");
        }

        try {
            var uploadResult = imageService.upload(image, photoFileName);
            return new ImageURL(
                    uploadResult.get("url"),
                    uploadResult.get("publicId")
            );
        } catch (Exception e) {
            throw new ImageUploadException("Failed to upload image: " + e.getMessage());
        }
    }

    private void deleteOldImage(String publicId) {
        try {
            imageService.delete(publicId);
            log.info("Previous custom supply image deleted: publicId='{}'", publicId);
        } catch (Exception e) {
            log.warn(
                    "Could not delete previous custom supply image: publicId='{}', error='{}'",
                    publicId,
                    e.getMessage()
            );
        }
    }
}