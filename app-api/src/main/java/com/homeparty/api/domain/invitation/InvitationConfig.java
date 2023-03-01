package com.homeparty.api.domain.invitation;

import com.homeparty.identity.kakao.KakaoSocialProviderFetcher;
import com.homeparty.invitation.aws.AwsConfig;
import com.homeparty.invitation.aws.S3InvitationCardUploadUrlFetcher;
import com.homeparty.invitation.domain.models.InvitationCardUploadUrlFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@RequiredArgsConstructor
@Configuration
public class InvitationConfig {

    @Value("${homeparty.aws.s3.default-bucket}")
    String invitationCardBucket;

    @Bean
    public AwsConfig invitationAwsConfig() {
        return new AwsConfig(invitationCardBucket, 1800); // 30 분
    }

    @Bean
    public InvitationCardUploadUrlFetcher s3InvitationCardUploadUrlFetcher(
            S3Presigner presigner,
            AwsConfig config
    ) {
        return new S3InvitationCardUploadUrlFetcher(presigner, config);
    }
}
