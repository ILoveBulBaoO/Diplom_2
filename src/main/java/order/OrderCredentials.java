package order;

import java.util.List;

public class OrderCredentials {
    private List<String> ingredients;

    public OrderCredentials(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static OrderCredentials from(Order order) {
        return new OrderCredentials(order.getIngredient());
    }
}
