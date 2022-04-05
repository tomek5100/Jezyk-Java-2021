package pl.edu.uj.sender;

import java.util.Optional;

public interface MessageProvider<T extends Message> {
    Optional<T> getNextMessage() throws InterruptedException;
}
