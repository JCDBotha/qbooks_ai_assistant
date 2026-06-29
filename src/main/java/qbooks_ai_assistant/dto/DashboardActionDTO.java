package qbooks_ai_assistant.dto;

public class DashboardActionDTO {

    private Long transactionId;

    private Long accountId;

    private DashboardAction action;

    public DashboardActionDTO() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public DashboardAction getAction() {
        return action;
    }

    public void setAction(DashboardAction action) {
        this.action = action;
    }
}
