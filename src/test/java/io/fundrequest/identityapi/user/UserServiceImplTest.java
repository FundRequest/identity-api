package io.fundrequest.identityapi.user;

import io.fundrequest.common.infrastructure.exception.ResourceNotFoundException;
import io.fundrequest.identityapi.infrastructure.KeycloakDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserService userService;

    private KeycloakDBRepository dbRepository;
    private RealmResource realmResource;

    @BeforeEach
    void setUp() {
        dbRepository = mock(KeycloakDBRepository.class);
        realmResource = mock(RealmResource.class, RETURNS_DEEP_STUBS);
        userService = new UserServiceImpl(dbRepository, realmResource);
    }

    @Test
    void findByFederatedUsername() {
        final String identityProvider = "hfgjkmklj";
        final String username = "gdfhj";
        final String userId = "afdsgsb";
        final UserRepresentation userRepresentation = mock(UserRepresentation.class);

        when(dbRepository.findUserIdByIdentityProviderAndFederatedUsername(identityProvider, username)).thenReturn(Optional.of(userId));
        when(realmResource.users().get(userId).toRepresentation()).thenReturn(userRepresentation);

        final UserRepresentation result = userService.findByFederatedUsername(identityProvider, username);

        assertThat(result).isSameAs(userRepresentation);
    }

    @Test
    void findByFederatedUsername_userIdEmpty() {
        final String identityProvider = "hfgjkmklj";
        final String username = "gdfhj";

        when(dbRepository.findUserIdByIdentityProviderAndFederatedUsername(identityProvider, username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByFederatedUsername(identityProvider, username)).isInstanceOf(ResourceNotFoundException.class);
    }
}
