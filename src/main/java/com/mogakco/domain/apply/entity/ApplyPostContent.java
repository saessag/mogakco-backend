package com.mogakco.domain.apply.entity;

import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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

    @Comment("모각코 모임 url 오픈카톡")
    @Column(length = 2083)
    private String projectUrlKaKaoTalk;

    @Comment("모각코 모임 url discord")
    @Column(length = 2083)
    private String projectUrlDiscord;

    @Comment("모각코 모임 url 기타")
    @Column(length = 2083)
    private String projectUrlEtc;

    @Builder
    public ApplyPostContent(String text, String projectUrlKaKaoTalk, String projectUrlDiscord, String projectUrlEtc) {
        this.text = text;
        this.projectUrlKaKaoTalk = projectUrlKaKaoTalk;
        this.projectUrlDiscord = projectUrlDiscord;
        this.projectUrlEtc = projectUrlEtc;
    }

    public void updateApplyPostContent(String text, String projectUrlKaKaoTalk, String projectUrlDiscord, String projectUrlEtc) {
        this.text = text;
        this.projectUrlKaKaoTalk = projectUrlKaKaoTalk;
        this.projectUrlDiscord = projectUrlDiscord;
        this.projectUrlEtc = projectUrlEtc;
    }


}
