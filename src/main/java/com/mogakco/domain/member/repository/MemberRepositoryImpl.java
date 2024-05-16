package com.mogakco.domain.member.repository;

import com.mogakco.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.mogakco.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 회원가입 시 중복 닉네임 검증
    @Override
    public Boolean validateDuplicationNickname(String nickname) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.nickname.eq(nickname))
                .fetchFirst() != null;
    }
}
