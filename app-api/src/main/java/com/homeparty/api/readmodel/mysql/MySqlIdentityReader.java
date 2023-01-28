package com.homeparty.api.readmodel.mysql;

import com.homeparty.api.readmodel.reader.IdentityReader;
import com.homeparty.api.readmodel.view.IdentityView;
import com.homeparty.identity.domain.aggregates.identity.Identity;
import com.homeparty.identity.domain.aggregates.identity.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MySqlIdentityReader implements IdentityReader {

    private final IdentityRepository identityRepository;

    private IdentityView translateToIdentityView(Identity identity) {
        return new IdentityView(
                identity.getId(),
                identity.getNickname(),
                identity.getProfileImageUrl(),
                identity.getEmail(),
                identity.getProvider().getProviderType()
        );
    }

    @Override
    public Optional<IdentityView> getIdentityById(UUID identityId) {
        return identityRepository.findById(identityId)
                .map(this::translateToIdentityView);
    }
}
