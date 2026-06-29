package qbooks_ai_assistant.dto;

public class AIResultDTO {

    private String description;

    private Double amount;

    private String suggestedAccount;

    private Double confidence;

    private String reason;

    private String matchedBy;

    public AIResultDTO() {
    }

    public AIResultDTO(
            String description,
            Double amount,
            String suggestedAccount,
            Double confidence,
            String reason,
            String matchedBy) {

        this.description = description;
        this.amount = amount;
        this.suggestedAccount = suggestedAccount;
        this.confidence = confidence;
        this.reason = reason;
        this.matchedBy = matchedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSuggestedAccount() {
        return suggestedAccount;
    }

    public void setSuggestedAccount(String suggestedAccount) {
        this.suggestedAccount = suggestedAccount;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMatchedBy() {
        return matchedBy;
    }

    public void setMatchedBy(String matchedBy) {
        this.matchedBy = matchedBy;
    }
}
