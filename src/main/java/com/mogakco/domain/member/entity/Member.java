package com.mogakco.domain.member.entity;

import com.mogakco.domain.member.part.Address;
import com.mogakco.domain.member.type.MemberStatus;
import com.mogakco.domain.member.type.Role;
import com.mogakco.domain.profile.entity.Profile;
import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "createdAt", column = @Column(name = "joined_at", nullable = false, updatable = false))
public class Member extends BaseDateTimeEntity {

    @Id
    @Comment("회원 테이블 PK")
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 이름")
    @Column(name = "member_name", nullable = false)
    private String name;

    @Comment("회원 닉네임")
    @Column(length = 8, nullable = false, unique = true)
    private String nickname;

    @Comment("회원 이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("회원 비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("회원 휴대전화번호")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Comment("회원 생일")
    @Column(nullable = false, updatable = false)
    private LocalDate birthday;

    @Embedded
    @Comment("회원 주소")
    private Address address;

    @Comment("회원 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private MemberStatus memberStatus;

    @Comment("회원 권한")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Role role;

    @Comment("회원 프로필")
    @JoinColumn(name = "profile_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @Builder
    public Member(String name, String nickname, String email, String password, String phoneNumber, LocalDate birthday) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.memberStatus = MemberStatus.ACTIVE;
        this.role = Role.GUEST;
        this.profile = Profile.builder().build();
    }
}
