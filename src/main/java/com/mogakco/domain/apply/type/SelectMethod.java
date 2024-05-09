package com.mogakco.domain.apply.type;

import com.mogakco.global.common.type.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelectMethod implements EnumType {
    RANDOM("랜덤 전략"),
    FIRST_COME("선착순 전략"),
    PICK_UP("개설자 지정 전략");

    private final String description;
}
