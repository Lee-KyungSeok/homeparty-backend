package com.homeparty.invitation.testing;

import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerator;
import com.homeparty.invitation.aws.AwsConfig;

import java.util.UUID;

public class AwsConfigCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(AwsConfig.class)
                ? new ObjectContainer(new AwsConfig(UUID.randomUUID().toString(), 60))
                : generator.generate(query, context);
    }
}
