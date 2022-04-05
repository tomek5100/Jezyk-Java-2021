package pl.edu.uj.sender;

public class PushMessage extends Message {
  private final String messageTitle;
  private final String messageBody;

  public PushMessage(String messageTitle, String messageBody) {
    this.messageTitle = messageTitle;
    this.messageBody = messageBody;
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
