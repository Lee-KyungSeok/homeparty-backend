package com.homeparty.invitation.domain.repositories;

import autoparams.AutoSource;
import com.homeparty.invitation.domain.aggregates.invitationcomment.InvitationComment;
import com.homeparty.invitation.domain.aggregates.invitationcomment.InvitationCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InvitationInvitationCommentRepositoryTest {

    @Autowired
    private InvitationCommentRepository sut;

    @DisplayName("초대장 댓글을 저장한다.")
    @ParameterizedTest
    @AutoSource()
    public void sut_save_comment(InvitationComment invitationComment) {

        // when
        sut.save(invitationComment);

        // then
        var actual = sut.findById(invitationComment.getId());
        assertThat(actual.get())
                .usingRecursiveComparison()
                .ignoringFields("sequence")
                .isEqualTo(invitationComment);
    }
}