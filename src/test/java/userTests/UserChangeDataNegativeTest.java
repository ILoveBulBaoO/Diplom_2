package userTests;

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

public class UserChangeDataNegativeTest {
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

    // нельзя изменить данные без авторизации
    @DisplayName("Unlogin user can not change data")
    @Test
    public void userCanNotChangeData() {

        // создать юзера и получить access token
        accessToken = userClient.register(randomUser).extract().path("accessToken");

        // дернуть метод изменения данных без авторизации
        ValidatableResponse changeDataResponse = userClient.update(UserCredentials.from(randomUser), "");

        // проверить статус код, боди, сообщение
        int statusCode = changeDataResponse.extract().statusCode();
        Assert.assertEquals(401, statusCode);

        boolean isUserChangeDataSuccess = changeDataResponse.extract().path("success");
        Assert.assertFalse(isUserChangeDataSuccess);

        String actualMessage = changeDataResponse.extract().path("message");
        Assert.assertEquals("Incorrect message", "You should be authorised", actualMessage);

    }
}
