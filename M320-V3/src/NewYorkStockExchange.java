class NewYorkStockExchange implements StockExchange {
    @Override
    public double getPrice(String stockName) {
        if (stockName.equals("Microsoft")) {
            return 100.0;
        } else if (stockName.equals("Apple")) {
            return 125.0;
        } else if (stockName.equals("Google")) {
            return 2500.0;
        }
        return 0.0;
    }
}