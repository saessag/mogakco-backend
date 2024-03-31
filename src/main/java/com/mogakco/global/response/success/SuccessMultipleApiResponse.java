package com.mogakco.global.response.success;

import lombok.Getter;

import java.util.List;

@Getter
public class SuccessMultipleApiResponse<T> extends SuccessCommonApiResponse {

    private final List<T> data;

    private SuccessMultipleApiResponse(final String message, final List<T> data) {
        super(message);
        this.data = data;
    }

    public static <T> SuccessMultipleApiResponse<T> of(final String message, final List<T> data) {
        return new SuccessMultipleApiResponse<>(message, data);
    }
}
