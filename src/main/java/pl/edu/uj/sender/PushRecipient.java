package pl.edu.uj.sender;

public class PushRecipient extends Recipient {
  private final String recipientAddress;

  public PushRecipient(String recipientAddress) {
    this.recipientAddress = recipientAddress;
  }

  @Override
  public String getRecipientAddress() {
    return recipientAddress;
  }
}
