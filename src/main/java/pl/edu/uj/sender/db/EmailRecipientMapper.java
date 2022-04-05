package pl.edu.uj.sender.db;

import pl.edu.uj.sender.EmailRecipient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class EmailRecipientMapper implements SenderMapper<EmailRecipient> {

    public Optional<EmailRecipient> get(ResultSet rs) throws SQLException {
        if (rs.next()) {
            final long emailRecipientId = rs.getInt("email_recipient_id");
            final Timestamp creationDate = rs.getTimestamp("creation_date");
            final String recipientAddress = rs.getString("recipient_address");
            return Optional.of(new EmailRecipient(emailRecipientId, creationDate, recipientAddress));
        }
        return Optional.empty();
    }
}
