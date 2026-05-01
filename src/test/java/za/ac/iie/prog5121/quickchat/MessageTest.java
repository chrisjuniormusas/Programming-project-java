package za.ac.iie.prog5121.quickchat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.",
                Message.validateMessageLength("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    void messageLengthFailure() {
        String longMessage = "a".repeat(255);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.",
                Message.validateMessageLength(longMessage));
    }

    @Test
    void recipientNumberCorrectlyFormatted() {
        assertEquals("Cell phone number successfully captured.",
                Message.checkRecipientCell("+27718693002"));
    }

    @Test
    void recipientNumberIncorrectlyFormatted() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkRecipientCell("08575975889"));
    }

    @Test
    void messageHashIsCorrectForBriefTestData() {
        assertEquals("00:0:HITONIGHT",
                Message.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    void messageHashesCanBeTestedInLoop() {
        String[][] testData = {
                {"0012345678", "0", "Hi Mike, can you join us for dinner tonight?", "00:0:HITONIGHT"},
                {"9912345678", "1", "Hi Keegan, did you receive the payment?", "99:1:HIPAYMENT"}
        };

        for (String[] row : testData) {
            assertEquals(row[3], Message.createMessageHash(row[0], Integer.parseInt(row[1]), row[2]));
        }
    }

    @Test
    void messageIdIsCreatedAndNoMoreThanTenCharacters() {
        String generatedId = Message.generateMessageId();
        Message message = new Message(generatedId, 1, "+27718693002", "Hello there", "Developer", "Sent");
        assertTrue(message.checkMessageID());
        assertEquals(10, generatedId.length());
    }

    @Test
    void sentMessageOptionsReturnCorrectMessages() {
        assertEquals("Message successfully sent.", Message.sentMessage(1));
        assertEquals("Press 0 to delete the message.", Message.sentMessage(2));
        assertEquals("Message successfully stored.", Message.sentMessage(3));
    }
}
