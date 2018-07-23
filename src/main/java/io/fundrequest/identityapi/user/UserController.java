package io.fundrequest.identityapi.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping(value = "/users", params = {"identityProvider", "federatedUsername"})
    public UserRepresentation findUser(@RequestParam String identityProvider, @RequestParam String federatedUsername) {
        return userService.findByFederatedUsername(identityProvider, federatedUsername);
    }
}
