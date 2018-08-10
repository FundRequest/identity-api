package io.fundrequest.identityapi.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", params = {"identityProvider", "federatedUsername"})
    public UserRepresentation findUser(@RequestParam String identityProvider, @RequestParam String federatedUsername) {
        return userService.findByIdentityProviderAndFederatedUsername(identityProvider, federatedUsername);
    }
}
