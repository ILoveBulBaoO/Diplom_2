import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;
import user.UserGenerator;

public class UserLoginNegativeTest {
    private UserClient userClient;
    private User randomUser;

    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = UserGenerator.getRandomUser();
    }

    // нельзя залогиниться с неверным логином
    @DisplayName("Can not login with incorrect login")
    @Test
    public void userCanNotLoginWithIncorrectLogin() {

        // залогиниться юзером которого не регистрировали
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(randomUser));

        // проверить статус код и боди
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals(401, statusCode);

        boolean isUserLoginSuccess = loginResponse.extract().path("success");
        Assert.assertFalse(isUserLoginSuccess);

        String actualMessage = loginResponse.extract().path("message");
        Assert.assertEquals("Incorrect message", "email or password are incorrect", actualMessage);
    }
}
