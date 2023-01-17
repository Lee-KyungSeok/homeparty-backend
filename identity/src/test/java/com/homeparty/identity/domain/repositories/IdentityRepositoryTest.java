package com.homeparty.identity.domain.repositories;

import autoparams.AutoSource;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
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
public class IdentityRepositoryTest {

    @Autowired
    private IdentityRepository identityRepository;

    @DisplayName("identity 를 저장한다.")
    @ParameterizedTest
    @AutoSource
    public void Sut_save_Identity(Identity identity) {
        // when
        identityRepository.save(identity);

        // then
        var actual =  identityRepository.findById(identity.getId());
        assertThat(actual.get()).isEqualTo(identity);
    }
}
