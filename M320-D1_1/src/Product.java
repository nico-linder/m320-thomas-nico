class Product {
    private String name;
    private double price;
    private int stock;
    private String category;
    private boolean isOnSale;

    public Product(String name, double price, int stock, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.isOnSale = false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return isOnSale ? price * 0.8 : price;
    }

    public double getOriginalPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    
    public boolean reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    public void addStock(int quantity) {
        stock += quantity;
    }

    public void setOnSale(boolean onSale) {
        this.isOnSale = onSale;
    }

    public void updatePrice(double newPrice) {
        if (newPrice > 0) {
            this.price = newPrice;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - CHF %.2f%s (Stock: %d)",
                name, getPrice(),
                isOnSale ? " [ON SALE]" : "", stock);
    }
}
