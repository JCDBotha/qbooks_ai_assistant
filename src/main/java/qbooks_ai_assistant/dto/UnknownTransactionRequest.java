package qbooks_ai_assistant.dto;

import java.util.List;

public class UnknownTransactionRequest {

    private String companyName;

    private List<UnknownTransactionDTO> transactions;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<UnknownTransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(
            List<UnknownTransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
