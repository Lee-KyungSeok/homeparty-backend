package identity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class IdentityWithSpringRepositoryTest {

    @Autowired
    private IdentityRepository identityRepository;

    @DisplayName("identity 를 저장한다.")
    @Test
    public void Sut_save_Identity() {
        // given
        var id = UUID.randomUUID();
        var identity = new Identity(id, "kakao");

        // when
        identityRepository.save(identity);

        // then
        var actual =  identityRepository.findById(id);
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getProvider()).isEqualTo("kakao");
    }
}
