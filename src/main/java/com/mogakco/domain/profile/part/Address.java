package com.mogakco.domain.profile.part;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Builder
@AllArgsConstructor
public class Address {

    @Comment("도 또는 특별시 내의 시")
    private String city;

    @Comment("도로명 주소")
    private String street;

    @Comment("상세 주소")
    @Column(name = "detail_address")
    private String detail;

    @Comment("우편번호")
    private String postalCode;
}
