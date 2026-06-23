package qbooks_ai_assistant.dto;

import java.math.BigDecimal;

public class UnknownTransactionDTO {

    private String date;
    private String description;
    private BigDecimal amount;
    private String bankAccount;
    private String reference;

    public UnknownTransactionDTO() {
    }

    public UnknownTransactionDTO(String date,
            String description,
            BigDecimal amount,
            String bankAccount,
            String reference) {

        this.date = date;
        this.description = description;
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
