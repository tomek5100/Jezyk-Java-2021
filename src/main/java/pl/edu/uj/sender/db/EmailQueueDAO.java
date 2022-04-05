package pl.edu.uj.sender.db;

import pl.edu.uj.sender.DatabaseException;
import pl.edu.uj.sender.EmailQueue;

import java.util.Optional;

public class EmailQueueDAO implements SenderDAO<EmailQueue> {
    SenderConnection dbConnection;
    EmailQueueMapper emailQueueMapper;

    public EmailQueueDAO(SenderConnection dbConnection, EmailQueueMapper emailQueueMapper) {
        this.dbConnection = dbConnection;
        this.emailQueueMapper = emailQueueMapper;
    }

    @Override
    public Optional<EmailQueue> get(long id) throws DatabaseException {
        throw new DatabaseException(NOT_IMPLEMENTED);
    }

    @Override
    public Optional<EmailQueue> get(int statusId) throws DatabaseException {
        return dbConnection.executeQuery("SELECT * FROM uj_sender.email_queue WHERE status_id = %d LIMIT 1;"
                .formatted(statusId), emailQueueMapper);
    }

    @Override
    public Optional<EmailQueue> get(String id) throws DatabaseException {
        throw new DatabaseException(NOT_IMPLEMENTED);
    }

    @Override
    public Long save(EmailQueue emailQueue) throws DatabaseException {
        // https://www.javamadesoeasy.com/2015/12/how-to-insert-timestamp-in-java-jdbc.html
        final String statement = ("INSERT INTO uj_sender.email_queue (creation_date, modification_date, status_id, email_message_id, email_recipient_id) " +
                "VALUES ('%s','%s','%d','%d','%d');").formatted(SenderDAO.timestampAsString(emailQueue.getCreationDate()),
                SenderDAO.timestampAsString(emailQueue.getModificationDate()),
                emailQueue.getStatusId(),
                emailQueue.getEmailMessageId(),
                emailQueue.getEmailRecipientId());
        return dbConnection.executeUpdate(statement, true);
    }

    @Override
    public long update(EmailQueue newEmailQueue, EmailQueue expectedEmailQueue) throws DatabaseException {
        if (newEmailQueue.getEmailQueueId() != null) {
            return dbConnection.executeUpdate(("UPDATE uj_sender.email_queue SET modification_date = '%s', status_id = %d " +
                    "WHERE email_queue_id = %d AND status_id = %d;")
                    .formatted(SenderDAO.timestampAsString(newEmailQueue.getModificationDate()),
                            newEmailQueue.getStatusId(),
                            newEmailQueue.getEmailQueueId(),
                            expectedEmailQueue.getStatusId()), false);
        }
        throw new DatabaseException("Cannot update: email_queue_id is null");
    }

    @Override
    public void delete(EmailQueue emailQueue) throws DatabaseException {
        if (emailQueue.getEmailQueueId() != null) {
            dbConnection.executeUpdate(("DELETE FROM uj_sender.email_queue " +
                    "WHERE email_queue_id = %d;").formatted(emailQueue.getEmailQueueId()), false);
        } else {
            throw new DatabaseException("Cannot delete: email_queue_id is null");
        }
    }
}
