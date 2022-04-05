package pl.edu.uj.sender.db;

import pl.edu.uj.sender.DatabaseException;
import pl.edu.uj.sender.EmailRecipient;

import java.util.Optional;

public class EmailRecipientDAO implements SenderDAO<EmailRecipient> {
    SenderConnection dbConnection;
    EmailRecipientMapper recipientMapper;

    public EmailRecipientDAO(SenderConnection dbConnection, EmailRecipientMapper recipientMapper) {
        this.dbConnection = dbConnection;
        this.recipientMapper = recipientMapper;
    }

    @Override
    public Optional<EmailRecipient> get(long id) throws DatabaseException {
        return dbConnection.executeQuery(("SELECT * FROM uj_sender.email_recipient " +
                "WHERE email_recipient_id = %d;").formatted(id), recipientMapper);
    }

    @Override
    public Optional<EmailRecipient> get(int statusId) throws DatabaseException {
        throw new DatabaseException(NOT_IMPLEMENTED);
    }

    @Override
    public Optional<EmailRecipient> get(String id) throws DatabaseException {
        return dbConnection.executeQuery(("SELECT * FROM uj_sender.email_recipient " +
                "WHERE recipient_address = '%s';").formatted(id), recipientMapper);
    }

    @Override
    public Long save(EmailRecipient emailRecipient) throws DatabaseException {
        final String statement = ("INSERT INTO uj_sender.email_recipient (creation_date, recipient_address) " +
                "VALUES ('%s', '%s');").formatted(emailRecipient.getCreationDate(),
                emailRecipient.getRecipientAddress());
        return dbConnection.executeUpdate(statement, true);
    }

    @Override
    public long update(EmailRecipient newEmailRecipient, EmailRecipient expectedEmailRecipient) throws DatabaseException {
        throw new DatabaseException(NOT_IMPLEMENTED);
    }

    @Override
    public void delete(EmailRecipient emailRecipient) throws DatabaseException {
        throw new DatabaseException(NOT_IMPLEMENTED);
    }
}
