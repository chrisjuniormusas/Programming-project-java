package za.ac.iie.prog5121.quickchat;

import java.security.SecureRandom;

/**
 * Represents one QuickChat message and contains the validation rules required
 * by Part 2 of the POE.
 */
public class Message {
    public static final String MESSAGE_READY = "Message ready to send.";
    public static final String PHONE_SUCCESS = "Cell phone number successfully captured.";
    public static final String PHONE_ERROR = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    public static final String SENT = "Message successfully sent.";
    public static final String DISREGARD = "Press 0 to delete the message.";
    public static final String STORED = "Message successfully stored.";

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private final String sender;
    private String flag;

    public Message(String messageId, int messageNumber, String recipient, String messageText, String sender, String flag) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.sender = sender;
        this.flag = flag;
        this.messageHash = createMessageHash(messageId, messageNumber, messageText);
    }

    public static String generateMessageId() {
        long number = Math.abs(RANDOM.nextLong()) % 10_000_000_000L;
        return String.format("%010d", number);
    }

    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    public static String validateMessageLength(String messageText) {
        if (messageText == null) {
            return "Message exceeds 250 characters by 0; please reduce the size.";
        }

        if (messageText.length() <= 250) {
            return MESSAGE_READY;
        }

        int extraCharacters = messageText.length() - 250;
        return "Message exceeds 250 characters by " + extraCharacters + "; please reduce the size.";
    }

    /**
     * Reuses the same South African phone rule used in Login, because the POE
     * asks the student to reuse validation logic where possible.
     */
    public static String checkRecipientCell(String recipient) {
        Login loginValidator = new Login();
        return loginValidator.checkCellPhoneNumber(recipient) ? PHONE_SUCCESS : PHONE_ERROR;
    }

    public static boolean isRecipientCellValid(String recipient) {
        return new Login().checkCellPhoneNumber(recipient);
    }

    public static String createMessageHash(String messageId, int messageNumber, String messageText) {
        String safeId = messageId == null ? "00" : messageId;
        String firstTwoDigits = safeId.length() >= 2 ? safeId.substring(0, 2) : safeId;

        String safeText = messageText == null ? "" : messageText.trim();
        if (safeText.isEmpty()) {
            return firstTwoDigits + ":" + messageNumber + ":";
        }

        String[] words = safeText.split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public static String sentMessage(int option) {
        return switch (option) {
            case 1 -> SENT;
            case 2 -> DISREGARD;
            case 3 -> STORED;
            default -> "Invalid option selected.";
        };
    }

    public String printMessageDetails() {
        return "Message ID: " + messageId + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + messageText;
    }

    public String toJson() {
        return "{"
                + "\"messageId\":\"" + escapeJson(messageId) + "\","
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                + "\"messageNumber\":" + messageNumber + ","
                + "\"sender\":\"" + escapeJson(sender) + "\","
                + "\"recipient\":\"" + escapeJson(recipient) + "\","
                + "\"message\":\"" + escapeJson(messageText) + "\","
                + "\"flag\":\"" + escapeJson(flag) + "\""
                + "}";
    }

    public static Message fromJsonLine(String jsonLine) {
        String messageId = extractJsonValue(jsonLine, "messageId");
        String sender = extractJsonValue(jsonLine, "sender");
        String recipient = extractJsonValue(jsonLine, "recipient");
        String message = extractJsonValue(jsonLine, "message");
        String flag = extractJsonValue(jsonLine, "flag");
        int messageNumber = Integer.parseInt(extractJsonNumber(jsonLine, "messageNumber"));
        return new Message(messageId, messageNumber, recipient, message, sender, flag);
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String extractJsonValue(String jsonLine, String key) {
        String search = "\"" + key + "\":\"";
        int start = jsonLine.indexOf(search);
        if (start < 0) {
            return "";
        }
        start += search.length();
        int end = jsonLine.indexOf("\"", start);
        return end < 0 ? "" : jsonLine.substring(start, end).replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private static String extractJsonNumber(String jsonLine, String key) {
        String search = "\"" + key + "\":";
        int start = jsonLine.indexOf(search);
        if (start < 0) {
            return "0";
        }
        start += search.length();
        int end = jsonLine.indexOf(",", start);
        if (end < 0) {
            end = jsonLine.indexOf("}", start);
        }
        return jsonLine.substring(start, end).trim();
    }

    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getSender() { return sender; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
}
