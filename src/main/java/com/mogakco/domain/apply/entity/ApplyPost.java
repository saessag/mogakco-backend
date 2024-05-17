package com.mogakco.domain.apply.entity;

import com.mogakco.domain.apply.type.SelectMethod;
import com.mogakco.domain.apply.type.Status;
import com.mogakco.domain.reply.entity.Reply;
import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE apply_post SET deleted = true WHERE apply_post_id = ?")
@SQLRestriction("deleted = false")
public class ApplyPost extends BaseDateTimeEntity {

    @Comment("모집 글 테이블 PK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_post_id")
    private Long id;

    @Comment("모집 글 제목")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("모집 글 내용")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "apply_post_content_id")
    private ApplyPostContent applyPostContent;

    @Comment("모집 시작 시간")
    @Column(nullable = false)
    private LocalDateTime applyBeginDatetime;

    @Comment("모집 종료 시간")
    @Column(nullable = false)
    private LocalDateTime applyEndDateTime;

    @Comment("모각코 시작 시간")
    @Column(nullable = false)
    private LocalDateTime projectBeginDatetime;

    @Comment("모각코 종료 시간")
    @Column(nullable = false)
    private LocalDateTime projectEndDatetime;

    @Comment("모집 방식")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private SelectMethod selectMethod;

    @Comment("공개 여부")
    @Column(nullable = false)
    private boolean visible;

    @Comment("모집 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Status status;

    @Comment("분류")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY)
    private List<ApplyPostCategory> applyPostCategories = new ArrayList<>();

    @Comment("댓글")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @Comment("삭제 여부")
    private boolean deleted;

    @Comment("모집글에 대한 신청 내역")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY)
    private List<ApplyRecord> applyRecords = new ArrayList<>();

    @Comment("모집글에 대한 초대 내역")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY)
    private List<InvitationRecord> invitationRecords = new ArrayList<>();

    @Builder
    public ApplyPost(String title, ApplyPostContent applyPostContent, LocalDateTime applyBeginDatetime, LocalDateTime applyEndDateTime, LocalDateTime projectBeginDatetime, LocalDateTime projectEndDatetime, SelectMethod selectMethod, boolean visible, Status status, List<ApplyPostCategory> applyPostCategories, List<Reply> replies, List<ApplyRecord> applyRecords, List<InvitationRecord> invitationRecords) {
        this.title = title;
        this.applyPostContent = applyPostContent;
        this.applyBeginDatetime = applyBeginDatetime;
        this.applyEndDateTime = applyEndDateTime;
        this.projectBeginDatetime = projectBeginDatetime;
        this.projectEndDatetime = projectEndDatetime;
        this.selectMethod = selectMethod;
        this.visible = visible;
        this.status = status;
        this.applyPostCategories = applyPostCategories;
        this.replies = replies;
        this.applyRecords = applyRecords;
        this.invitationRecords = invitationRecords;
    }
}
