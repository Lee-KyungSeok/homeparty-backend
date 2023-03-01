package com.homeparty.invitation.aws;

import autoparams.CsvAutoSource;
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

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class S3InvitationCardUploadUrlFetcherTest {

    @DisplayName("SignedUrl 을 만든다.")
    @ParameterizedTest
    @CsvAutoSource({
            "81cfa37d-23e4-46d3-84d1-51540e5ea7d4, 81cfa37d-23e4-46d3-84d1-51540e5ea7d4",
            "/81cfa37d-23e4-46d3-84d1-51540e5ea7d4, 81cfa37d-23e4-46d3-84d1-51540e5ea7d4",
            "abcd/81cfa37d-23e4-46d3-84d1-51540e5ea7d4, abcd/81cfa37d-23e4-46d3-84d1-51540e5ea7d4",
            "12j3bbsaf_nvjbehf, 12j3bbsaf_nvjbehf",
            "/12j3bbsaf_nvjbehf, 12j3bbsaf_nvjbehf",
    })
    @Customization(DomainDefaultCustomization.class)
    public void sut_return_signed_url(
            String filePath,
            String key,
            S3Presigner presigner,
            AwsConfig awsConfig
    ) {
        // given
        S3InvitationCardUploadUrlFetcher fetcher = new S3InvitationCardUploadUrlFetcher(presigner, awsConfig);

        // when
        InvitationCardUploadUrlResult actual = fetcher.fetch(new InvitationCardUploadUrlParams(filePath, InvitationCardContentType.JPEG));

        // then
        assertThat(actual.uploadUrl().toString()).contains("https://" + awsConfig.invitationCardBucket() + ".s3.ap-northeast-2.amazonaws.com/" + key);
        assertThat(actual.httpMethod()).isEqualTo("PUT");
        assertThat(actual.uploadData()).usingRecursiveComparison().isEqualTo(new HashMap<>());
    }
}