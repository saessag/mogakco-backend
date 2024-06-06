package com.mogakco.domain.member.service;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.model.adapter.MemberAdapter;
import com.mogakco.domain.member.repository.MemberRepository;
import com.mogakco.global.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findById(Long.parseLong(username)).orElseThrow(() -> new BusinessException("존재하는 회원이 없습니다."));

        return new MemberAdapter(member);
    }
}
