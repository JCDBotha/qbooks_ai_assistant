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

    public DashboardService(
            TransactionRepository transactionRepository,
            ClientRepository clientRepository,
            ChartOfAccountRepository chartOfAccountRepository,
            BusinessRuleService businessRuleService) {

        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.chartOfAccountRepository = chartOfAccountRepository;
        this.businessRuleService = businessRuleService;
    }

    public List<Transaction> getTransactions() {

        Optional<Client> client = clientRepository.findByCompanyName("Barak Medical");

        if (client.isEmpty()) {
            return Collections.emptyList();
        }

        System.out.println();
        System.out.println("==============================");
        System.out.println("DASHBOARD CLIENT");
        System.out.println("==============================");
        System.out.println("Company  : " + client.get().getCompanyName());
        System.out.println("Realm ID : " + client.get().getRealmId());
        System.out.println("Client ID: " + client.get().getId());

        List<Transaction> transactions = transactionRepository.findByClientOrderByIdDesc(client.get());

        System.out.println("Transactions found: " + transactions.size());

        return transactions;
    }

    public String getCompanyName() {

        Optional<Client> client = clientRepository.findByCompanyName("Barak Medical");

        if (client.isPresent()) {
            return client.get().getCompanyName();
        }

        return "Unknown Company";

    }

    public List<ChartOfAccount> getChartOfAccounts() {

        Optional<Client> client = clientRepository.findByCompanyName("Barak Medical");

        if (client.isEmpty()) {
            return Collections.emptyList();
        }

        return chartOfAccountRepository
                .findByClientAndActiveTrue(client.get());

    }

    public void performAction(DashboardActionDTO dto) {

        if (dto.getAction() == DashboardAction.ACCEPT) {

            Optional<Transaction> transaction = transactionRepository.findById(dto.getTransactionId());

            if (transaction.isPresent()) {

                transaction.get().setStatus("ACCEPTED");

                transactionRepository.save(transaction.get());

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

        Optional<ChartOfAccount> account = chartOfAccountRepository.findById(dto.getAccountId());

        if (account.isEmpty()) {

            System.out.println("Account not found.");
            return;
        }

        transaction.get().setSuggestedCategory(
                account.get().getAccountName());

        transactionRepository.save(transaction.get());

        System.out.println();
        System.out.println("==============================");
        System.out.println("TRANSACTION UPDATED");
        System.out.println("==============================");
        System.out.println("Transaction : " + transaction.get().getId());
        System.out.println("New Account : " + account.get().getAccountName());

    }

}