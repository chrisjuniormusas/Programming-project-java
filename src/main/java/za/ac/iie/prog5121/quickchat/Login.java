package za.ac.iie.prog5121.quickchat;

import java.util.regex.Pattern;

/**
 * Handles registration and login validation for QuickChat.
 *
 * The class is deliberately small and testable. Each rule is placed in its own
 * method so that the application can be unit tested without running the console.
 */
public class Login {
    public static final String USERNAME_SUCCESS = "Username successfully captured.";
    public static final String USERNAME_ERROR = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    public static final String PASSWORD_SUCCESS = "Password successfully captured.";
    public static final String PASSWORD_ERROR = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    public static final String PHONE_SUCCESS = "Cell phone number successfully added.";
    public static final String PHONE_SUCCESS_MARKING = "Cell number successfully captured.";
    public static final String PHONE_ERROR = "Cell phone number incorrectly formatted or does not contain international code.";
    public static final String PHONE_ERROR_MARKING = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    public static final String LOGIN_ERROR = "Username or password incorrect, please try again.";

    private static final Pattern CAPITAL_LETTER = Pattern.compile(".*[A-Z].*");
    private static final Pattern NUMBER = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL_CHARACTER = Pattern.compile(".*[^A-Za-z0-9].*");

    /*
     * South African mobile numbers in international format usually start with +27
     * followed by 9 national digits. This expression accepts +27 and then nine digits.
     * References used for the rule:
     * - International Telecommunication Union, E.164 numbering plan: international
     *   telephone numbers use a leading country code.
     * - South Africa country calling code is +27.
     */
    private static final Pattern SOUTH_AFRICAN_PHONE = Pattern.compile("^\\+27\\d{9}$");

    private String firstName;
    private String lastName;
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhone;

    public Login() {
        this("", "");
    }

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password != null
                && password.length() >= 8
                && CAPITAL_LETTER.matcher(password).matches()
                && NUMBER.matcher(password).matches()
                && SPECIAL_CHARACTER.matcher(password).matches();
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && SOUTH_AFRICAN_PHONE.matcher(cellPhoneNumber).matches();
    }

    public String usernameMessage(String username) {
        return checkUserName(username) ? USERNAME_SUCCESS : USERNAME_ERROR;
    }

    public String passwordMessage(String password) {
        return checkPasswordComplexity(password) ? PASSWORD_SUCCESS : PASSWORD_ERROR;
    }

    public String cellPhoneMessage(String cellPhoneNumber) {
        return checkCellPhoneNumber(cellPhoneNumber) ? PHONE_SUCCESS_MARKING : PHONE_ERROR_MARKING;
    }

    public String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return USERNAME_ERROR;
        }

        if (!checkPasswordComplexity(password)) {
            return PASSWORD_ERROR;
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return PHONE_ERROR;
        }

        registeredUsername = username;
        registeredPassword = password;
        registeredCellPhone = cellPhoneNumber;
        return "The user has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return registeredUsername != null
                && registeredPassword != null
                && registeredUsername.equals(enteredUsername)
                && registeredPassword.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return LOGIN_ERROR;
    }

    public void setNames(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRegisteredUsername() {
        return registeredUsername;
    }

    public String getRegisteredCellPhone() {
        return registeredCellPhone;
    }
}
