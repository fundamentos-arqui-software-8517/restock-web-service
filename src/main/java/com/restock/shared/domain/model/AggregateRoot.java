package com.restock.shared.domain.model;

import org.springframework.data.annotation.Id;

public abstract class AggregateRoot {
    @Id
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
