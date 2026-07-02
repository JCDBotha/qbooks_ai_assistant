
package qbooks_ai_assistant.quickbooks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBasedExpenseLineDetail {

    @JsonProperty("AccountRef")
    private ReferenceType accountRef;

    public AccountBasedExpenseLineDetail() {
    }

    public ReferenceType getAccountRef() {
        return accountRef;
    }

    public void setAccountRef(ReferenceType accountRef) {
        this.accountRef = accountRef;
    }
}