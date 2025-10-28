class ZurichStockExchange implements StockExchange {
    @Override
    public double getPrice(String stockName) {
        if (stockName.equals("Microsoft")) {
            return 120.0;
        } else if (stockName.equals("Apple")) {
            return 150.0;
        } else if (stockName.equals("Google")) {
            return 2800.0;
        }
        return 0.0;
    }
}