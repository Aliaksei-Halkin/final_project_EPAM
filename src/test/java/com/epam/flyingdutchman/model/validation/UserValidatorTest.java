package com.epam.flyingdutchman.model.validation;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class UserValidatorTest extends TestCase {
    private final UserValidator validator = new UserValidator();
    private final String validUsername;
    private final String notValidUsername;
    private final String validPassword;
    private final String notValidPassword;
    private final String validName;
    private final String notValidName;

    public UserValidatorTest(String validUsername, String notValidUsername,
                             String validPassword, String notValidPassword,
                             String validName, String notValidName) {
        this.validUsername = validUsername;
        this.notValidUsername = notValidUsername;
        this.validPassword = validPassword;
        this.notValidPassword = notValidPassword;
        this.validName = validName;
        this.notValidName = notValidName;
    }

    @Test
    public void isValidUserNamePositiveTest() {
        Assert.assertTrue(validator.validateUserName(validUsername));
    }

    @Test
    public void isValidUserNameNegativeTest() {
        Assert.assertFalse(validator.validateUserName(notValidUsername));
    }

    @Test
    public void isValidPasswordPositiveTest() {
        Assert.assertTrue(validator.isValidPassword(validPassword));
    }

    @Test
    public void isValidPasswordNegativeTest() {
        Assert.assertFalse(validator.isValidPassword(notValidPassword));
    }

    @Test
    public void isValidNamePositiveTest() {
        Assert.assertTrue(validator.isValidName(validName));
    }

    @Test
    public void isValidNameNegativeTest() {
        Assert.assertFalse(validator.isValidName(notValidName));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"username", "notValidUsername!",
                        "password", "notOk/",
                        "Li", "@ntony"},
                {"UsErNaMe", "hn",
                        "dhajkds4545", "MoreThanFiftyCharactersIsTooLargeForPasswordReally",
                        "Тен", "Li_"},
                {"USERName1234", "NameIsTooLongReallyLongForUserName",
                        "BVc7W", "!@!",
                        "Андрей", "Andrew#1"},
                {"1234", "QuestionMark?",
                        "55544", "<>!!",
                        "Esmiralda", "My@name"},
                {"User_Name", "Comas,are,not,allowed",
                        "only111", "still not whitespaces",
                        "McMillan", "My.Name"},
                {"dots.is.allowed", "no/any/slashes",
                        "HiHkn", "why123и ещё",
                        "RymskiyKorsakov", "Name!"},
                {".dot_and_underscore.", "////",
                        "PassWoRd", "_underscore",
                        "Alexander", "z"},
                {"abcd", "no|any|pipes",
                        "Kolt14", "1234567-",
                        "ОрловаЛенских", "NoAnyNumbers1234"},
                {"a1b2", "no(brackets)",
                        "yesIknow", "SeV@n!+",
                        "Herman", "NoMoreThanFiftyLettersNoMoreThanFiftyLettersNoMoreThanFiftyLetters"},
                {"_-_-", "no whitespaces",
                        "someLetters", "no whitespaces",
                        "Lancaster", "no whitespaces"},
                {"-.-_", "безКириллицы",
                        "punctuation", "кириллица",
                        "SometimesSurnamesCanBeReallyLong", "no_underscores"}
        });
    }
}