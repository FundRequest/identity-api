package io.fundrequest.identityapi.infrastructure;

import java.util.Optional;

public interface KeycloakDBRepository {

    Optional<String> findUserIdByIdentityProviderAndFederatedUsername(String identityProvider, String username);
}
