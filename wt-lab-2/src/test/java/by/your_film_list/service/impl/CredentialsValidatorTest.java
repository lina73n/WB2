package by.your_film_list.service.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CredentialsValidatorTest {
    @Test
    void testLoginValidationOnStringWithUnacceptableCharacters() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String login = "Lina$$$";
        assertFalse(credentialsValidator.validLogin(login));
    }

    @Test
    void testLoginValidationOnNullString() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String login = null;
        assertFalse(credentialsValidator.validLogin(login));
    }

    @Test
    void testLoginValidationOnCorrectLogin() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String login = "MyPassword1234";
        assertTrue(credentialsValidator.validLogin(login));
    }

    @Test
    void testPasswordValidationOnStringWithTooShortLen() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String password = "1234";
        assertFalse(credentialsValidator.validPassword(password));
    }

    @Test
    void testPasswordValidationOnNullString() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String password = null;
        assertFalse(credentialsValidator.validPassword(password));
    }

    @Test
    void testPasswordValidationOnCorrectPassword() {
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        String password = "11mpassword123";
        assertTrue(credentialsValidator.validPassword(password));
    }
}
