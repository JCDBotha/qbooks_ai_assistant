package qbooks_ai_assistant.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.repository.ClientRepository;
import qbooks_ai_assistant.repository.TransactionRepository;
import qbooks_ai_assistant.repository.ChartOfAccountRepository;
import qbooks_ai_assistant.dto.DashboardAction;
import qbooks_ai_assistant.dto.DashboardActionDTO;

@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final ChartOfAccountRepository chartOfAccountRepository;
    private final BusinessRuleService businessRuleService;
    private final QuickBooksSessionService quickBooksSessionService;
    private final QuickBooksWriteBackService quickBooksWriteBackService;

    public DashboardService(
            TransactionRepository transactionRepository,
            ClientRepository clientRepository,
            ChartOfAccountRepository chartOfAccountRepository,
            BusinessRuleService businessRuleService,
            QuickBooksSessionService quickBooksSessionService,
            QuickBooksWriteBackService quickBooksWriteBackService) {

        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.chartOfAccountRepository = chartOfAccountRepository;
        this.businessRuleService = businessRuleService;
        this.quickBooksSessionService = quickBooksSessionService;
        this.quickBooksWriteBackService = quickBooksWriteBackService;
    }

    public org.springframework.data.domain.Page<Transaction> getTransactions(int page) {

        Client client = getCurrentClient();

        System.out.println();
        System.out.println("==============================");
        System.out.println("DASHBOARD CLIENT");
        System.out.println("==============================");
        System.out.println("Company  : " + client.getCompanyName());
        System.out.println("Realm ID : " + client.getRealmId());
        System.out.println("Client ID: " + client.getId());

        return transactionRepository.findByClient(
                client,
                org.springframework.data.domain.PageRequest.of(page, 10));

    }

    public String getCompanyName() {

        return getCurrentClient().getCompanyName();

    }

    public List<ChartOfAccount> getChartOfAccounts() {

        return chartOfAccountRepository
                .findByClientAndActiveTrue(getCurrentClient());

    }

    public void performAction(DashboardActionDTO dto) {

        if (dto.getAction() == DashboardAction.ACCEPT
                || dto.getAction() == DashboardAction.ONCE) {

            Optional<Transaction> transaction = transactionRepository.findById(dto.getTransactionId());

            if (transaction.isPresent()) {

                try {

                    quickBooksWriteBackService.createExpense(transaction.get());

                    transaction.get().setStatus("ACCEPTED");

                    transactionRepository.save(transaction.get());

                } catch (Exception ex) {

                    ex.printStackTrace();

                    throw new RuntimeException(
                            "Failed to write transaction back to QuickBooks.");

                }

                System.out.println();
                System.out.println("==============================");
                System.out.println("TRANSACTION ACCEPTED");
                System.out.println("==============================");
                System.out.println("Transaction : " + transaction.get().getId());

            }

            return;
        }

        Optional<Transaction> transaction = transactionRepository.findById(dto.getTransactionId());

        if (transaction.isEmpty()) {

            System.out.println("Transaction not found.");
            return;
        }

        ChartOfAccount account;

        if (dto.getAction() == DashboardAction.ALWAYS) {

            account = transaction.get().getChartOfAccount();

            if (account == null) {

                System.out.println("Transaction has no Chart of Account.");
                return;
            }

        } else {

            Optional<ChartOfAccount> selectedAccount = chartOfAccountRepository.findById(dto.getAccountId());

            if (selectedAccount.isEmpty()) {

                System.out.println("Account not found.");
                return;
            }

            account = selectedAccount.get();
        }

        transaction.get().setChartOfAccount(account);

        transactionRepository.save(transaction.get());

        System.out.println();
        System.out.println("==============================");
        System.out.println("TRANSACTION UPDATED");
        System.out.println("==============================");
        System.out.println("Transaction : " + transaction.get().getId());
        System.out.println("New Account : " + account.getAccountName());

        if (dto.getAction() == DashboardAction.ALWAYS) {

            businessRuleService.saveRule(
                    transaction.get().getClient(),
                    transaction.get().getDescription(),
                    account);

            transaction.get().setChartOfAccount(account);
            transaction.get().setStatus("ACCEPTED");

            transactionRepository.save(transaction.get());

            System.out.println();
            System.out.println("==============================");
            System.out.println("BUSINESS RULE CREATED");
            System.out.println("==============================");
            System.out.println("Description : " + transaction.get().getDescription());
            System.out.println("Account     : " + account.getAccountName());

            return;
        }

    }

    public void acceptSelected(List<Long> transactionIds) {

        for (Long id : transactionIds) {

            Optional<Transaction> transaction = transactionRepository.findById(id);

            if (transaction.isPresent()) {

                transaction.get().setStatus("ACCEPTED");

                transactionRepository.save(transaction.get());

                System.out.println("Accepted transaction: " + id);

            }

        }

    }

    private Client getCurrentClient() {

        String companyName = quickBooksSessionService.getCompanyName();
        System.out.println("SESSION COMPANY IS: " + quickBooksSessionService.getCompanyName());

        System.out.println("Current Company: " + companyName);

        return clientRepository
                .findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Client not found: " + companyName));

    }

    public void rememberAll(List<Long> transactionIds) {

        for (Long id : transactionIds) {

            Optional<Transaction> transaction = transactionRepository.findById(id);

            if (transaction.isPresent()) {

                Transaction t = transaction.get();

                ChartOfAccount account = t.getChartOfAccount();

                if (account != null) {

                    businessRuleService.saveRule(
                            t.getClient(),
                            t.getDescription(),
                            account);

                    t.setStatus("ACCEPTED");
                    transactionRepository.save(t);

                    System.out.println("Rule created: " + t.getDescription());

                }

            }

        }

    }
}