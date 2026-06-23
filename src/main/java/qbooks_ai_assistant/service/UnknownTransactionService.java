package qbooks_ai_assistant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.Transaction;
import qbooks_ai_assistant.repository.TransactionRepository;

@Service
public class UnknownTransactionService {

    private final TransactionRepository transactionRepository;

    public UnknownTransactionService(
            TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    public void process(List<UnknownTransactionDTO> transactions) {

        System.out.println();
        System.out.println("Processing "
                + transactions.size()
                + " unknown transactions...");

        for (UnknownTransactionDTO dto : transactions) {

            Transaction transaction = new Transaction();

            transaction.setDescription(dto.getDescription());

            transaction.setAmount(dto.getAmount());

            transaction.setStatus("PENDING");

            transaction.setSuggestedCategory("UNKNOWN");

            transaction.setTransactionDate(LocalDate.now());

            transactionRepository.save(transaction);

            System.out.println(
                    "Saved transaction: "
                            + dto.getDescription());
        }
    }
}