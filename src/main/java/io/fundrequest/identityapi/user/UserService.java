package io.fundrequest.identityapi.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserRepresentation findByFederatedUsername(String identityProvider, final String federatedUsername);
}
