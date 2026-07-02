package qbooks_ai_assistant.quickbooks.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.quickbooks.dto.AccountBasedExpenseLineDetail;
import qbooks_ai_assistant.quickbooks.dto.ExpenseLine;
import qbooks_ai_assistant.quickbooks.dto.ExpenseRequest;
import qbooks_ai_assistant.quickbooks.dto.ReferenceType;

@Component
public class ExpenseMapper {

    public ExpenseRequest toExpenseRequest(Transaction transaction) {

        if (transaction == null) {
            throw new IllegalArgumentException("Transaction may not be null.");
        }

        if (transaction.getChartOfAccount() == null) {
            throw new IllegalArgumentException(
                    "Transaction has no Chart of Account.");
        }

        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException(
                    "Transaction amount may not be null.");
        }

        ChartOfAccount account = transaction.getChartOfAccount();

        String accountId = account.getQuickbooksAccountId();

        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException(
                    "Chart of Account has no QuickBooks Account ID.");
        }

        ExpenseLine line = new ExpenseLine();

        line.setAmount(transaction.getAmount().doubleValue());
        line.setDetailType("AccountBasedExpenseLineDetail");

        ReferenceType accountRef = new ReferenceType(accountId);

        AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();

        detail.setAccountRef(accountRef);

        line.setAccountBasedExpenseLineDetail(detail);

        ExpenseRequest request = new ExpenseRequest();

        request.setLine(List.of(line));

        return request;
    }
}