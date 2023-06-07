package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

    // рандомный пользователь со всеми необходимыми полями
    public static User getRandomUser() {
        final String email = RandomStringUtils.randomAlphabetic(6) + "@mail.ru";
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String name = RandomStringUtils.randomAlphabetic(6);
        return new User(email, password, name);
    }

    // рандомный пользователь без email
    public static User getRandomUserWithoutEmail() {
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String name = RandomStringUtils.randomAlphabetic(6);
        return new User(null, password, name);
    }

    // рандомный пользователь без password
    public static User getRandomUserWithoutPassword() {
        final String email = RandomStringUtils.randomAlphabetic(6) + "@mail.ru";;
        final String name = RandomStringUtils.randomAlphabetic(6);
        return new User(email, null, name);
    }

    // рандомный пользователь без name
    public static User getRandomUserWithoutName() {
        final String email = RandomStringUtils.randomAlphabetic(6) + "@mail.ru";;
        final String password = RandomStringUtils.randomAlphabetic(6);
        return new User(email, password, null);
    }

    // существующий пользователь
    public static User getExistingUser() {
        final String email = "existTestUser@ya.ru";
        final String password = "qwerty123";
        final String name = "Exist Test User";
        return new User(email, password, name);
    }

}
