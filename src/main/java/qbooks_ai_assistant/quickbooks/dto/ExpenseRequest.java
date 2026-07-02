package qbooks_ai_assistant.quickbooks.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseRequest {

    @JsonProperty("Line")
    private List<ExpenseLine> line;

    public ExpenseRequest() {
    }

    public List<ExpenseLine> getLine() {
        return line;
    }

    public void setLine(List<ExpenseLine> line) {
        this.line = line;
    }
}