package com.mogakco.domain.apply.type;

import com.mogakco.global.common.type.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status implements EnumType {
    COMPLETED("모집 완료"),
    PROCEEDING("모집 중"),
    WAITING("모집 대기"),
    ETC("기타");

    private final String description;

}
