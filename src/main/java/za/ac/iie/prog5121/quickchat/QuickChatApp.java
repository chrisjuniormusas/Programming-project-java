package za.ac.iie.prog5121.quickchat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Console entry point for the full QuickChat POE.
 *
 * The application intentionally avoids JOptionPane because the brief requires
 * a console application only.
 */
public class QuickChatApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Path STORED_MESSAGES_FILE = Path.of("data", "stored-messages.jsonl");

    private final Login login = new Login();
    private final MessageStore messageStore = new MessageStore();
    private boolean loggedIn = false;

    public static void main(String[] args) {
        new QuickChatApp().run();
    }

    public void run() {
        System.out.println("Welcome to the QuickChat POE console application.");
        registerUserFlow();
        loginFlow();

        if (loggedIn) {
            quickChatMenu();
        }

        System.out.println("Application closed. Goodbye.");
    }

    private void registerUserFlow() {
        System.out.println("\n--- Registration ---");
        System.out.print("Enter first name: ");
        String firstName = SCANNER.nextLine();
        System.out.print("Enter last name: ");
        String lastName = SCANNER.nextLine();
        login.setNames(firstName, lastName);

        System.out.print("Enter username: ");
        String username = SCANNER.nextLine();
        System.out.println(login.usernameMessage(username));

        System.out.print("Enter password: ");
        String password = SCANNER.nextLine();
        System.out.println(login.passwordMessage(password));

        System.out.print("Enter South African cell phone number with international code: ");
        String cellPhone = SCANNER.nextLine();
        System.out.println(login.cellPhoneMessage(cellPhone));

        String registrationResult = login.registerUser(username, password, cellPhone);
        System.out.println(registrationResult);
    }

    private void loginFlow() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = SCANNER.nextLine();
        System.out.print("Enter password: ");
        String password = SCANNER.nextLine();

        loggedIn = login.loginUser(username, password);
        System.out.println(login.returnLoginStatus(loggedIn));
    }

    private void quickChatMenu() {
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            int option = readInt();
            switch (option) {
                case 1 -> sendMessagesFlow();
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> running = false;
                case 4 -> storedMessagesMenu();
                default -> System.out.println("Invalid option. Please select 1, 2, 3, or 4.");
            }
        }
    }

    private void sendMessagesFlow() {
        System.out.print("How many messages would you like to enter? ");
        int numberOfMessages = readInt();

        for (int i = 1; i <= numberOfMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numberOfMessages + " ---");
            String messageId = Message.generateMessageId();
            System.out.println("Message ID generated: " + messageId);

            System.out.print("Enter recipient cell phone number: ");
            String recipient = SCANNER.nextLine();
            System.out.println(Message.checkRecipientCell(recipient));

            System.out.print("Enter your message: ");
            String messageText = SCANNER.nextLine();
            System.out.println(Message.validateMessageLength(messageText));

            Message message = new Message(messageId, i, recipient, messageText,
                    login.getRegisteredCellPhone(), "Draft");
            System.out.println("Message Hash: " + message.getMessageHash());

            System.out.println("\nChoose what to do with this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choose an option: ");
            int sendOption = readInt();
            System.out.println(Message.sentMessage(sendOption));

            if (sendOption == 1) {
                message.setFlag("Sent");
                messageStore.addMessage(message);
                System.out.println(message.printMessageDetails());
            } else if (sendOption == 2) {
                message.setFlag("Disregard");
                messageStore.addMessage(message);
            } else if (sendOption == 3) {
                message.setFlag("Stored");
                messageStore.addMessage(message);
                try {
                    messageStore.storeMessage(message, STORED_MESSAGES_FILE);
                } catch (IOException e) {
                    System.out.println("The message was stored in memory, but the JSON file could not be updated.");
                }
            }
        }

        System.out.println("Total messages sent: " + messageStore.returnTotalMessages());
    }

    private void storedMessagesMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nStored Messages Menu");
            System.out.println("1) Display sender and recipient of stored messages");
            System.out.println("2) Display longest stored message");
            System.out.println("3) Search by message ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete message by hash");
            System.out.println("6) Display report");
            System.out.println("7) Load stored messages from JSON file");
            System.out.println("8) Populate Part 3 test data");
            System.out.println("9) Return to main menu");
            System.out.print("Choose an option: ");

            int option = readInt();
            switch (option) {
                case 1 -> System.out.println(messageStore.displayStoredSendersAndRecipients());
                case 2 -> System.out.println(messageStore.displayLongestStoredMessage());
                case 3 -> {
                    System.out.print("Enter message ID: ");
                    String id = SCANNER.nextLine();
                    System.out.println(messageStore.searchRecipientAndMessageById(id));
                }
                case 4 -> {
                    System.out.print("Enter recipient number: ");
                    String recipient = SCANNER.nextLine();
                    System.out.println(messageStore.searchMessagesByRecipient(recipient));
                }
                case 5 -> {
                    System.out.print("Enter message hash: ");
                    String hash = SCANNER.nextLine();
                    System.out.println(messageStore.deleteMessageByHash(hash));
                }
                case 6 -> System.out.println(messageStore.displayReport());
                case 7 -> {
                    try {
                        messageStore.loadStoredMessagesFromJson(STORED_MESSAGES_FILE);
                        System.out.println("Stored messages loaded from JSON file.");
                    } catch (IOException e) {
                        System.out.println("Stored messages could not be loaded from JSON file.");
                    }
                }
                case 8 -> {
                    messageStore.populateWithPart3TestData();
                    System.out.println("Part 3 test data populated.");
                }
                case 9 -> running = false;
                default -> System.out.println("Invalid option. Please select a menu number.");
            }
        }
    }

    private int readInt() {
        while (true) {
            String input = SCANNER.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
