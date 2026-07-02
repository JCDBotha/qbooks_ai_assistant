package qbooks_ai_assistant.quickbooks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseLine {

    @JsonProperty("Amount")
    private Double amount;

    @JsonProperty("DetailType")
    private String detailType;

    @JsonProperty("AccountBasedExpenseLineDetail")
    private AccountBasedExpenseLineDetail accountBasedExpenseLineDetail;

    public ExpenseLine() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public AccountBasedExpenseLineDetail getAccountBasedExpenseLineDetail() {
        return accountBasedExpenseLineDetail;
    }

    public void setAccountBasedExpenseLineDetail(
            AccountBasedExpenseLineDetail accountBasedExpenseLineDetail) {

        this.accountBasedExpenseLineDetail = accountBasedExpenseLineDetail;
    }
}
