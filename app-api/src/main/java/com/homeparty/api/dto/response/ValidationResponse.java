package com.homeparty.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationResponse {
    private boolean isValid;

    protected ValidationResponse() {

    }
}
