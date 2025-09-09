class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (quantity > amount) {
            quantity -= amount;
        }
    }

    @Override
    public String toString() {
        return String.format("%s x%d = CHF %.2f",
                product.getName(), quantity,
                product.getPrice() * quantity);
    }
}