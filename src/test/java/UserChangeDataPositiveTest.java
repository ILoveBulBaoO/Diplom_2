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

public class UserChangeDataPositiveTest {
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

    // можно изменить данные
    @DisplayName("Login user can change data")
    @Test
    public void userCanChangeData() {

        // создать юзера
        userClient.register(randomUser);

        // залогиниться и получить access token
        accessToken = userClient.login(UserCredentials.from(randomUser)).extract().path("accessToken");

        // дернуть метод изменения данных
        ValidatableResponse changeDataResponse = userClient.update(UserCredentials.from(randomUser), accessToken);

        // проверить статус код и боди
        int statusCode = changeDataResponse.extract().statusCode();
        Assert.assertEquals(200, statusCode);

        boolean isUserChangeDataSuccess = changeDataResponse.extract().path("success");
        Assert.assertTrue(isUserChangeDataSuccess);

        // проверить, что user email, user name соответствуют данным авторизации
        String expectedEmail = UserCredentials.from(randomUser).getEmail().toLowerCase();
        String actualEmail = changeDataResponse.extract().path("user.email");
        Assert.assertEquals("Incorrect email", expectedEmail, actualEmail);

        String expectedName = UserCredentials.from(randomUser).getName();
        String actualName = changeDataResponse.extract().path("user.name");
        Assert.assertEquals("Incorrect name", expectedName, actualName);
    }

}
