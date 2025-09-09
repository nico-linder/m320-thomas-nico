import java.util.List;

public class Main {
    public static void main(String[] args) {
        Supermarket market = new Supermarket("Fresh Market");

        Product milk = new Product("Milk", 1.50, 20, "Dairy");
        Product bread = new Product("Bread", 2.00, 15, "Bakery");
        Product apple = new Product("Apples", 3.00, 30, "Fruits");
        Product cheese = new Product("Cheese", 5.50, 10, "Dairy");

        market.addProduct(milk);
        market.addProduct(bread);
        market.addProduct(apple);
        market.addProduct(cheese);

        market.displayInventory();

        System.out.println("\n=== Shopping Simulation ===");

        ShoppingCart cart = market.createNewCart();
        System.out.println("Created cart ID: " + cart.getCartId());

        System.out.println("\nAdding items to cart:");
        if (cart.addItem(milk, 2)) {
            System.out.println("Added 2x Milk to cart");
        }
        if (cart.addItem(bread, 1)) {
            System.out.println("Added 1x Bread to cart");
        }
        if (cart.addItem(apple, 5)) {
            System.out.println("Added 5x Apples to cart");
        }

        System.out.println("\n=== Cart Contents ===");
        for (CartItem item : cart.getItems()) {
            System.out.println(item);
        }
        System.out.printf("Total: CHF %.2f\n", cart.getTotalPrice());

        market.setSaleOnCategory("Dairy", true);

        System.out.println("\n=== Updated Inventory (after shopping and sale) ===");
        market.displayInventory();

        System.out.printf("\nUpdated cart total with sale prices: CHF %.2f\n",
                cart.getTotalPrice());

    }
}