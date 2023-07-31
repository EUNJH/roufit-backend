package com.roufit.backend.global.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long modifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void remove() {
        this.status = Status.REMOVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }

    public void hide() {
        this.status = Status.PRIVATE;
    }
}
