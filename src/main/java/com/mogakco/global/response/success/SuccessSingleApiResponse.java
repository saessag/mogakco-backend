package com.mogakco.global.response.success;

import lombok.Getter;

@Getter
public class SuccessSingleApiResponse<T> extends SuccessCommonApiResponse {

    private final T data;

    private SuccessSingleApiResponse(final String message, final T data) {
        super(message);
        this.data = data;
    }

    public static <T> SuccessSingleApiResponse<T> of(final String message, final T data) {
        return new SuccessSingleApiResponse<>(message, data);
    }
}
