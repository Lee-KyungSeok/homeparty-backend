package com.homeparty.invitation.testing;

import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerationContext;
import autoparams.generator.ObjectGenerator;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

public class ProfileS3PresignerCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(S3Presigner.class)
                ? new ObjectContainer(factory(context))
                : generator.generate(query, context);
    }

    private S3Presigner factory(ObjectGenerationContext context) {
        return S3Presigner
                .builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(ProfileCredentialsProvider.create("homeparty_dev"))
                .build();
    }
}