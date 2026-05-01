# QuickChat Full POE Video Presentation Script

Use this script exactly with the project structure that was built.

Recommended recording length: 10 to 14 minutes.

Open these before recording:

1. NetBeans with `QuickChatFullPOE` open
2. Browser with your GitHub repository open
3. Terminal or NetBeans Output window ready

---

## Scene 1 - Introduction

**Screen:** NetBeans with the project open.

**Say:**

Hello, my name is Chris Junior Musas, and this is my Programming 1A POE project.

This project is a console-based Java application called QuickChat. It covers registration and login, message sending, message storage, arrays, JSON file handling, reports, unit testing, and automated testing with GitHub Actions.

I will demonstrate the project structure, explain the main classes, run the application, run the unit tests, and show the GitHub automation.

---

## Scene 2 - Show Project Structure

**Screen Action:** In NetBeans, expand:

- `src/main/java/za/ac/iie/prog5121/quickchat`
- `src/test/java/za/ac/iie/prog5121/quickchat`

**Say:**

This is the structure of my Maven project. The main application code is inside `src/main/java`, and the unit tests are inside `src/test/java`.

The four main classes are:

- `Login.java`
- `Message.java`
- `MessageStore.java`
- `QuickChatApp.java`

The test classes are:

- `LoginTest.java`
- `MessageTest.java`
- `MessageStoreTest.java`

This separation helps keep the project organised and easier to test.

---

## Scene 3 - Explain `Login.java`

**Screen Action:** Open `Login.java`.

**Say:**

This class handles Part 1 of the POE, which is registration and login.

The `checkUserName` method checks that the username contains an underscore and is no more than five characters long.

The `checkPasswordComplexity` method checks that the password is at least eight characters long, contains a capital letter, contains a number, and contains a special character.

The `checkCellPhoneNumber` method validates a South African phone number using a regular expression. The number must start with the international country code `+27` and then contain the remaining digits.

The `registerUser` method uses these validation methods before storing the user's details. The `loginUser` method then compares the entered login details with the stored registration details.

---

## Scene 4 - Explain `Message.java`

**Screen Action:** Open `Message.java`.

**Say:**

This class handles the message logic for Part 2.

The message contains a message ID, a message number, a recipient, the message text, a sender, a flag, and a message hash.

The `generateMessageId` method creates a random ten-digit message ID.

The `validateMessageLength` method checks that the message is not longer than 250 characters.

The `checkRecipientCell` method validates the recipient number by reusing the same cell phone logic from the login class.

The `createMessageHash` method creates the required message hash using the first two digits of the message ID, the message number, and the first and last words of the message. The hash is converted to uppercase.

The `sentMessage` method returns the correct message depending on whether the user sends, disregards, or stores the message.

---

## Scene 5 - Explain `MessageStore.java`

**Screen Action:** Open `MessageStore.java`.

**Say:**

This class handles Part 3 of the POE.

It stores messages in separate arrays or lists for sent messages, disregarded messages, stored messages, message hashes, and message IDs.

It also contains the methods used to display stored senders and recipients, find the longest stored message, search by message ID, search by recipient, delete a message using its hash, and display a report.

The `storeMessage` method writes stored messages into a JSON-lines file. The `loadStoredMessagesFromJson` method reads the JSON file back into the application.

The `populateWithPart3TestData` method is included so I can quickly demonstrate the exact Part 3 test data required by the POE.

---

## Scene 6 - Explain `QuickChatApp.java`

**Screen Action:** Open `QuickChatApp.java`.

**Say:**

This is the main class that runs the console application.

It first starts with registration, then login. If the login is successful, the user is shown the QuickChat menu.

The main menu has four options:

1. Send Messages
2. Show recently sent messages
3. Quit
4. Stored Messages

I used a while loop to keep the menu running until the user chooses to quit. I also used a for loop when the user enters the number of messages they want to send.

---

## Scene 7 - Run the Application: Registration and Login

**Screen Action:** Right-click `QuickChatApp.java` and select **Run File**.

**Use this sample input:**

First name: `Chris`

Last name: `Musas`

Username: `kyl_1`

Password: `Ch&&sec@ke99!`

Phone: `+27838968976`

Login username: `kyl_1`

Login password: `Ch&&sec@ke99!`

**Say while running:**

I am now running the application. I will register a user using valid test data, and then I will log in using the same username and password.

The system confirms that the username, password and phone number are valid. After that, it registers the user and allows login.

Because the login is successful, the system displays the QuickChat menu.

---

## Scene 8 - Demonstrate Part 2: Send Messages

**Screen Action:** In the main menu, choose option `1`.

**Use this sample input:**

Number of messages: `2`

Message 1 recipient: `+27718693002`

Message 1 text: `Hi Mike, can you join us for dinner tonight?`

Message 1 action: `1`

Message 2 recipient: `08575975889`

Message 2 text: `Hi Keegan, did you receive the payment?`

Message 2 action: `2`

**Say:**

I selected option 1 to send messages. The application asks how many messages I want to enter, and I entered two.

For the first message, I entered a valid recipient number and a message under 250 characters. The system generated a message ID and a message hash. I selected send, so the message details are displayed.

For the second message, I entered an incorrectly formatted recipient number. This demonstrates the recipient validation. I selected disregard, so the system returns the disregard message.

At the end, the application displays the total number of messages sent.

---

## Scene 9 - Demonstrate Part 3: Stored Messages Menu

**Screen Action:** From the main menu, choose option `4`.

Then choose option `8` to populate Part 3 test data.

**Say:**

I am now opening the stored messages menu for Part 3. To demonstrate the required Part 3 features quickly, I will select option 8, which populates the application with the required Part 3 test data.

---

## Scene 10 - Show Longest Stored Message

**Screen Action:** Choose option `2`.

**Say:**

This option searches through the stored messages and displays the longest stored message. The result is the message: `Where are you? You are late! I have asked you to be on time.`

---

## Scene 11 - Search by Message ID

**Screen Action:** Choose option `3`.

Enter message ID: `0838884567`

**Say:**

This option searches for a message using the message ID. I entered `0838884567`, and the system returns the matching recipient and message.

---

## Scene 12 - Search by Recipient

**Screen Action:** Choose option `4`.

Enter recipient: `+27838884567`

**Say:**

This option searches for all sent or stored messages linked to a specific recipient. I entered `+27838884567`, and the system displays both messages linked to that recipient.

---

## Scene 13 - Display Report

**Screen Action:** Choose option `6`.

**Say:**

This option displays the report. The report includes the message hash, recipient, and message content for sent and stored messages.

---

## Scene 14 - Delete by Message Hash

**Screen Action:** Copy one message hash from the report or use the hash shown for the stored message. Choose option `5`, paste the hash, and press Enter.

**Say:**

This option deletes a message using the message hash. The application searches through the stored messages, removes the matching message, and confirms that it has been deleted.

---

## Scene 15 - Run Unit Tests

**Screen Action:** In NetBeans, right-click the project and select **Test**.

**Say:**

I also created unit tests using JUnit. These tests check registration, login, password complexity, phone number validation, message length, recipient validation, message hash creation, sent message options, arrays, searching, deleting, reporting and JSON storage.

I am now running the tests. The tests passing shows that the methods work as expected.

---

## Scene 16 - Show GitHub and GitHub Actions

**Screen Action:** Switch to your browser and open the GitHub repository.

Show:

- repository files
- commits
- `.github/workflows/maven.yml`
- Actions tab

**Say:**

This is my GitHub repository. I used GitHub for version control and committed my work as the project developed.

I also added a GitHub Actions workflow. This workflow runs Maven tests automatically whenever code is pushed to the repository. This helps ensure that changes do not break the application.

---

## Scene 17 - Conclusion

**Screen:** Return to NetBeans or GitHub.

**Say:**

In conclusion, this project demonstrates object-oriented programming, decisions, loops, arrays, string manipulation, JSON file storage, unit testing, and automated testing.

The application is console-based, follows the POE requirements, and is structured so that the code can be tested and maintained.

Thank you for watching my presentation.
