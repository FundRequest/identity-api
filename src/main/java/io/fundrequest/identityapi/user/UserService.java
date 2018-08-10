package io.fundrequest.identityapi.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserRepresentation findByIdentityProviderAndFederatedUsername(final String identityProvider, final String federatedUsername);
}
