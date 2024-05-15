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
public class ApplyPostContent extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_post_content_id")
    private Long id;

    @Lob
    @Comment("모집 글 내용")
    @Column(nullable = false)
    private String text;

    @Comment("모각코 모임 url, 오픈카톡, discord, etc 링크 1")
    @Column(nullable = false)
    private String projectUrl1;

    @Comment("모각코 모임 url, 오픈카톡, discord, etc 링크 2")
    @Column(nullable = false)
    private String projectUrl2;

    @Comment("모각코 모임 url, 오픈카톡, discord, etc 링크 3")
    @Column(nullable = false)
    private String projectUrl3;

    @Builder
    public ApplyPostContent(String text, String projectUrl1, String projectUrl2, String projectUrl3) {
        this.text = text;
        this.projectUrl1 = projectUrl1;
        this.projectUrl2 = projectUrl2;
        this.projectUrl3 = projectUrl3;
    }
}
