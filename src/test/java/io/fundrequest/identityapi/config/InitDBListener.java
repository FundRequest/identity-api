package io.fundrequest.identityapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;

public class InitDBListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        jdbcTemplate.execute("CREATE TABLE federated_identity ("
                             + "identity_provider VARCHAR(255) NOT NULL, "
                             + "realm_id VARCHAR(36), "
                             + "federated_user_id VARCHAR(255), "
                             + "federated_username VARCHAR(255), "
                             + "token TEXT, "
                             + "user_id VARCHAR(36) NOT NULL,"
                             + "CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id))");
    }
}