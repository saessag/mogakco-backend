package com.mogakco.domain.member.type;

import com.mogakco.global.common.type.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumType {
    GUEST("게스트"),
    MEMBER("일반 회원"),
    ADMIN("관리자");

    private final String description;
}
