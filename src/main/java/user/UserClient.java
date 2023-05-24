package user;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends GeneralData {
    private static final String USER_REGISTER = "api/auth/register";
    private static final String USER_LOGIN = "api/auth/login";
    private static final String USER_UPDATE = "api/auth/user";

    public UserClient() {

    }

    // зарегистрировать
    public ValidatableResponse register(User user) {
        return given()
                .spec(getBaseSpec)
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then();
    }

    // логин
    public ValidatableResponse login() {
        return given()
                .spec(getBaseSpec)
                .body(user)
                .when()
                .post(USER_LOGIN)
                .then();
    }


}
