import org.junit.After;
import org.junit.Before;
import user.User;
import user.UserClient;
import user.UserGenerator;

public class UserCreateNegativeTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private String refreshToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }


}
