package com.homeparty.api.domain.invitation;

import com.homeparty.invitation.aws.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class InvitationConfig {

    @Value("${homeparty.aws.s3.default-bucket}")
    String invitationCardBucket;

    @Bean
    public AwsConfig invitationAwsConfig() {
        return new AwsConfig(invitationCardBucket, 3600); // 1시간
    }
}
