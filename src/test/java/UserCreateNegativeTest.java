import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;

public class UserCreateNegativeTest {
    private User randomUser;
    private User existingUser;
    private UserClient userClient;
    private String accessToken;
    private String refreshToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = UserGenerator.getRandomUser();
        existingUser = UserGenerator.getExistingUser();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    // нельзя создать существующего пользователя
    @DisplayName("User can not be created with existing user data")
    @Test
    public void userCanNotBeCreatedWithExistingData() {


    }

}
