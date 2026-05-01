# QuickChat Full POE - PROG5121 Programming 1A

This project is a complete Java Maven console application for the Programming 1A POE. It includes:

- Part 1: Registration and login
- Part 2: Sending, storing and disregarding messages
- Part 3: Arrays, JSON storage, search, delete and reporting
- JUnit unit tests
- GitHub Actions automated testing

The application uses only console input/output. No GUI or JOptionPane is used.

---

## Project Structure

```text
QuickChatFullPOE/
├── pom.xml
├── README.md
├── PRESENTATION_SCRIPT.md
├── .github/workflows/maven.yml
├── src/main/java/za/ac/iie/prog5121/quickchat/
│   ├── Login.java
│   ├── Message.java
│   ├── MessageStore.java
│   └── QuickChatApp.java
└── src/test/java/za/ac/iie/prog5121/quickchat/
    ├── LoginTest.java
    ├── MessageTest.java
    └── MessageStoreTest.java
```

---

## What Each Class Does

### `Login.java`
Handles Part 1:

- username validation
- password validation
- South African cell phone number validation
- user registration
- login verification
- login status message

### `Message.java`
Handles Part 2:

- message ID validation
- recipient cell number validation
- message length validation
- message hash creation
- send/store/disregard response messages
- JSON conversion for stored messages

### `MessageStore.java`
Handles Part 3:

- sent message array
- stored message array
- disregarded message array
- message hash array
- message ID array
- JSON file writing and reading
- longest stored message search
- search by message ID
- search by recipient
- delete by message hash
- report generation

### `QuickChatApp.java`
The console application runner. This is the class to run when demonstrating the project.

---

## How to Run the App in NetBeans

1. Open NetBeans.
2. Select **File > Open Project**.
3. Choose the `QuickChatFullPOE` folder.
4. Make sure NetBeans detects the `pom.xml` file.
5. Open `QuickChatApp.java`.
6. Right-click the file and choose **Run File**.

---

## How to Run Unit Tests in NetBeans

1. Right-click the project.
2. Select **Test**.
3. NetBeans will run the JUnit test classes in `src/test/java`.

---

## How to Run Tests with Maven

From the project root, run:

```bash
mvn test
```

Expected result:

```text
BUILD SUCCESS
```

---

## GitHub Actions

The workflow file is located at:

```text
.github/workflows/maven.yml
```

It automatically runs `mvn test` when code is pushed to GitHub.

---

## Notes About References

The regex validation for South African phone numbers is documented inside `Login.java`. It is based on the international `+27` country code format and international numbering principles.

The JSON storage uses Java's built-in `java.nio.file.Files` class. This is documented inside `MessageStore.java`.

---


