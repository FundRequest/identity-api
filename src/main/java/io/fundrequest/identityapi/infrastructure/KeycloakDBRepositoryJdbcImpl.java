package io.fundrequest.identityapi.infrastructure;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class KeycloakDBRepositoryJdbcImpl implements KeycloakDBRepository {

    private final JdbcTemplate jdbcTemplate;

    public KeycloakDBRepositoryJdbcImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<String> findUserIdByIdentityProviderAndFederatedUsername(final String identityProvider, final String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT user_id FROM federated_identity WHERE identity_provider = ? AND federated_username = ?",
                                                                   (rs, rowNum) -> rs.getString("user_id"),
                                                                   identityProvider,
                                                                   username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
