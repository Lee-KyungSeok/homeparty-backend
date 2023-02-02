package com.homeparty.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExistedResponse {
    private boolean exist;

    protected ExistedResponse() {

    }
}
