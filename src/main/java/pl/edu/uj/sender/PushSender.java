package pl.edu.uj.sender;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class PushSender implements Sender {

    @Override
    public void send(Message message, Recipient recipient) throws SenderException {
        if (!(message instanceof PushMessage)) {
            throw new SenderException(
                    MessageFormat.format(
                            "Should be of type ''{0}'', but got ''{1}''",
                            PushMessage.class.getCanonicalName(), message.getClass()));
        }
        if (!(recipient instanceof PushRecipient)) {
            throw new SenderException(
                    MessageFormat.format(
                            "Should be of type ''{0}'', but got ''{1}''",
                            PushRecipient.class.getCanonicalName(), recipient.getClass()));
        }
        if (StringUtils.isEmpty(message.getMessageTitle())
                || StringUtils.isEmpty(message.getMessageBody())
                || message.getMessageBody().length() > 256
                || StringUtils.isEmpty(recipient.getRecipientAddress())) {
            throw new SenderException("Please provide all data");
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if (recipient.getRecipientAddress().length() != 32
                || !p.matcher(recipient.getRecipientAddress()).find()) {
            throw new SenderException(
                    "Invalid recipientAddress= " + recipient.getRecipientAddress() + "");
        }
        String bodyMD5 = DigestUtils.md5Hex(message.getMessageBody());
        String anonymizedRecipientAddress =
                "***" + recipient.getRecipientAddress().substring(32 - 5, 32);
        System.out.printf("[Push] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n", message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
    }
}
