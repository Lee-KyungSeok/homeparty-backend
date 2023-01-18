package com.homeparty.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerifySocialUserExistedResponse {
    boolean exist;

    protected VerifySocialUserExistedResponse() {

    }
}
