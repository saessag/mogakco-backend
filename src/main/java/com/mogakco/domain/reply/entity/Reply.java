package com.mogakco.domain.reply.entity;

import com.mogakco.domain.apply.entity.ApplyPost;
import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE reply SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Reply extends BaseDateTimeEntity {

    @Comment("댓글 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Comment("원글")
    @ManyToOne
    @JoinColumn(name = "apply_post_id", nullable = false)
    private ApplyPost applyPost;

    @Comment("댓글 내용")
    @Column(nullable = false, length = 2048)
    private String text;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Comment("부모 댓글")
    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private Reply parentReply;

    @Comment("대댓글(답글, 자식 답글)")
    @OneToMany(mappedBy = "parentReply", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> nestedReply = new CopyOnWriteArrayList<>();

    public void setParentReply(Reply reply) {
        this.parentReply = reply;
        reply.getNestedReply().add(this);
    }

    @Builder
    public Reply(ApplyPost applyPost, String text, Reply parentReply) {
        this.applyPost = applyPost;
        this.text = text;
        this.parentReply = parentReply;
    }
}
