package orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import order.OrderCredentials;
import order.OrderGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserCredentials;
import user.UserGenerator;

public class OrderCreateNegativeTest {

    private UserClient userClient;

    private String accessToken;
    private Order incorrectOrder;
    private Order orderWithoutIngredients;
    private Order defaultOrder;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        incorrectOrder = OrderGenerator.incorrectOrder();
        orderWithoutIngredients = OrderGenerator.orderWithoutIngredients();
        defaultOrder = OrderGenerator.getDefaultOrder();
    }

    @After
    public void cleanUn() {
        if (userClient != null && accessToken != null && accessToken.equals("")) {
            userClient.delete(accessToken);
        }
    }

    // нельзя создать заказ без ингредиентов авторизованным юзером
    @DisplayName("Can not create order login user without ingredients")
    @Test
    public void loginUserCanNotCreateOrderWithoutIngredients() {

        // получить access Token
        accessToken = getAccessToken();

        // создать ордер
        ValidatableResponse orderResponse = orderClient.createOrder(OrderCredentials.from(orderWithoutIngredients), accessToken);

        // проверить статус код и боди
        int statusCode = orderResponse.extract().statusCode();
        Assert.assertEquals(400, statusCode);

        boolean isOrderCreatedSuccess = orderResponse.extract().path("success");
        Assert.assertFalse(isOrderCreatedSuccess);

        // проверить message
        String actualMessage = orderResponse.extract().path("message");
        Assert.assertEquals("Incorrect message", "Ingredient ids must be provided", actualMessage);

    }

    // нельзя создать заказ с невалидными игредиентами авторизованным юзером
    @DisplayName("Can not create order login user with incorrect ingredients")
    @Test
    public void loginUserCanNotCreateOrderWithIncorrectIngredients() {

        // получить access Token
        accessToken = getAccessToken();

        // создать ордер
        ValidatableResponse orderResponse = orderClient.createOrder(OrderCredentials.from(incorrectOrder), accessToken);

        // проверить статус код
        int statusCode = orderResponse.extract().statusCode();
        Assert.assertEquals(500, statusCode);

    }

    // нельзя создать заказ неавторизованным юзером
    @DisplayName("Can not create order unregister user")
    @Test
    public void unRegisterUserCanNotCreateOrder() {

        // залогиниться юзером которого не регистрировали
        accessToken = "Bearer 1234567890";

        // создать ордер
        ValidatableResponse orderResponse = orderClient.createOrder(OrderCredentials.from(defaultOrder), accessToken);

        // проверить статус код и боди
        int statusCode = orderResponse.extract().statusCode();
        Assert.assertEquals(403, statusCode);

        String actualMessage = orderResponse.extract().path("message");
        Assert.assertEquals("Incorrect message", "jwt malformed", actualMessage);
    }

    private String getAccessToken() {
        User randomUser = UserGenerator.getRandomUser();
        userClient = new UserClient();
        userClient.register(randomUser);
        accessToken = userClient.login(UserCredentials.from(randomUser)).extract().path("accessToken");
        return accessToken;
    }

}
