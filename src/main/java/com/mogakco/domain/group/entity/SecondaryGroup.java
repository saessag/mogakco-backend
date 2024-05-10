package com.mogakco.domain.group.entity;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE secondary_group SET deleted = true WHERE secondary_group_id = ?")
@SQLRestriction("deleted = false")
public class SecondaryGroup extends BaseDateTimeEntity {

    @Comment("소분류 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secondary_group_id")
    private Long id;

    @Comment("소속")
    @ManyToOne
    @JoinColumn(name = "primary_group_id")
    private PrimaryGroup primaryGroup;

    @Comment("소분류 이름")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("소분류 소속 글")
    @OneToMany(mappedBy = "secondaryGroup", fetch = FetchType.LAZY)
    private List<ApplyPost> applyPosts = new CopyOnWriteArrayList<>();

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public SecondaryGroup(PrimaryGroup primaryGroup, String title) {
        this.primaryGroup = primaryGroup;
        this.title = title;
    }
}
