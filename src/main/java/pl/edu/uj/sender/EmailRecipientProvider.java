package pl.edu.uj.sender;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

public class EmailRecipientProvider implements RecipientProvider<EmailRecipient> {
    Random random = new Random();

    /**
     * Returns email address provided by user
     **/
    @Override
    public Optional<EmailRecipient> getNextRecipient() {
        final int i = random.nextInt(4);
        return switch (i) {
            case 0 -> Optional.of(new EmailRecipient(new Timestamp(System.currentTimeMillis()), "MR.Smith@example.com"));
            case 1 -> Optional.of(new EmailRecipient(new Timestamp(System.currentTimeMillis()), "john23@gmail.com"));
            case 2 -> Optional.of(new EmailRecipient(new Timestamp(System.currentTimeMillis()), "aliceinchains@yahoo.com"));
            case 3 -> Optional.empty(); // recipient didn't fill in email address
            default -> throw new IllegalArgumentException();
        };
    }
}
