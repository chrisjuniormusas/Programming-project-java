package za.ac.iie.prog5121.quickchat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Stores sent, disregarded and stored messages in arrays/lists and provides the
 * reporting methods required in Part 3 of the POE.
 */
public class MessageStore {
    private final List<Message> allMessages = new ArrayList<>();
    private final List<String> sentMessages = new ArrayList<>();
    private final List<String> disregardedMessages = new ArrayList<>();
    private final List<String> storedMessages = new ArrayList<>();
    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIds = new ArrayList<>();

    public void addMessage(Message message) {
        allMessages.add(message);
        messageHashes.add(message.getMessageHash());
        messageIds.add(message.getMessageId());

        if ("Sent".equalsIgnoreCase(message.getFlag())) {
            sentMessages.add(message.getMessageText());
        } else if ("Stored".equalsIgnoreCase(message.getFlag())) {
            storedMessages.add(message.getMessageText());
        } else if ("Disregard".equalsIgnoreCase(message.getFlag()) || "Disregarded".equalsIgnoreCase(message.getFlag())) {
            disregardedMessages.add(message.getMessageText());
        }
    }

    /**
     * Stores messages in a simple JSON-lines file. Each line is one JSON object.
     * This avoids extra dependencies and keeps the project easy to run in NetBeans.
     * Reference: Oracle Java documentation for java.nio.file.Files was used for
     * writing and reading text files.
     */
    public void storeMessage(Message message, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, message.toJson() + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public void loadStoredMessagesFromJson(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            return;
        }
        for (String line : Files.readAllLines(filePath)) {
            if (!line.isBlank()) {
                addMessage(Message.fromJsonLine(line));
            }
        }
    }

    public String displayStoredSendersAndRecipients() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        for (Message message : allMessages) {
            if ("Stored".equalsIgnoreCase(message.getFlag())) {
                output.add("Sender: " + message.getSender() + ", Recipient: " + message.getRecipient());
            }
        }
        return output.length() == 0 ? "No stored messages found." : output.toString();
    }

    public String displayLongestStoredMessage() {
        String longest = "";
        for (Message message : allMessages) {
            if ("Stored".equalsIgnoreCase(message.getFlag()) && message.getMessageText().length() > longest.length()) {
                longest = message.getMessageText();
            }
        }
        return longest.isEmpty() ? "No stored messages found." : longest;
    }

    public String searchByMessageId(String messageId) {
        for (Message message : allMessages) {
            if (message.getMessageId().equals(messageId)) {
                return message.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public String searchRecipientAndMessageById(String messageId) {
        for (Message message : allMessages) {
            if (message.getMessageId().equals(messageId)) {
                return "Recipient: " + message.getRecipient() + System.lineSeparator()
                        + "Message: " + message.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public String searchMessagesByRecipient(String recipient) {
        StringJoiner foundMessages = new StringJoiner(" ");
        for (Message message : allMessages) {
            boolean isSentOrStored = "Sent".equalsIgnoreCase(message.getFlag()) || "Stored".equalsIgnoreCase(message.getFlag());
            if (isSentOrStored && message.getRecipient().equals(recipient)) {
                foundMessages.add(message.getMessageText());
            }
        }
        return foundMessages.length() == 0 ? "No messages found for this recipient." : foundMessages.toString();
    }

    public String deleteMessageByHash(String messageHash) {
        for (int i = 0; i < allMessages.size(); i++) {
            Message message = allMessages.get(i);
            if (message.getMessageHash().equals(messageHash)) {
                String deletedText = message.getMessageText();
                allMessages.remove(i);
                rebuildArrays();
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String displayReport() {
        StringJoiner report = new StringJoiner(System.lineSeparator());
        report.add("Stored/Sent Message Report");
        report.add("--------------------------");
        for (Message message : allMessages) {
            if ("Sent".equalsIgnoreCase(message.getFlag()) || "Stored".equalsIgnoreCase(message.getFlag())) {
                report.add("Message Hash: " + message.getMessageHash());
                report.add("Recipient: " + message.getRecipient());
                report.add("Message: " + message.getMessageText());
                report.add("");
            }
        }
        return report.toString().trim();
    }

    public String printMessages() {
        StringJoiner output = new StringJoiner(System.lineSeparator() + System.lineSeparator());
        for (Message message : allMessages) {
            if ("Sent".equalsIgnoreCase(message.getFlag())) {
                output.add(message.printMessageDetails());
            }
        }
        return output.length() == 0 ? "No messages sent yet." : output.toString();
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public void populateWithPart3TestData() {
        addMessage(new Message("1000000001", 1, "+27834557896", "Did you get the cake?", "Developer", "Sent"));
        addMessage(new Message("1000000002", 2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored"));
        addMessage(new Message("1000000003", 3, "+27834484567", "Yohoooo, I am at your gate.", "Developer", "Disregard"));
        addMessage(new Message("0838884567", 4, "0838884567", "It is dinner time!", "Developer", "Sent"));
        addMessage(new Message("1000000005", 5, "+27838884567", "Ok, I am leaving without you.", "Developer", "Stored"));
    }

    private void rebuildArrays() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
        List<Message> copy = new ArrayList<>(allMessages);
        allMessages.clear();
        for (Message message : copy) {
            addMessage(message);
        }
    }

    public List<String> getSentMessages() { return sentMessages; }
    public List<String> getDisregardedMessages() { return disregardedMessages; }
    public List<String> getStoredMessages() { return storedMessages; }
    public List<String> getMessageHashes() { return messageHashes; }
    public List<String> getMessageIds() { return messageIds; }
    public List<Message> getAllMessages() { return allMessages; }
}
