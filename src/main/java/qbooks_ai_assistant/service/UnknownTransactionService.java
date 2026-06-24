package qbooks_ai_assistant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.repository.ClientRepository;
import qbooks_ai_assistant.repository.TransactionRepository;

@Service
public class UnknownTransactionService {

        private final TransactionRepository transactionRepository;
        private final ClientRepository clientRepository;

        public UnknownTransactionService(
                        TransactionRepository transactionRepository,
                        ClientRepository clientRepository) {

                this.transactionRepository = transactionRepository;
                this.clientRepository = clientRepository;
        }

        public void process(String companyName, List<UnknownTransactionDTO> transactions) {

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

                        Transaction transaction = new Transaction();

                        transaction.setClient(client);

                        transaction.setDescription(dto.getDescription());

                        transaction.setAmount(dto.getAmount());

                        transaction.setStatus("PENDING");

                        transaction.setSuggestedCategory("UNKNOWN");

                        transaction.setTransactionDate(LocalDate.now());

                        transactionRepository.save(transaction);

                        System.out.println(
                                        "Saved transaction for client: "
                                                        + client.getCompanyName()
                                                        + " -> "
                                                        + dto.getDescription());
                }
        }
}