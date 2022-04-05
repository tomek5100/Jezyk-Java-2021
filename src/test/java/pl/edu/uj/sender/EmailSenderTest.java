package pl.edu.uj.sender;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailSenderTest {
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {

        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    void testOKMessageAndRecipient() throws SenderException, InterruptedException {
        EmailSender sender = new EmailSender();
        Message message = new EmailMessage(0L, null, "Test title", "Test message");
        Recipient recipient = new EmailRecipient(0L, null, "MR.Smith@example.com");
        sender.send(message, recipient);
        assertEquals(
                "[Email] Message sent, title= 'Test title', bodyMD5= '82dfa5549ebc9afc168eb7931ebece5f', recipient= '***@example.com'\n",
                systemOutContent.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "null",
                    "thisIsNotAValidEmail",
                    "smithexample.com",
                    "smith@examp@le.com",
                    "MR.Smith+example.com"
            })
    void testEmptyOrInvalidRecipient(String invalidValue) {
        EmailSender sender = new EmailSender();
        Message message = new EmailMessage(0L, null, "Test title", "Test message");
        Recipient recipient = new EmailRecipient(0L, null, invalidValue);
        assertThrows(SenderException.class, () -> sender.send(message, recipient));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyMessageTitle(String invalidValue) {
        EmailSender sender = new EmailSender();
        Message message = new EmailMessage(0L, null, invalidValue, "Test message");
        Recipient recipient = new EmailRecipient(0L, null, "MR.Smith@example.com");
        assertThrows(SenderException.class, () -> sender.send(message, recipient));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyOrInvalidMessageBody(String invalidValue) {
        EmailSender sender = new EmailSender();
        Message message = new EmailMessage(0L, null, "Title is OK", invalidValue);
        Recipient recipient = new EmailRecipient(0L, null, "MR.Smith@example.com");
        assertThrows(SenderException.class, () -> sender.send(message, recipient));
    }
}
