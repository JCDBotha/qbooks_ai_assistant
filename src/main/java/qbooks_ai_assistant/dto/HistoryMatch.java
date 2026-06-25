package qbooks_ai_assistant.dto;

public class HistoryMatch {

    private String accountName;
    private double confidence;
    private boolean found;

    public HistoryMatch() {
    }

    public HistoryMatch(
            String accountName,
            double confidence,
            boolean found) {

        this.accountName = accountName;
        this.confidence = confidence;
        this.found = found;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getConfidence() {
        return confidence;
    }

    public boolean isFound() {
        return found;
    }
}
