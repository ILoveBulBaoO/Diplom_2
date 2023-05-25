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

    }

}
