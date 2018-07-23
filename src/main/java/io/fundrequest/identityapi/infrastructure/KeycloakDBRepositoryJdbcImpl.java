package io.fundrequest.identityapi.infrastructure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KeycloakDBRepositoryJdbcImpl implements KeycloakDBRepository {

    private final JdbcTemplate jdbcTemplate;

    public KeycloakDBRepositoryJdbcImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findUserIdByIdentityProviderAndFederatedUsername(final String identityProvider, final String username) {
        return jdbcTemplate.queryForObject("SELECT user_id FROM federated_identity WHERE identity_provider = ? AND federated_username = ?",
                                           (rs, rowNum) -> rs.getString("user_id"),
                                           identityProvider,
                                           username);
    }
}
