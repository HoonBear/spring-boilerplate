package com.example.boilerplate.global.model;

import com.example.boilerplate.global.enumeration.Yn;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseTime {

    @CreatedBy
    @Comment("생성자")
    private String createdBy;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Comment("생성일")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Comment("수정자")
    private String modifiedBy;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Comment("수정일")
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Comment("삭제여부(미삭제:N, 삭제:Y)")
    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private Yn deleteYn;

    public void updateYn(Yn yn) {
        this.deleteYn = yn;
    }
}
