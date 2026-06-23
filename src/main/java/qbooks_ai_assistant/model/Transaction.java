package qbooks_ai_assistant.model;

public class Transaction {

    private String description;
    private double amount;
    private String category;

    public Transaction(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}