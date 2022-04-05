package pl.edu.uj.sender;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

import static java.lang.Thread.sleep;

public class EmailSender implements Sender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Override
    public void send(Message message, Recipient recipient)
            throws SenderException, InterruptedException {
        if (!(message instanceof EmailMessage)) {
            throw new SenderException(
                    MessageFormat.format(
                            "Should be of type ''{0}'', but got ''{1}''",
                            EmailMessage.class.getCanonicalName(), message.getClass()));
        }
        if (!(recipient instanceof EmailRecipient)) {
            throw new SenderException(
                    MessageFormat.format(
                            "Should be of type ''{0}'', but got ''{1}''",
                            EmailRecipient.class.getCanonicalName(), recipient.getClass()));
        }
        if (StringUtils.isEmpty(message.getMessageTitle())
                || StringUtils.isEmpty(message.getMessageBody())
                || StringUtils.isEmpty(recipient.getRecipientAddress())) {
            throw new SenderException("Please provide all data");
        }
        int length = recipient.getRecipientAddress().split("@").length;
        if (length != 2) {
            throw new SenderException(
                    "Invalid recipientAddress= '" + recipient.getRecipientAddress() + "'");
        }
        String bodyMD5 = DigestUtils.md5Hex(message.getMessageBody());
        String anonymizedRecipientAddress = "***@" + recipient.getRecipientAddress().split("@")[1];

        sleep(5000); // sending

        /* Use System.out to graphically distinguish sending from logging */
        System.out.printf("[Email] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n",
                message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
    }
}
