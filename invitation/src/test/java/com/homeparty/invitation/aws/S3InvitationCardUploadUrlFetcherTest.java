package com.homeparty.invitation.aws;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardContentType;
import com.homeparty.invitation.domain.models.InvitationCardUploadUrlFetcher.InvitationCardUploadUrlParams;
import com.homeparty.invitation.domain.models.InvitationCardUploadUrlFetcher.InvitationCardUploadUrlResult;
import com.homeparty.invitation.testing.DomainDefaultCustomization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class S3InvitationCardUploadUrlFetcherTest {

    @DisplayName("SignedUrl 을 만든다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(DomainDefaultCustomization.class)
    public void sut_return_signed_url(
            String filePath,
            S3Presigner presigner,
            AwsConfig awsConfig
    ) {
        // given
        S3InvitationCardUploadUrlFetcher fetcher = new S3InvitationCardUploadUrlFetcher(presigner, awsConfig);

        // when
        InvitationCardUploadUrlResult actual = fetcher.fetch(new InvitationCardUploadUrlParams(filePath, InvitationCardContentType.JPEG));

        // then
        assertThat(actual.uploadUrl().toString()).contains("https://" + awsConfig.invitationCardBucket() + ".s3.ap-northeast-2.amazonaws.com/" + filePath);
        assertThat(actual.httpMethod()).isEqualTo("PUT");
        assertThat(actual.uploadData()).isNull();
    }
}