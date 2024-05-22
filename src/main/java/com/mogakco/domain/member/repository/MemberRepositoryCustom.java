package com.mogakco.domain.member.repository;

public interface MemberRepositoryCustom {

    // 회원가입 시 중복 닉네임 검증
    Boolean validateDuplicationNickname(String nickname);
}
