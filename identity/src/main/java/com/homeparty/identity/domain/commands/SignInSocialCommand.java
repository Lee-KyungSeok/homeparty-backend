package com.homeparty.identity.domain.commands;

import abstraction.command.Command;
import com.homeparty.identity.domain.aggregates.identity.SocialProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInSocialCommand implements Command {
    SocialProviderType providerType;
    String providerToken;
}
