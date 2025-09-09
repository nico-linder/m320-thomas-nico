import java.util.ArrayList;
import java.util.List;

class ShoppingCart {
    private List<CartItem> items;
    private int cartId;

    public ShoppingCart(int cartId) {
        this.cartId = cartId;
        this.items = new ArrayList<>();
    }

    public int getCartId() {
        return cartId;
    }

    
    public boolean addItem(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            
            for (CartItem item : items) {
                if (item.getProduct().getName().equals(product.getName())) {
                    item.increaseQuantity(quantity);
                    return product.reduceStock(quantity);
                }
            }
            
            items.add(new CartItem(product, quantity));
            return product.reduceStock(quantity);
        }
        return false;
    }

    public boolean removeItem(String productName, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(productName)) {
                if (item.getQuantity() <= quantity) {
                    
                    item.getProduct().addStock(item.getQuantity());
                    items.remove(item);
                } else {
                    item.decreaseQuantity(quantity);
                    item.getProduct().addStock(quantity);
                }
                return true;
            }
        }
        return false;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clearCart() {
        
        for (CartItem item : items) {
            item.getProduct().addStock(item.getQuantity());
        }
        items.clear();
    }
}