package pl.edu.uj.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.uj.sender.db.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class SenderApplication {
    private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

    public static void main(String[] args) throws InterruptedException, DatabaseException {

        if (args.length == 4) {
            String username = args[0];
            String password = args[1];

            SenderConnection dbConnection = new SenderConnection();
            dbConnection.connect("jdbc:mysql://localhost:3306/uj_sender", username, password);

            EmailMessageMapper messageMapper = new EmailMessageMapper();
            EmailMessageDAO emailMessageDAO = new EmailMessageDAO(dbConnection, messageMapper);
            EmailRecipientMapper recipientMapper = new EmailRecipientMapper();
            EmailRecipientDAO recipientDAO = new EmailRecipientDAO(dbConnection, recipientMapper);
            EmailQueueMapper queueMapper = new EmailQueueMapper();
            EmailQueueDAO queueDAO = new EmailQueueDAO(dbConnection, queueMapper);

            final int numberOfEnqueuingThreads = Integer.parseInt(args[2]);
            final int numberOfSendingThreads = Integer.parseInt(args[3]);
            logger.info("There will be %d enqueuing threads and %d sender threads"
                    .formatted(numberOfEnqueuingThreads, numberOfSendingThreads));

            EmailSender emailSender = new EmailSender();
            EmailMessageProvider messageProvider = new EmailMessageProvider();
            EmailRecipientProvider recipientProvider = new EmailRecipientProvider();

            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numberOfEnqueuingThreads; i++) {
                threads.add(new Thread(new EmailEnquerRunnable(messageProvider, recipientProvider, queueDAO, emailMessageDAO, recipientDAO)));
            }
            for (int i = 0; i < numberOfSendingThreads; i++) {
                threads.add(new Thread(new EmailSenderRunnable(emailSender, queueDAO, emailMessageDAO, recipientDAO)));
            }

            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }

        } else {
            logger.error("Params should be: username password enqueuing-threads-count sender-threads-count");
            System.exit(-1);
        }
    }

    private static class EmailEnquerRunnable implements Runnable {
        private final EmailMessageProvider messageProvider;
        private final EmailRecipientProvider recipientProvider;

        private final EmailQueueDAO queueDAO;
        private final EmailMessageDAO emailMessageDAO;
        private final EmailRecipientDAO recipientDAO;

        private int counter = 0;

        public EmailEnquerRunnable(EmailMessageProvider messageProvider, EmailRecipientProvider recipientProvider, EmailQueueDAO queueDAO, EmailMessageDAO emailMessageDAO, EmailRecipientDAO recipientDAO) {
            this.messageProvider = messageProvider;
            this.recipientProvider = recipientProvider;
            this.queueDAO = queueDAO;
            this.emailMessageDAO = emailMessageDAO;
            this.recipientDAO = recipientDAO;
        }

        @Override
        public void run() {
            try {
                do {
                    logger.info("Enqueueing message.");
                    final Optional<EmailMessage> nextMessage = messageProvider.getNextMessage();
                    if (nextMessage.isPresent()) {
                        final long messageId = emailMessageDAO.save(nextMessage.get());
                        final Optional<EmailRecipient> nextRecipient = recipientProvider.getNextRecipient();
                        if (nextRecipient.isPresent()) {
                            final Optional<EmailRecipient> existingEmailRecipient = recipientDAO.get(nextRecipient.get().getRecipientAddress());
                            long recipientId;
                            if (existingEmailRecipient.isEmpty()) { // add only new (distinct) recipients (by email)
                                recipientId = recipientDAO.save(nextRecipient.get());
                                logger.info("New recipient added, recipient_queue_id= %d.".formatted(recipientId));
                            } else {
                                recipientId = existingEmailRecipient.get().getEmailRecipientId();
                            }
                            final EmailQueue emailQueue = new EmailQueue(new Timestamp(System.currentTimeMillis()), messageId, recipientId);
                            final Long emailQueueId = queueDAO.save(emailQueue);
                            logger.info("Message enqueued, email_queue_id= %d.".formatted(emailQueueId));
                            counter = 0;
                        }
                    } else {
                        logger.info("No message to enqueue.");
                        counter++;
                        sleep(1000); // wait for new message to send
                    }
                } while (counter < 100);
            } catch (DatabaseException | InterruptedException e) {
                logger.error("Couldn't enqueue message", e);
            }
        }
    }

    private static class EmailSenderRunnable implements Runnable {
        private final EmailSender emailSender;
        private final EmailQueueDAO queueDAO;
        private final EmailMessageDAO emailMessageDAO;
        private final EmailRecipientDAO recipientDAO;

        private int counter = 0;

        public EmailSenderRunnable(EmailSender emailSender, EmailQueueDAO queueDAO, EmailMessageDAO emailMessageDAO, EmailRecipientDAO recipientDAO) {
            this.emailSender = emailSender;
            this.queueDAO = queueDAO;
            this.emailMessageDAO = emailMessageDAO;
            this.recipientDAO = recipientDAO;
        }

        @Override
        public void run() {
            do {
                try {
                    logger.info("Getting message package to send.");
                    final Optional<EmailQueue> expectedEmailQueue = queueDAO.get(EmailQueueStatus.AWAITING.getId());
                    if (expectedEmailQueue.isPresent()) {
                        EmailQueue emailQueueToProcess = expectedEmailQueue.get();
                        processQueueElement(emailQueueToProcess);
                        counter = 0;
                    } else {
                        logger.info("No email to send, waiting.");
                        counter++;
                        sleep(1000); // wait for new element in the queue
                    }
                } catch (SenderException | DatabaseException | InterruptedException e) {
                    logger.error("Couldn't send a message", e);
                }
            } while (counter < 100);
        }

        private void processQueueElement(EmailQueue emailQueueToProcess) throws DatabaseException, SenderException, InterruptedException {
            final Optional<EmailRecipient> emailRecipient = recipientDAO.get(emailQueueToProcess.getEmailRecipientId());
            if (emailRecipient.isPresent()) {
                final Optional<EmailMessage> emailMessage = emailMessageDAO.get(emailQueueToProcess.getEmailMessageId());
                if (emailMessage.isPresent()) {
                    logger.info("Delivering message, emailQueueId = %d, recipientId = %d, messageId = %d."
                            .formatted(emailQueueToProcess.getEmailQueueId(), emailRecipient.get().getEmailRecipientId(), emailMessage.get().getEmailMessageId()));
                    EmailQueue newEmailQueue = emailQueueToProcess.copy();
                    newEmailQueue.setStatusId(EmailQueueStatus.IN_PROGRESS.getId());
                    newEmailQueue.setModificationDate(new Timestamp(System.currentTimeMillis()));
                    final long updated = queueDAO.update(newEmailQueue, emailQueueToProcess);
                    if (updated > 0) {
                        emailSender.send(emailMessage.get(), emailRecipient.get());
                        queueDAO.delete(newEmailQueue);
                    } else {
                        logger.info("Message in progress by other thread, emailQueueId = %d, recipientId = %d, messageId = %d."
                                .formatted(emailQueueToProcess.getEmailQueueId(), emailRecipient.get().getEmailRecipientId(), emailMessage.get().getEmailMessageId()));
                    }
                }
            }
        }
    }
}
