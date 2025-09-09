import java.util.ArrayList;
import java.util.List;

class Supermarket {
    private String name;
    private List<Product> inventory;
    private List<ShoppingCart> activeCarts;
    private int nextCartId;

    public Supermarket(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.activeCarts = new ArrayList<>();
        this.nextCartId = 1;
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public Product findProduct(String productName) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    public ShoppingCart createNewCart() {
        ShoppingCart cart = new ShoppingCart(nextCartId++);
        activeCarts.add(cart);
        return cart;
    }

    public void displayInventory() {
        System.out.println("\n=== " + name + " Inventory ===");
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    public void setSaleOnCategory(String category, boolean onSale) {
        System.out.println("\nSetting sale status for category: " + category);
        for (Product product : inventory) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                product.setOnSale(onSale);
                System.out.println("Updated: " + product.getName());
            }
        }
    }
}