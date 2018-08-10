package io.fundrequest.identityapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fundrequest.common.infrastructure.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest extends AbstractControllerTest<UserController> {

    private UserService userService;

    @Override
    protected UserController setupController() {
        userService = mock(UserService.class);
        return new UserController(userService);
    }

    @Test
    public void findUserByFederatedUsername() throws Exception {
        final String identityProvider = "ghfjhgk";
        final String userName = "adgsfdh";
        final UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(UUID.randomUUID().toString());

        when(userService.findByIdentityProviderAndFederatedUsername(identityProvider, userName)).thenReturn(userRepresentation);

        final String jsonContent = new ObjectMapper().writeValueAsString(userRepresentation);
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                                              .param("identityProvider", identityProvider)
                                              .param("federatedUsername", userName))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(jsonContent));
    }
}
