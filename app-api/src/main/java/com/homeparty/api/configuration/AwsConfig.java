package com.homeparty.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@RequiredArgsConstructor
@Configuration
public class AwsConfig {

    @Value("${homeparty.aws.credentials.profile-name:#{null}}")
    String credentialsProfileName;

    @Bean
    public S3Presigner s3Presigner() {
        var builder = S3Presigner
                .builder()
                .region(Region.AP_NORTHEAST_2);

        if (credentialsProfileName != null) {
            builder.credentialsProvider(ProfileCredentialsProvider.create(credentialsProfileName));
        }

        return builder.build();
    }
}
