package pl.edu.uj.sender.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface SenderMapper<T> {
    Optional<T> get(ResultSet rs) throws SQLException;
}
