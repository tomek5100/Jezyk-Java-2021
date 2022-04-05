package pl.edu.uj.sender;

import java.sql.Timestamp;

public class EmailRecipient extends Recipient {
    private Long emailRecipientId = null;
    private final Timestamp creationDate;
    private final String recipientAddress;

    public EmailRecipient(Long emailRecipientId, Timestamp creationDate, String recipientAddress) {
        this.emailRecipientId = emailRecipientId;
        this.creationDate = creationDate;
        this.recipientAddress = recipientAddress;
    }

    public EmailRecipient(Timestamp creationDate, String recipientAddress) {
        this.creationDate = creationDate;
        this.recipientAddress = recipientAddress;
    }

    public void setEmailRecipientId(Long emailRecipientId) {
        this.emailRecipientId = emailRecipientId;
    }

    public Long getEmailRecipientId() {
        return emailRecipientId;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
}
