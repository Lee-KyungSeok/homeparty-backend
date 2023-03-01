package com.homeparty.invitation.domain.commands;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.homeparty.invitation.aws.AwsConfig;
import com.homeparty.invitation.aws.S3InvitationCardUploadUrlFetcher;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardRepository;
import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardState;
import com.homeparty.invitation.testing.DomainDefaultCustomization;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateInvitationCardCommandHandlerTest {

    @Autowired
    private InvitationCardRepository invitationCardRepository;

    @DisplayName("새로운 초대장 카드를 생성한다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(DomainDefaultCustomization.class)
    public void sut_create_invitation(
            S3Presigner presigner,
            AwsConfig awsConfig,
            CreateInvitationCardCommand command
    ) {
        // given
        var fetcher = new S3InvitationCardUploadUrlFetcher(presigner, awsConfig);
        var sut = new CreateInvitationCardCommandHandler(fetcher, invitationCardRepository);

        // when
        sut.handle(command);

        // then
        var actual =  invitationCardRepository.findById(command.getCardId());

        assertThat(actual.get().getId()).isEqualTo(command.getCardId());
        assertThat(actual.get().getInvitationId()).isNull();
        assertThat(actual.get().getUploaderId()).isEqualTo(command.getUploaderId());
        assertThat(actual.get().getState()).isEqualTo(InvitationCardState.CREATED);
        assertThat(actual.get().getUsedAt()).isNull();
        assertThat(actual.get().getDeletedAt()).isNull();
    }

    @DisplayName("결과로 upload url 을 가져온다.")
    @ParameterizedTest
    @AutoSource()
    @Customization(DomainDefaultCustomization.class)
    public void sut_returns_result(
            S3Presigner presigner,
            AwsConfig awsConfig,
            CreateInvitationCardCommand command
    ) {
        // given
        var fetcher = new S3InvitationCardUploadUrlFetcher(presigner, awsConfig);
        var sut = new CreateInvitationCardCommandHandler(fetcher, invitationCardRepository);

        // when
        var actual = sut.handle(command);

        // then
        assertThat(actual.cardId()).isEqualTo(command.getCardId());
        assertThat(actual.uploadUrl().toString()).contains(
                "https://" + awsConfig.invitationCardBucket() + ".s3.ap-northeast-2.amazonaws.com/invitation-cards/"
                        + command.getCardId().toString() + "_"
        );
        assertThat(actual.httpMethod()).isEqualTo("PUT");
        assertThat(actual.uploadData()).usingRecursiveComparison().isEqualTo(new HashMap<>());
    }
}