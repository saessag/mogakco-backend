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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE primary_group SET deleted = true WHERE primary_group_id = ?")
@SQLRestriction("deleted = false")
public class PrimaryGroup extends BaseDateTimeEntity {

    @Comment("대분류 id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_group_id")
    private Integer id;

    @Comment("내부 소분류")
    @OneToMany(mappedBy = "primaryGroup", fetch = FetchType.LAZY)
    private Set<SecondaryGroup> secondaryGroups = new HashSet<>();

    @Comment("대분류 이름")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("대분류 소속 글")
    @OneToMany(mappedBy = "primaryGroup", fetch = FetchType.LAZY)
    private List<ApplyPost> applyPosts = new CopyOnWriteArrayList<>();

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public PrimaryGroup(Set<SecondaryGroup> secondaryGroups, String title) {
        this.secondaryGroups = secondaryGroups;
        this.title = title;
    }
}
