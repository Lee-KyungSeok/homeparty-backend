package com.homeparty.api.dto;

import abstraction.exeption.HomePartyExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApiResponse<T extends Object> {

    private static final String SUCCESS_CODE = "SUCCESS";
    private static final String SUCCESS_MESSAGE = "api success";

    private String code;

    private String message;

    private T data;

    public static ApiResponse<Object> success() {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, new HashMap<>());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<Object> failure(String code, String message) {
        return new ApiResponse<>(code, message, new HashMap<>());
    }

    public static ApiResponse<Object> failure(HomePartyExceptionCode exceptionCode) {
        return new ApiResponse<>(exceptionCode.getCode(), exceptionCode.getMessage(), new HashMap<>());
    }
}
