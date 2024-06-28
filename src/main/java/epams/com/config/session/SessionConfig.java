package epams.com.config.session;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.support.TransactionOperations;


@Configuration
@EnableJdbcHttpSession(cleanupCron = "0 0 * * * *") // top of every hour of every day
public class SessionConfig {

    @Bean
    public TableNameCustomizer tableNameCustomizer() {
        return new TableNameCustomizer();
    }

    @Bean
    public QueryCustomizer queryCustomizer() {
        return new QueryCustomizer();
    }

    @Bean("springSessionTransactionOperations")
    public TransactionOperations springSessionTransactionOperations() {
        return TransactionOperations.withoutTransaction();
    }

}
