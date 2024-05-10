package com.mogakco.domain.apply.entity;

import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE apply_record SET deleted = true WHERE apply_record_id = ?")
@SQLRestriction("deleted = false")
public class ApplyRecord extends BaseDateTimeEntity {

    @Comment("신청 기록 id")
    @Column(name = "apply_record_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("모집 글")
    @ManyToOne
    @JoinColumn(name = "apply_post_id", nullable = false)
    private ApplyPost applyPost;

    @Comment("취소 여부")
    @Column(nullable = false)
    private boolean cancel;

    @Comment("선정 여부")
    @Column(nullable = false)
    private boolean selected;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public ApplyRecord(ApplyPost applyPost, boolean cancel, boolean selected) {
        this.applyPost = applyPost;
        this.cancel = cancel;
        this.selected = selected;
    }
}
