package order;

import java.util.Collections;
import java.util.List;

public class OrderGenerator {

    // дефолтный ордер со списком ингердиентов
    public static Order getDefaultOrder() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"));
    }

    // ордер с некорректными ингредиентами
    public static Order incorrectOrder() {
        return new Order(List.of("12345", "fffff", "asd123"));
    }

    // ордер с пустым списком ингредиентов
    public static Order orderWithoutIngredients() {
        return new Order(Collections.EMPTY_LIST);
    }
}
