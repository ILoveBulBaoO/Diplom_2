package order;

import data.GeneralData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends GeneralData {
    private static final String ORDER_CREATE_ORDER_GET = "api/orders";

    public OrderClient() {

    }

    @Step("Create order")
    // создать ордер
    public ValidatableResponse createOrder(OrderCredentials orderCredentials, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(orderCredentials)
                .when()
                .post(ORDER_CREATE_ORDER_GET)
                .then();
    }

    @Step("Get order")
    // получить ордер
    public ValidatableResponse getOrder(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_CREATE_ORDER_GET)
                .then();
    }

}
