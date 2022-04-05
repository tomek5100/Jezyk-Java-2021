package pl.edu.uj.sender.db;

import pl.edu.uj.sender.EmailMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class EmailMessageMapper implements SenderMapper<EmailMessage> {

    public Optional<EmailMessage> get(ResultSet rs) throws SQLException {
        if (rs.next()) {
            final long emailMessageId = rs.getInt("email_message_id");
            final Timestamp creationDate = rs.getTimestamp("creation_date");
            final String messageTitle = rs.getString("message_title");
            final String messageBody = rs.getString("message_body");
            return Optional.of(new EmailMessage(emailMessageId, creationDate, messageTitle, messageBody));
        }
        return Optional.empty();
    }
}
