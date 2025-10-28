class Portfolio {
    private java.util.List<Stock> stocks;
    private StockExchange exchange;

    public Portfolio(StockExchange exchange) {
        this.stocks = new java.util.ArrayList<>();
        this.exchange = exchange;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0.0;
        for (Stock stock : stocks) {
            double price = exchange.getPrice(stock.getName());
            total += price * stock.getQuantity();
        }
        return total;
    }

    public void setExchange(StockExchange exchange) {
        this.exchange = exchange;
    }
}