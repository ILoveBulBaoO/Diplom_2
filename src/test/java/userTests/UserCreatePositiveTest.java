package userTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;

public class UserCreatePositiveTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    // можно создать пользователя, заполнив все необходимые поля
    @DisplayName("User can be created with all required fields")
    @Test
    public void userCanBeRegisteredTest() {

        // вызвать метод для создания юзера
        ValidatableResponse registerResponse = userClient.register(user);

        // проверить статус код, проверить боди
        int statusCode = registerResponse.extract().statusCode();
        Assert.assertEquals(200, statusCode);

        boolean isUserCreatedSuccess = registerResponse.extract().path("success");
        Assert.assertTrue(isUserCreatedSuccess);

        // проверить, что токены accessToken, refreshToken не null
        accessToken = registerResponse.extract().path("accessToken");
        Assert.assertNotNull(accessToken);

        String refreshToken = registerResponse.extract().path("refreshToken");
        Assert.assertNotNull(refreshToken);
    }
}
