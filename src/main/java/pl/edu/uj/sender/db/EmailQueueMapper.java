package pl.edu.uj.sender.db;

import pl.edu.uj.sender.EmailQueue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class EmailQueueMapper implements SenderMapper<EmailQueue> {

    public Optional<EmailQueue> get(ResultSet rs) throws SQLException {
        if (rs.next()) {
            final long emailQueueId = rs.getInt("email_queue_id");
            final Timestamp creationDate = rs.getTimestamp("creation_date");
            final Timestamp modificationDate = rs.getTimestamp("modification_date");
            final long statusId = rs.getInt("status_id");
            final long emailMessageId = rs.getInt("email_message_id");
            final long emailRecipientId = rs.getInt("email_recipient_id");
            return Optional.of(new EmailQueue(emailQueueId, creationDate, modificationDate, statusId, emailMessageId, emailRecipientId));
        }
        return Optional.empty();
    }
}
