package qbooks_ai_assistant.service;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.quickbooks.dto.ExpenseRequest;
import qbooks_ai_assistant.quickbooks.mapper.ExpenseMapper;

@Service
public class QuickBooksWriteBackService {

    private final QuickBooksSessionService quickBooksSessionService;
    private final QuickBooksApiService quickBooksApiService;
    private final ExpenseMapper expenseMapper;

    public QuickBooksWriteBackService(
            QuickBooksSessionService quickBooksSessionService,
            QuickBooksApiService quickBooksApiService,
            ExpenseMapper expenseMapper) {

        this.quickBooksSessionService = quickBooksSessionService;
        this.quickBooksApiService = quickBooksApiService;
        this.expenseMapper = expenseMapper;
    }

    /**
     * Creates the QuickBooks ExpenseRequest from a Transaction.
     */
    public ExpenseRequest createExpenseRequest(Transaction transaction) {

        return expenseMapper.toExpenseRequest(transaction);
    }

    /**
     * Sends the Expense to QuickBooks.
     */
    public String createExpense(Transaction transaction) {

        String accessToken = quickBooksSessionService.getAccessToken();
        String realmId = quickBooksSessionService.getRealmId();

        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("No QuickBooks access token found.");
        }

        if (realmId == null || realmId.isBlank()) {
            throw new IllegalStateException("No QuickBooks Realm ID found.");
        }

        ExpenseRequest request = createExpenseRequest(transaction);

        return quickBooksApiService.createExpense(
                realmId,
                accessToken,
                request);
    }

}
