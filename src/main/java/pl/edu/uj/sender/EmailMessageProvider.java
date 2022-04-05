package pl.edu.uj.sender;

import java.sql.Timestamp;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class EmailMessageProvider implements MessageProvider<EmailMessage> {
    int messagesCount = 100;

    @Override
    public Optional<EmailMessage> getNextMessage() throws InterruptedException {
        int currentCount;
        synchronized (this) {
            if (messagesCount > 0) {
                currentCount = messagesCount--;
            } else {
                return Optional.empty(); // user does not provide any new messages
            }
        }
        sleep(1000); // waiting for user to provide message
        return Optional.of(new EmailMessage(new Timestamp(System.currentTimeMillis()),
                "Hi there this is message nr %d".formatted(100 - currentCount),
                "This is your email!"));
    }
}
