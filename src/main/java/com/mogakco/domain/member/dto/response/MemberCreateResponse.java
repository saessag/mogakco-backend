package com.mogakco.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberCreateResponse {

    private String name;
    private String nickname;
    private String email;
    private String responseCode;

    @Builder
    public MemberCreateResponse(String name,
                                String nickname,
                                String email,
                                String responseCode) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.responseCode = responseCode;
    }
}
