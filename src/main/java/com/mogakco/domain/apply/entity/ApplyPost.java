package com.mogakco.domain.apply.entity;

import com.mogakco.domain.apply.type.SelectMethod;
import com.mogakco.domain.apply.type.Status;
import com.mogakco.domain.group.entity.PrimaryGroup;
import com.mogakco.domain.group.entity.SecondaryGroup;
import com.mogakco.domain.member.entity.Member;
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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE apply_post SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class ApplyPost extends BaseDateTimeEntity {

    @Comment("모집 글 테이블 PK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_post_id")
    private Long id;

    @Comment("모집 글 작성자")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Comment("모집 글 제목")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("모집 글 내용")
    @Column(nullable = false, length = 4096)
    private String text;

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

    @Comment("모각코 모임 url, 오픈카톡, discord, etc 링크")
    @Column(nullable = false, length = 2048)
    private String projectUrl;

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

    @Comment("대분류")
    @ManyToOne
    @JoinColumn(name = "primary_group_id", nullable = false)
    private PrimaryGroup primaryGroup;

    @Comment("소분류")
    @ManyToOne
    @JoinColumn(name = "secondary_group_id", nullable = false)
    private SecondaryGroup secondaryGroup;

    @Comment("댓글")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new CopyOnWriteArrayList<>();

    @Comment("삭제 여부")
    private boolean deleted;

    @Comment("모집글에 대한 신청 내역")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY)
    private List<ApplyRecord> applyRecords = new CopyOnWriteArrayList<>();

    @Comment("모집글에 대한 초대 내역")
    @OneToMany(mappedBy = "applyPost", fetch = FetchType.LAZY)
    private List<InvitationRecord> invitationRecords = new CopyOnWriteArrayList<>();

    @Builder
    public ApplyPost(Member member, String title, String text, LocalDateTime applyBeginDatetime, LocalDateTime applyEndDateTime, LocalDateTime projectBeginDatetime, LocalDateTime projectEndDatetime, String projectUrl, SelectMethod selectMethod, boolean visible, Status status, PrimaryGroup primaryGroup, SecondaryGroup secondaryGroup) {
        this.member = member;
        this.title = title;
        this.text = text;
        this.applyBeginDatetime = applyBeginDatetime;
        this.applyEndDateTime = applyEndDateTime;
        this.projectBeginDatetime = projectBeginDatetime;
        this.projectEndDatetime = projectEndDatetime;
        this.projectUrl = projectUrl;
        this.selectMethod = selectMethod;
        this.visible = visible;
        this.status = status;
        this.primaryGroup = primaryGroup;
        this.secondaryGroup = secondaryGroup;
    }
}
