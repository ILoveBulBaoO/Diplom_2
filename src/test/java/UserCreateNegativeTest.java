import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;

import static org.junit.Assert.assertEquals;

public class UserCreateNegativeTest {
    private User randomUser;
    private User existingUser;
    private UserClient userClient;
    private User userWithoutEmail;
    private User userWithoutPassword;
    private User userWithoutName;

    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = UserGenerator.getRandomUser();
        existingUser = UserGenerator.getExistingUser();
        userWithoutEmail = UserGenerator.getRandomUserWithoutEmail();
        userWithoutPassword = UserGenerator.getRandomUserWithoutPassword();
        userWithoutName = UserGenerator.getRandomUserWithoutName();
    }

    // нельзя создать существующего пользователя
    @DisplayName("User can not be created with existing user data")
    @Test
    public void userCanNotBeCreatedWithExistingData() {

        // вызвать метод для создания юзера
        ValidatableResponse registerResponse = userClient.register(existingUser);

        // проверить статус код, проверить боди
        int statusCode = registerResponse.extract().statusCode();
        assertEquals(403, statusCode);

        boolean isUserCreatedSuccess = registerResponse.extract().path("success");
        Assert.assertFalse(isUserCreatedSuccess);

        String actualMessage = registerResponse.extract().path("message");
        Assert.assertEquals("User already exists", actualMessage);
    }

    // нельзя создать пользователя без email
    @DisplayName("User can not be created without email")
    @Test
    public void userCanNotBeCreatedWithoutEmail() {

        // вызвать метод для создания юзера
        ValidatableResponse registerResponse = userClient.register(userWithoutEmail);

        // проверить статус код, проверить боди
        int statusCode = registerResponse.extract().statusCode();
        assertEquals(403, statusCode);

        boolean isUserCreatedSuccess = registerResponse.extract().path("success");
        Assert.assertFalse(isUserCreatedSuccess);

        String actualMessage = registerResponse.extract().path("message");
        Assert.assertEquals("Email, password and name are required fields", actualMessage);
    }

    // нельзя создать пользователя без password
    @DisplayName("User can not be created without password")
    @Test
    public void userCanNotBeCreatedWithoutPassword() {

        // вызвать метод для создания юзера
        ValidatableResponse registerResponse = userClient.register(userWithoutPassword);

        // проверить статус код, проверить боди
        int statusCode = registerResponse.extract().statusCode();
        assertEquals(403, statusCode);

        boolean isUserCreatedSuccess = registerResponse.extract().path("success");
        Assert.assertFalse(isUserCreatedSuccess);

        String actualMessage = registerResponse.extract().path("message");
        Assert.assertEquals("Email, password and name are required fields", actualMessage);
    }

    // нельзя создать пользователя без name
    @DisplayName("User can not be created without name")
    @Test
    public void userCanNotBeCreatedWithoutName() {

        // вызвать метод для создания юзера
        ValidatableResponse registerResponse = userClient.register(userWithoutName);

        // проверить статус код, проверить боди
        int statusCode = registerResponse.extract().statusCode();
        assertEquals(403, statusCode);

        boolean isUserCreatedSuccess = registerResponse.extract().path("success");
        Assert.assertFalse(isUserCreatedSuccess);

        String actualMessage = registerResponse.extract().path("message");
        Assert.assertEquals("Email, password and name are required fields", actualMessage);
    }

}
