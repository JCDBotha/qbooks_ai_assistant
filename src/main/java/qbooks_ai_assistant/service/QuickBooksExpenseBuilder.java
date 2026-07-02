package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.quickbooks.dto.AccountBasedExpenseLineDetail;
import qbooks_ai_assistant.quickbooks.dto.ExpenseLine;
import qbooks_ai_assistant.quickbooks.dto.ExpenseRequest;
import qbooks_ai_assistant.quickbooks.dto.ReferenceType;

@Service
public class QuickBooksExpenseBuilder {

    public ExpenseRequest build(Transaction transaction) {

        ExpenseLine line = new ExpenseLine();

        line.setAmount(transaction.getAmount().doubleValue());

        line.setDetailType("AccountBasedExpenseLineDetail");

        AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();

        detail.setAccountRef(
                new ReferenceType(
                        transaction.getChartOfAccount().getQuickbooksAccountId()));

        line.setAccountBasedExpenseLineDetail(detail);

        ExpenseRequest expense = new ExpenseRequest();

        expense.setLine(List.of(line));

        return expense;
    }

}
