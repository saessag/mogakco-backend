package com.mogakco.domain.member.repository;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.model.response.MemberFindEmailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmail(String email);

    @Query("SELECT new com.mogakco.domain.member.model.response.MemberFindEmailResponseDto(m.email) FROM Member m WHERE m.phoneNumber = :phoneNumber")
    Optional<MemberFindEmailResponseDto> findEmailByPhoneNumber(String phoneNumber);
}
