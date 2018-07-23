package io.fundrequest.identityapi.infrastructure;

public interface KeycloakDBRepository {

    String findUserIdByIdentityProviderAndFederatedUsername(String identityProvider, String username);
}
