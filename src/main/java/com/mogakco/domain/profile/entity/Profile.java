package com.mogakco.domain.profile.entity;

import com.mogakco.domain.profile.part.Address;
import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseDateTimeEntity {

    @Id
    @Comment("프로필 테이블 PK")
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Comment("회원 프로필 이미지")
    @Column(columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    private String profileImage; // 개발 단계에서는 이미지 바이트코드로 저장하고 배포단계에서는 s3를 이용하여 변경 필요

    @Comment("프로필의 간단한 자기 소개")
    private String bio;

    @Comment("프로필에 등록된 내 직업")
    private String occupation;

    @ElementCollection
    @CollectionTable(name = "profile_urls", joinColumns = @JoinColumn(name = "profile_id"))
    private Set<String> urls = new HashSet<>();

    @Embedded
    @Comment("회원 주소")
    private Address address;

    @Builder
    public Profile(String profileImage, String bio, String occupation, Set<String> urls, Address address) {
        this.profileImage = profileImage;
        this.bio = bio;
        this.occupation = occupation;
        this.urls = urls;
        this.address = address;
    }
}
