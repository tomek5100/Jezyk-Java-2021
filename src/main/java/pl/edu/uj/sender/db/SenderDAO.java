package pl.edu.uj.sender.db;

import pl.edu.uj.sender.DatabaseException;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public interface SenderDAO<T> {
    String NOT_IMPLEMENTED = "Not implemented";

    static String timestampAsString(Timestamp timestamp) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(timestamp.toLocalDateTime());
    }

    Optional<T> get(long id) throws DatabaseException;

    Optional<T> get(int statusId) throws DatabaseException;

    Optional<T> get(String id) throws DatabaseException;

    Long save(T t) throws DatabaseException;

    long update(T newT, T expectedT) throws DatabaseException;

    void delete(T t) throws DatabaseException;

}
