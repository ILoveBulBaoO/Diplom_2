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

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreatePositiveTest {

    private UserClient userClient;

    private String accessToken;
    private Order defaultOrder;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        User randomUser = UserGenerator.getRandomUser();
        userClient = new UserClient();
        userClient.register(randomUser);
        accessToken = userClient.login(UserCredentials.from(randomUser)).extract().path("accessToken");

        orderClient = new OrderClient();
        defaultOrder = OrderGenerator.getDefaultOrder();
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }

    // создать заказ c ингредиентами авторизованным юзером
    @DisplayName("Create order with ingredients by login user")
    @Test
    public void loginUserCanCreateOrder() {

        // создать ордер
        ValidatableResponse orderResponse = orderClient.createOrder(OrderCredentials.from(defaultOrder), accessToken);

        // проверить статус код и боди
        int statusCode = orderResponse.extract().statusCode();
        Assert.assertEquals(200, statusCode);

        boolean isOrderCreatedSuccess = orderResponse.extract().path("success");
        Assert.assertTrue(isOrderCreatedSuccess);

        // проверить name и order number не null
        String actualName = orderResponse.extract().path("name");
        Assert.assertNotNull("Name is null", actualName);

        int actualOrderNumber = orderResponse.extract().path("order.number");
        Assert.assertThat(actualOrderNumber, notNullValue());
    }
}
