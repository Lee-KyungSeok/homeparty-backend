package com.homeparty.invitation.aws;

import com.homeparty.invitation.domain.models.InvitationCardUploadUrlFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;


// https://github.com/aws/aws-sdk-java-v2/issues/1493
// 위를 보면 아직 presignedPost 구현이 되어 있지 않음
@RequiredArgsConstructor
@Service
public class S3InvitationCardUploadUrlFetcher implements InvitationCardUploadUrlFetcher {

    private final S3Presigner presigner;
    private final AwsConfig awsConfig;

    @Override
    public InvitationCardUploadUrlResult fetch(InvitationCardUploadUrlParams params) {

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(awsConfig.invitationCardBucket())
                .key(params.filePath())
                .contentType(params.contentType().getContentType())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(awsConfig.invitationCardUploadDuration()))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);

        return new InvitationCardUploadUrlResult(
                presignedRequest.url(),
                presignedRequest.httpRequest().method().name(),
                null
        );
    }
}
