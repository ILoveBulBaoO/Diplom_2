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

public class OrderGetPositiveTest {

    private String accessToken;
    private OrderClient orderClient;
    private UserClient userClient;

    @Before
    public void setUp() {
        User randomUser = UserGenerator.getRandomUser();
        userClient = new UserClient();
        userClient.register(randomUser);
        accessToken = userClient.login(UserCredentials.from(randomUser)).extract().path("accessToken");

        orderClient = new OrderClient();
        Order defaultOrder = OrderGenerator.getDefaultOrder();
        orderClient.createOrder(OrderCredentials.from(defaultOrder), accessToken);
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    // получить заказ
    @DisplayName("Get user order")
    @Test
    public void getUserOrder() {

        // получить ордер
        ValidatableResponse getOrderResponse = orderClient.getOrder(accessToken);

        // проверить статус код и боди
        int statusCode = getOrderResponse.extract().statusCode();
        Assert.assertEquals(200, statusCode);

        boolean isGetOrderSuccess = getOrderResponse.extract().path("success");
        Assert.assertTrue(isGetOrderSuccess);

    }

}
