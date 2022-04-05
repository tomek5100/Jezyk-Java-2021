package pl.edu.uj.sender.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.uj.sender.DatabaseException;

import java.sql.*;
import java.util.Optional;

public class SenderConnection {
    private static final Logger logger = LoggerFactory.getLogger(SenderConnection.class);

    private Connection connection = null;
    private String url = null;

    public synchronized void connect(String url, String username, String password) throws DatabaseException {
        if (connection == null) {
            logger.info("Connecting to %s".formatted(url));
            this.url = url;
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new DatabaseException("Error connecting to %s".formatted(url), ex);
            }
        } else {
            throw new DatabaseException("Already connected to %s".formatted(url));
        }
    }

    public synchronized void disconnect() throws DatabaseException {
        logger.info("Closing connection to %s".formatted(url));
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseException("Can't close connection %s".formatted(url), ex);
            }
        }
    }

    public synchronized void reconnect(String username, String password) throws DatabaseException {
        logger.info("Reconnecting to %s".formatted(url));
        disconnect();
        connect(url, username, password);
    }

    public <T> Optional<T> executeQuery(String statement, SenderMapper<T> mapper) throws DatabaseException {
        if (connection != null) {
            try (Statement cStatement = connection.createStatement(); ResultSet resultSet = cStatement.executeQuery(statement)) {
                return mapper.get(resultSet);
            } catch (SQLException ex) {
                throw new DatabaseException("Can't execute statement '%s'".formatted(statement), ex);
            }
        } else {
            throw new DatabaseException("Not connected to %s".formatted(url));
        }
    }

    public long executeUpdate(String statement, boolean returnGeneratedKeys) throws DatabaseException {
        if (connection != null) {
            try {
                try (Statement cStatement = connection.createStatement()) {
                    long updated = cStatement.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
                    if (returnGeneratedKeys) { // https://stackoverflow.com/questions/24378270/how-do-you-determine-if-an-insert-or-update-was-successful-using-java-and-mysql
                        final ResultSet rs = cStatement.getGeneratedKeys();// https://stackoverflow.com/questions/4246646/mysql-java-get-id-of-the-last-inserted-value-jdbc
                        if (rs.next()) {
                            return rs.getLong(1);
                        }
                    }
                    return updated;
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Can't execute statement '%s'".formatted(statement), ex);
            }
        } else {
            throw new DatabaseException("Not connected to %s".formatted(url));
        }
    }
}
