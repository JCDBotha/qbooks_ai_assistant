package qbooks_ai_assistant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.AccountMatch;
import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.repository.ClientRepository;
import qbooks_ai_assistant.repository.TransactionRepository;

@Service
public class UnknownTransactionService {

        private final TransactionRepository transactionRepository;
        private final ClientRepository clientRepository;
        private final ChartOfAccountService chartOfAccountService;
        private final MatchingService matchingService;
        private final QuickBooksSessionService quickBooksSessionService;

        public UnknownTransactionService(
                        TransactionRepository transactionRepository,
                        ClientRepository clientRepository,
                        ChartOfAccountService chartOfAccountService,
                        MatchingService matchingService,
                        QuickBooksSessionService quickBooksSessionService) {

                this.transactionRepository = transactionRepository;
                this.clientRepository = clientRepository;
                this.chartOfAccountService = chartOfAccountService;
                this.matchingService = matchingService;
                this.quickBooksSessionService = quickBooksSessionService;
        }

        public void process(
                        String companyName,
                        List<UnknownTransactionDTO> transactions) {

                quickBooksSessionService.setCompanyName(companyName);
                System.out.println("SESSION COMPANY SET TO: " + quickBooksSessionService.getCompanyName());

                System.out.println();
                System.out.println("Processing "
                                + transactions.size()
                                + " unknown transactions...");

                Client client = clientRepository
                                .findAll()
                                .stream()
                                .filter(c -> companyName.contains(c.getCompanyName())
                                                || c.getCompanyName().contains(companyName))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException(
                                                "Client not found: " + companyName));

                System.out.println();
                System.out.println("=================================");
                System.out.println("CLIENT FOUND");
                System.out.println("=================================");
                System.out.println(client.getCompanyName());

                List<ChartOfAccount> accounts = chartOfAccountService.getAccountsForClient(client);

                System.out.println();
                System.out.println("=================================");
                System.out.println("CLIENT CHART OF ACCOUNTS");
                System.out.println("=================================");
                System.out.println("Accounts loaded: " + accounts.size());

                accounts.forEach(account -> System.out.println(account.getAccountName()));

                for (UnknownTransactionDTO dto : transactions) {

                        boolean exists = transactionRepository.existsByDescriptionAndAmount(
                                        dto.getDescription(),
                                        dto.getAmount());

                        if (exists) {

                                System.out.println(
                                                "Skipping duplicate: "
                                                                + dto.getDescription());

                                continue;
                        }

                        AccountMatch match = matchingService.findBestMatch(
                                        dto,
                                        client,
                                        accounts);

                        Transaction transaction = new Transaction();

                        transaction.setClient(client);
                        transaction.setDescription(dto.getDescription());
                        transaction.setAmount(dto.getAmount());
                        transaction.setStatus("PENDING");
                        transaction.setTransactionDate(LocalDate.now());

                        if (match.getAccount() != null) {

                                transaction.setChartOfAccount(
                                                match.getAccount());

                        }

                        transactionRepository.save(transaction);

                        System.out.println();
                        System.out.println("---------------------------------");
                        System.out.println("TRANSACTION SAVED");
                        System.out.println("---------------------------------");
                        System.out.println("Client      : " + client.getCompanyName());
                        System.out.println("Description : " + dto.getDescription());
                        System.out.println("Suggestion  : "
                                        + (transaction.getChartOfAccount() != null
                                                        ? transaction.getChartOfAccount().getAccountName()
                                                        : "UNKNOWN"));
                        System.out.println("Confidence  : "
                                        + match.getConfidence());
                        System.out.println("Reason      : "
                                        + match.getReason());
                }
        }
}