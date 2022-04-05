package pl.edu.uj.sender;

import java.sql.Timestamp;

public class EmailMessage extends Message {
    private Long emailMessageId = null;
    private final Timestamp creationDate;
    private final String messageTitle;
    private final String messageBody;

    public EmailMessage(Long emailMessageId, Timestamp creationDate, String messageTitle, String messageBody) {
        this.emailMessageId = emailMessageId;
        this.creationDate = creationDate;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }

    public EmailMessage(Timestamp creationDate, String messageTitle, String messageBody) {
        this.creationDate = creationDate;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }

    public void setEmailMessageId(Long emailMessageId) {
        this.emailMessageId = emailMessageId;
    }

    public Long getEmailMessageId() {
        return emailMessageId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    @Override
    public String getMessageTitle() {
        return messageTitle;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }
}
