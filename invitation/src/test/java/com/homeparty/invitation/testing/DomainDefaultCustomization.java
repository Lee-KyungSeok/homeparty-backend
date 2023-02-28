package com.homeparty.invitation.testing;

import autoparams.customization.CompositeCustomizer;

public class DomainDefaultCustomization extends CompositeCustomizer {

    public DomainDefaultCustomization() {
        super(
                new AwsConfigCustomizer(),
                new DefaultS3PresignerCustomizer(),
                new InvitationLocationCustomizer()
        );
    }
}