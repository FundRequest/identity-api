package io.fundrequest.identityapi.user;

import io.fundrequest.identityapi.infrastructure.KeycloakDBRepository;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final KeycloakDBRepository dbRepository;
    private final RealmResource realmResource;

    public UserServiceImpl(final KeycloakDBRepository dbRepository, final RealmResource realmResource) {
        this.dbRepository = dbRepository;
        this.realmResource = realmResource;
    }

    @Override
    public UserRepresentation findByFederatedUsername(final String identityProvider, final String federatedUsername) {
        final String userid = dbRepository.findUserIdByIdentityProviderAndFederatedUsername(identityProvider, federatedUsername);
        return realmResource.users().get(userid).toRepresentation();
    }
}
