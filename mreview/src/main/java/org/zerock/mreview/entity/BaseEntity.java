package org.zerock.mreview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 시간을 따로 떼서 관리 자동 업데이트를 위해서 base Entity를 생성 하여 상속해준다
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate", updatable = false)
    private LocalDateTime modDate;
}