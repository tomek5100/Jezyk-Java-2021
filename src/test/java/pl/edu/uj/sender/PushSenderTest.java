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

class PushSenderTest {

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
  void testOKMessageAndRecipient() throws SenderException {
    PushSender sender = new PushSender();
    Message message = new PushMessage("Test title", "Test message");
    Recipient recipient = new PushRecipient("abcdefghijklmnopqrstuvxyz1234789");
    sender.send(message, recipient);
    assertEquals(
        "[Push] Message sent, title= 'Test title', bodyMD5= '82dfa5549ebc9afc168eb7931ebece5f', recipient= '***34789'\n",
        systemOutContent.toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(
      strings = {"null", "thisIsNotAValidLengthHash123", "abcdefghijklmnopqrstuvxyz@234789"})
  void testEmptyOrInvalidRecipient(String invalidValue) {
    PushSender sender = new PushSender();
    Message message = new PushMessage("Test title", "Test message");
    Recipient recipient = new PushRecipient(invalidValue);
    assertThrows(SenderException.class, () -> sender.send(message, recipient));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void testEmptyMessageTitle(String invalidValue) {
    PushSender sender = new PushSender();
    Message message = new PushMessage(invalidValue, "Test message");
    Recipient recipient = new PushRecipient("abcdefghijklmnopqrstuvxyz1234789");
    assertThrows(SenderException.class, () -> sender.send(message, recipient));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(
      strings = {
        "This message is a very very very very very very very very very very very very very very very very very "
            + "very very very very very very very very very very very very very very very very very very very "
            + "very very very very very very very very very very very very long"
      })
  void testEmptyOrInvalidMessageBody(String invalidValue) {
    PushSender sender = new PushSender();
    Message message = new PushMessage("Title is OK", invalidValue);
    Recipient recipient = new PushRecipient("abcdefghijklmnopqrstuvxyz1234789");
    assertThrows(SenderException.class, () -> sender.send(message, recipient));
  }

}
