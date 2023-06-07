package order;

import java.util.List;

public class Order {
    private List<String> ingredient;

    public Order() {

    }

    public Order(List<String> ingredient) {
        this.ingredient = ingredient;
    }

    public List<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }
}
