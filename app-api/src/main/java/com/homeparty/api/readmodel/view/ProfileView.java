package com.homeparty.api.readmodel.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class ProfileView {
    private UUID id;
    private String nickname;
    private String profileImageUrl;
}
