package com.lib.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@Data
@MappedSuperclass
public class BaseEntity<ID extends Serializable> {

    @EmbeddedId
    private ID id;

    @Column(length = 36, unique = true)
    private String uid;
}
