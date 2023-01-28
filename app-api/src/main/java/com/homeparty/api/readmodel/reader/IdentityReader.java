package com.homeparty.api.readmodel.reader;

import com.homeparty.api.readmodel.view.IdentityView;

import java.util.Optional;
import java.util.UUID;

public interface IdentityReader {
    Optional<IdentityView> getIdentityById(UUID identityId);
}
