package orderTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderGetNegativeTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    // нельзя получить заказ неавторизованным пользователем
    @DisplayName("Can not get order unLogin user")
    @Test
    public void canNotGetOrderUnloginUser() {
        String accessToken = "";

        // получить ордер
        ValidatableResponse getOrderResponse = orderClient.getOrder(accessToken);

        // проверить статус код и боди
        int statusCode = getOrderResponse.extract().statusCode();
        Assert.assertEquals(401, statusCode);

        boolean isGetOrderSuccess = getOrderResponse.extract().path("success");
        Assert.assertFalse(isGetOrderSuccess);

        // проверить сообщение
        String actualMessage = getOrderResponse.extract().path("message");
        Assert.assertEquals("Incorrect message", "You should be authorised", actualMessage);
    }
}
