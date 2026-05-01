package za.ac.iie.prog5121.quickchat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    @Test
    void usernameCorrectlyFormattedReturnsTrue() {
        Login login = new Login("Chris", "Musas");
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void usernameIncorrectlyFormattedReturnsFalse() {
        Login login = new Login("Chris", "Musas");
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void usernameErrorMessageMatchesBrief() {
        Login login = new Login("Chris", "Musas");
        assertEquals(Login.USERNAME_ERROR, login.usernameMessage("kyle!!!!!!!"));
    }

    @Test
    void passwordMeetsComplexityRequirements() {
        Login login = new Login("Chris", "Musas");
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertEquals(Login.PASSWORD_SUCCESS, login.passwordMessage("Ch&&sec@ke99!"));
    }

    @Test
    void passwordDoesNotMeetComplexityRequirements() {
        Login login = new Login("Chris", "Musas");
        assertFalse(login.checkPasswordComplexity("password"));
        assertEquals(Login.PASSWORD_ERROR, login.passwordMessage("password"));
    }

    @Test
    void cellPhoneCorrectlyFormatted() {
        Login login = new Login("Chris", "Musas");
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
        assertEquals(Login.PHONE_SUCCESS_MARKING, login.cellPhoneMessage("+27838968976"));
    }

    @Test
    void cellPhoneIncorrectlyFormatted() {
        Login login = new Login("Chris", "Musas");
        assertFalse(login.checkCellPhoneNumber("08966553"));
        assertEquals(Login.PHONE_ERROR_MARKING, login.cellPhoneMessage("08966553"));
    }

    @Test
    void registerUserReturnsSuccessWhenAllInputsAreValid() {
        Login login = new Login("Chris", "Musas");
        assertEquals("The user has been registered successfully.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    void loginSuccessfulReturnsTrueAndWelcomeMessage() {
        Login login = new Login("Chris", "Musas");
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertEquals("Welcome Chris, Musas it is great to see you again.", login.returnLoginStatus(true));
    }

    @Test
    void loginFailedReturnsFalseAndErrorMessage() {
        Login login = new Login("Chris", "Musas");
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrong", "password"));
        assertEquals(Login.LOGIN_ERROR, login.returnLoginStatus(false));
    }
}
