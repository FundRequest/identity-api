package io.fundrequest.identityapi.infrastructure;

import io.fundrequest.identityapi.WebApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebApplication.class)
public class KeycloakDBRepositoryJdbcImplTest {

    @Autowired
    private KeycloakDBRepository keycloakDBRepositoryImpl;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() throws DataAccessException {
        jdbcTemplate.execute("CREATE TABLE federated_identity ("
                             + "identity_provider VARCHAR(255) NOT NULL, "
                             + "realm_id VARCHAR(36), "
                             + "federated_user_id VARCHAR(255), "
                             + "federated_username VARCHAR(255), "
                             + "token TEXT, "
                             + "user_id VARCHAR(36) NOT NULL,"
                             + "CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id))");
    }

    @Test
    public void findUserIdByRealmAndIdentityProviderAndFederatedUsername() throws DataAccessException {
        final String identityProvider = "ljhk";
        final String federatedUsername = "dfszbgs";
        final String federatedUserId = "462f2db6-cfd2-47d8-a4de-g345aef3fd5";

        insertFederatedIdentity("afdfsa", federatedUsername, "dafdszd");
        insertFederatedIdentity(identityProvider, federatedUsername, federatedUserId);
        insertFederatedIdentity(identityProvider, "afsdsv", "safdadfS");

        assertThat(keycloakDBRepositoryImpl.findUserIdByIdentityProviderAndFederatedUsername(identityProvider, federatedUsername)).isEqualTo(federatedUserId);
    }

    private void insertFederatedIdentity(final String identityProvider, final String federatedUsername, final String federatedUserId) {
        jdbcTemplate.execute("INSERT INTO federated_identity(identity_provider, realm_id, federated_user_id, federated_username, token, user_id) VALUES ("
                             + "'" + identityProvider + "', "
                             + "'afsafd', "
                             + "'213243', "
                             + "'" + federatedUsername + "', "
                             + "'access_token=dgfhgrfnf&scope=read%3Auser%2Cuser%3Aemail&token_type=bearer', "
                             + "'" + federatedUserId + "')");
    }
}
