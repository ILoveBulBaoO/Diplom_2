import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;
import user.UserGenerator;

import static org.hamcrest.CoreMatchers.startsWith;

public class UserLoginPositiveTest {
    private User randomUser;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    // можно залогиниться существующим пользователем
    @DisplayName("User can log in with existing data")
    @Test
    public void userCanBeLoginTest() {

        // создать юзера
        userClient.register(randomUser);

        // залогиниться
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(randomUser));

        // проверить статус код и боди
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals(200, statusCode);

        boolean isUserCreatedSuccess = loginResponse.extract().path("success");
        Assert.assertTrue(isUserCreatedSuccess);

        // проверить, что токены accessToken(начинается с Bearer ) и не null, refreshToken не null
        accessToken = loginResponse.extract().path("accessToken");
        Assert.assertNotNull(accessToken);
        Assert.assertThat("Access token is incorrect", accessToken, startsWith("Bearer "));

        String refreshToken = loginResponse.extract().path("refreshToken");
        Assert.assertNotNull("Refresh token is null", refreshToken);

        // проверить, что user email, user name соответствуют данным авторизации
        String expectedEmail = UserCredentials.from(randomUser).getEmail().toLowerCase();
        String actualEmail = loginResponse.extract().path("user.email");
        Assert.assertEquals("Incorrect email", expectedEmail, actualEmail);

        String expectedName = UserCredentials.from(randomUser).getName();
        String actualName = loginResponse.extract().path("user.name");
        Assert.assertEquals("Incorrect name", expectedName, actualName);
    }
}
