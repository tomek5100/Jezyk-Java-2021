package pl.edu.uj.sender;

/*
 * https://docs.google.com/document/d/19r2BerCh5a3_HKaOPwgxIjEsHoPnfdUxl8X8NKPYoUo/edit?usp=sharing
 **/
public interface Sender {
    void send(Message message, Recipient recipient) throws SenderException, InterruptedException;
}
