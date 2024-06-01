package com.mogakco.domain.member.repository;

import com.mogakco.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
