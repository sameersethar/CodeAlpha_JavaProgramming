package Task2_StockPlatform;

import java.io.*;
import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}

class Transaction {
    String stockSymbol;
    int quantity;
    boolean isBuy;
    double price;

    public Transaction(String stockSymbol, int quantity, boolean isBuy, double price) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.isBuy = isBuy;
        this.price = price;
    }

    public String toString() {
        return (isBuy ? "BUY " : "SELL ") + quantity + " of " + stockSymbol + " @ " + price;
    }
}

class User {
    String name;
    Map<String, Integer> portfolio = new HashMap<>();
    List<Transaction> transactionHistory = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void buyStock(Stock stock, int quantity) {
        portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
        transactionHistory.add(new Transaction(stock.symbol, quantity, true, stock.price));
    }

    public void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (quantity <= owned) {
            portfolio.put(stock.symbol, owned - quantity);
            transactionHistory.add(new Transaction(stock.symbol, quantity, false, stock.price));
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    public void showPortfolio(Map<String, Stock> market) {
        double totalValue = 0;
        System.out.println(name + "'s Portfolio:");
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            double price = market.get(symbol).price;
            totalValue += qty * price;
            System.out.println(symbol + ": " + qty + " shares @ $" + price + " = $" + (qty * price));
        }
        System.out.println("Total Value: $" + totalValue);
    }

    public void showHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }
}

public class StockTradingPlatform {
    static Map<String, Stock> market = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample market stocks
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOG", new Stock("GOOG", 2700));
        market.put("TSLA", new Stock("TSLA", 700));

        User user = new User("Alice");

        while (true) {
            System.out.println("\n1. View Market\n2. Buy Stock\n3. Sell Stock\n4. View Portfolio\n5. View History\n6. Save to File\n7. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarket();
                    break;
                case 2:
                    tradeStock(user, true);
                    break;
                case 3:
                    tradeStock(user, false);
                    break;
                case 4:
                    user.showPortfolio(market);
                    break;
                case 5:
                    user.showHistory();
                    break;
                case 6:
                    saveToFile(user);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void viewMarket() {
        System.out.println("Current Market:");
        for (Stock stock : market.values()) {
            System.out.println(stock.symbol + ": $" + stock.price);
        }
    }

    static void tradeStock(User user, boolean isBuy) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        if (isBuy) {
            user.buyStock(stock, quantity);
        } else {
            user.sellStock(stock, quantity);
        }
    }

    // Optional File I/O Feature
    static void saveToFile(User user) {
        try (PrintWriter writer = new PrintWriter("portfolio.txt")) {
            writer.println("User: " + user.name);
            for (Map.Entry<String, Integer> entry : user.portfolio.entrySet()) {
                writer.println(entry.getKey() + " " + entry.getValue());
            }
            writer.println("Transactions:");
            for (Transaction t : user.transactionHistory) {
                writer.println(t);
            }
            System.out.println("Portfolio saved to portfolio.txt");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
