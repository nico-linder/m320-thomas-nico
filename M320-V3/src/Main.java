import java.util.Random;

class Main {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Portfolio portfolio = null;
        StockExchange zurich = new ZurichStockExchange();
        StockExchange newYork = new NewYorkStockExchange();

        System.out.println("\n*** WELCOME TO THE PORTFOLIO MANAGEMENT SYSTEM ***");
        System.out.println("Let's get started by creating your portfolio!\n");

        while (portfolio == null) {
            System.out.println("Select stock exchange for your portfolio:");
            System.out.println("1. Zurich Stock Exchange (CHF)");
            System.out.println("2. New York Stock Exchange (USD)");
            System.out.print("Choose exchange: ");
            int exchangeChoice = scanner.nextInt();
            scanner.nextLine();

            if (exchangeChoice == 1) {
                portfolio = new Portfolio(zurich);
                System.out.println("\nPortfolio created successfully with Zurich Stock Exchange!");
            } else if (exchangeChoice == 2) {
                portfolio = new Portfolio(newYork);
                System.out.println("\nPortfolio created successfully with New York Stock Exchange!");
            } else {
                System.out.println("Invalid choice. Please try again.\n");
            }
        }

        while (true) {
            System.out.println("\n=== PORTFOLIO MANAGEMENT SYSTEM ===");
            System.out.println("1. Add stock to portfolio");
            System.out.println("2. Change stock exchange");
            System.out.println("3. View portfolio value");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock name (Microsoft/Apple/Google): ");
                    String stockName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    portfolio.addStock(new Stock(stockName, quantity));
                    System.out.println("Stock added successfully!");
                    break;

                case 2:
                    System.out.println("\nSelect new stock exchange:");
                    System.out.println("1. Zurich (CHF)");
                    System.out.println("2. New York (USD)");
                    System.out.print("Choose exchange: ");
                    int newExchange = scanner.nextInt();
                    scanner.nextLine();

                    if (newExchange == 1) {
                        portfolio.setExchange(zurich);
                        System.out.println("Exchange changed to Zurich");
                    } else if (newExchange == 2) {
                        portfolio.setExchange(newYork);
                        System.out.println("Exchange changed to New York");
                    } else {
                        System.out.println("Invalid choice");
                    }
                    break;

                case 3:
                    System.out.printf("Total portfolio value: %.2f\n", portfolio.getTotalValue());
                    break;

                case 4:
                    try {
                        Random rand  = new Random();
                        int times = rand.nextInt(8) + 1;
                        int amount = rand.nextInt(300, 1000);
                        System.out.print("Exiting");
                        for (int i = 0; i < times; i++) {
                            Thread.sleep(amount);
                            System.out.print(".");
                            amount = rand.nextInt(300, 1000);
                        }
                        scanner.close();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}