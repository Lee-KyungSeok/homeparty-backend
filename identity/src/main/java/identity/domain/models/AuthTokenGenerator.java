package identity.domain.models;

import identity.domain.aggregates.authtoken.AuthToken;

public interface AuthTokenGenerator {
    AuthToken generate(String id);
}
