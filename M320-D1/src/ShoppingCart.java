import java.util.ArrayList;
import java.util.List;

class ShoppingCart {
    private List<CartItem> items; // Complex data type (List of objects)
    private int cartId; // Primitive data type

    public ShoppingCart(int cartId) {
        this.cartId = cartId;
        this.items = new ArrayList<>();
    }

    public int getCartId() {
        return cartId;
    }

    // Method that communicates with Product objects and passes values
    public boolean addItem(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            // Check if product already in cart
            for (CartItem item : items) {
                if (item.getProduct().getName().equals(product.getName())) {
                    item.increaseQuantity(quantity);
                    return product.reduceStock(quantity); // Object communication
                }
            }
            // Add new item
            items.add(new CartItem(product, quantity));
            return product.reduceStock(quantity); // Object communication with value passing
        }
        return false;
    }

    public boolean removeItem(String productName, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(productName)) {
                if (item.getQuantity() <= quantity) {
                    // Return stock to product
                    item.getProduct().addStock(item.getQuantity()); // Object communication
                    items.remove(item);
                } else {
                    item.decreaseQuantity(quantity);
                    item.getProduct().addStock(quantity); // Object communication
                }
                return true;
            }
        }
        return false;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            // Communication with Product objects to get price
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items); // Return copy to maintain encapsulation
    }

    public void clearCart() {
        // Return all items to stock before clearing
        for (CartItem item : items) {
            item.getProduct().addStock(item.getQuantity()); // Object communication
        }
        items.clear();
    }
}