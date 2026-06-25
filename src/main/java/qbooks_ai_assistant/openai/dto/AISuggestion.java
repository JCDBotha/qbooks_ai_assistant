package qbooks_ai_assistant.openai.dto;

public class AISuggestion {

    private String account;

    private int confidence;

    private String reason;

    private boolean valid;

    public AISuggestion() {
    }

    public AISuggestion(
            String account,
            int confidence,
            String reason,
            boolean valid) {

        this.account = account;
        this.confidence = confidence;
        this.reason = reason;
        this.valid = valid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
