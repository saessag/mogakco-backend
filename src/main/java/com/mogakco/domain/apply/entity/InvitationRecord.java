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
@SQLDelete(sql = "UPDATE invitation_record SET deleted = true WHERE invitation_record_id = ?")
@SQLRestriction("deleted = false")
public class InvitationRecord extends BaseDateTimeEntity {

    @Comment("초대 기록 id")
    @Column(name = "invitation_record_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("모집 글")
    @ManyToOne
    @JoinColumn(name = "apply_post_id", nullable = false)
    private ApplyPost applyPost;

    @Comment("동의 여부")
    @Column(nullable = false)
    private boolean agree;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public InvitationRecord(ApplyPost applyPost, boolean agree) {
        this.applyPost = applyPost;
        this.agree = agree;
    }
}
