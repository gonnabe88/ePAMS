package epams.com.config.session;

import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

public class QueryCustomizer implements SessionRepositoryCustomizer<JdbcIndexedSessionRepository> {

  private static final String CREATE_SESSION_QUERY = """
    INSERT INTO THURXE_CSSESM (PRIMARY_ID, SESSION_ID, CREATION_TIME, LAST_ACCESS_TIME, MAX_INACTIVE_INTERVAL, EXPIRY_TIME, PRINCIPAL_NAME)
    VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

private static final String CREATE_SESSION_ATTRIBUTE_QUERY = """
    INSERT INTO THURXE_CSSESD (SESSION_PRIMARY_ID, ATTRIBUTE_NAME, ATTRIBUTE_BYTES)
    VALUES (?, ?, ?)
    """;

private static final String GET_SESSION_QUERY = """
    SELECT S.PRIMARY_ID, S.SESSION_ID, S.CREATION_TIME, S.LAST_ACCESS_TIME, S.MAX_INACTIVE_INTERVAL, SA.ATTRIBUTE_NAME, SA.ATTRIBUTE_BYTES
    FROM THURXE_CSSESM S
    LEFT JOIN THURXE_CSSESD SA ON S.PRIMARY_ID = SA.SESSION_PRIMARY_ID
    WHERE S.SESSION_ID = ?
    """;

private static final String UPDATE_SESSION_QUERY = """
    UPDATE THURXE_CSSESM
    SET SESSION_ID = ?, LAST_ACCESS_TIME = ?, MAX_INACTIVE_INTERVAL = ?, EXPIRY_TIME = ?, PRINCIPAL_NAME = ?
    WHERE PRIMARY_ID = ?
    """;

private static final String UPDATE_SESSION_ATTRIBUTE_QUERY = """
    UPDATE THURXE_CSSESD
    SET ATTRIBUTE_BYTES = ?
    WHERE SESSION_PRIMARY_ID = ?
    AND ATTRIBUTE_NAME = ?
    """;

private static final String DELETE_SESSION_ATTRIBUTE_QUERY = """
    DELETE FROM THURXE_CSSESD
    WHERE SESSION_PRIMARY_ID = ?
    AND ATTRIBUTE_NAME = ?
    """;

private static final String DELETE_SESSION_QUERY = """
    DELETE FROM THURXE_CSSESM
    WHERE SESSION_ID = ?
    AND MAX_INACTIVE_INTERVAL >= 0
    """;

private static final String LIST_SESSIONS_BY_PRINCIPAL_NAME_QUERY = """
    SELECT S.PRIMARY_ID, S.SESSION_ID, S.CREATION_TIME, S.LAST_ACCESS_TIME, S.MAX_INACTIVE_INTERVAL, SA.ATTRIBUTE_NAME, SA.ATTRIBUTE_BYTES
    FROM THURXE_CSSESM S
    LEFT JOIN THURXE_CSSESD SA ON S.PRIMARY_ID = SA.SESSION_PRIMARY_ID
    WHERE S.PRINCIPAL_NAME = ?
    """;

private static final String DELETE_SESSIONS_BY_EXPIRY_TIME_QUERY = """
    DELETE FROM THURXE_CSSESM
    WHERE EXPIRY_TIME < ?
    """;

    @Override
    public void customize(JdbcIndexedSessionRepository sessionRepository) {
        sessionRepository.setCreateSessionQuery(CREATE_SESSION_QUERY);
        sessionRepository.setCreateSessionAttributeQuery(CREATE_SESSION_ATTRIBUTE_QUERY);        
        sessionRepository.setGetSessionQuery(GET_SESSION_QUERY);
        sessionRepository.setUpdateSessionQuery(UPDATE_SESSION_QUERY);
        sessionRepository.setUpdateSessionAttributeQuery(UPDATE_SESSION_ATTRIBUTE_QUERY);
        sessionRepository.setDeleteSessionAttributeQuery(DELETE_SESSION_ATTRIBUTE_QUERY);
        sessionRepository.setDeleteSessionQuery(DELETE_SESSION_QUERY);
        sessionRepository.setListSessionsByPrincipalNameQuery(LIST_SESSIONS_BY_PRINCIPAL_NAME_QUERY);
        sessionRepository.setDeleteSessionsByExpiryTimeQuery(DELETE_SESSIONS_BY_EXPIRY_TIME_QUERY);
    }
}