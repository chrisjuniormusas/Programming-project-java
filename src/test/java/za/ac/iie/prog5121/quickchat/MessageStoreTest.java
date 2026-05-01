package za.ac.iie.prog5121.quickchat;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageStoreTest {
    @Test
    void sentMessagesArrayCorrectlyPopulated() {
        MessageStore store = new MessageStore();
        store.populateWithPart3TestData();
        assertEquals(List.of("Did you get the cake?", "It is dinner time!"), store.getSentMessages());
    }

    @Test
    void displayLongestStoredMessage() {
        MessageStore store = new MessageStore();
        store.populateWithPart3TestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                store.displayLongestStoredMessage());
    }

    @Test
    void searchForMessageIdReturnsCorrectMessage() {
        MessageStore store = new MessageStore();
        store.populateWithPart3TestData();
        assertEquals("It is dinner time!", store.searchByMessageId("0838884567"));
    }

    @Test
    void searchAllMessagesByRecipient() {
        MessageStore store = new MessageStore();
        store.populateWithPart3TestData();
        assertEquals("Where are you? You are late! I have asked you to be on time. Ok, I am leaving without you.",
                store.searchMessagesByRecipient("+27838884567"));
    }

    @Test
    void deleteMessageUsingMessageHash() {
        MessageStore store = new MessageStore();
        Message target = new Message("1000000002", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        store.addMessage(target);

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                store.deleteMessageByHash(target.getMessageHash()));
    }

    @Test
    void displayReportContainsHashRecipientAndMessage() {
        MessageStore store = new MessageStore();
        store.populateWithPart3TestData();
        String report = store.displayReport();

        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
    }

    @Test
    void jsonFileCanBeWrittenAndReadIntoArray() throws IOException {
        Path temporaryFolder = Files.createTempDirectory("quickchat-test");
        Path jsonFile = temporaryFolder.resolve("messages.jsonl");

        MessageStore writer = new MessageStore();
        Message storedMessage = new Message("1000000002", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.", "Developer", "Stored");
        writer.storeMessage(storedMessage, jsonFile);

        MessageStore reader = new MessageStore();
        reader.loadStoredMessagesFromJson(jsonFile);

        assertEquals(List.of("Where are you? You are late! I have asked you to be on time."),
                reader.getStoredMessages());
    }
}
