package qbooks_ai_assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.service.UnknownTransactionService;

@RestController
@RequestMapping("/api/unknown-transactions")
public class UnknownTransactionController {

    private final UnknownTransactionService unknownTransactionService;

    public UnknownTransactionController(
            UnknownTransactionService unknownTransactionService) {

        this.unknownTransactionService = unknownTransactionService;
    }

    @PostMapping
    public String receiveUnknownTransactions(
            @RequestBody List<UnknownTransactionDTO> transactions) {

        System.out.println("====================================");
        System.out.println("UNKNOWN TRANSACTIONS RECEIVED");
        System.out.println("====================================");

        for (UnknownTransactionDTO transaction : transactions) {

            System.out.println("Date        : " + transaction.getDate());
            System.out.println("Description : " + transaction.getDescription());
            System.out.println("Amount      : " + transaction.getAmount());
            System.out.println("Bank        : " + transaction.getBankAccount());
            System.out.println("Reference   : " + transaction.getReference());
            System.out.println("------------------------------------");
        }

        unknownTransactionService.process(transactions);

        return "Received " + transactions.size() + " unknown transactions.";
    }

}
