package qbooks_ai_assistant.dto;

import qbooks_ai_assistant.entity.ChartOfAccount;

public class AccountMatch {

    private ChartOfAccount account;

    private double confidence;

    private String reason;

    public AccountMatch() {
    }

    public AccountMatch(
            ChartOfAccount account,
            double confidence,
            String reason) {

        this.account = account;
        this.confidence = confidence;
        this.reason = reason;
    }

    public ChartOfAccount getAccount() {
        return account;
    }

    public void setAccount(ChartOfAccount account) {
        this.account = account;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
