package pl.edu.uj.sender;

import java.util.Optional;

public interface RecipientProvider<T extends Recipient> {
    Optional<T> getNextRecipient();
}
