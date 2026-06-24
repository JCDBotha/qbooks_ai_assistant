package qbooks_ai_assistant.controller;

import org.springframework.web.bind.annotation.*;

import qbooks_ai_assistant.dto.UnknownTransactionRequest;
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
            @RequestBody UnknownTransactionRequest request) {

        System.out.println("====================================");
        System.out.println("UNKNOWN TRANSACTIONS RECEIVED");
        System.out.println("====================================");

        System.out.println(
                "COMPANY: "
                        + request.getCompanyName());

        System.out.println(
                "TRANSACTIONS: "
                        + request.getTransactions().size());

        unknownTransactionService.process(
                request.getCompanyName(),
                request.getTransactions());

        return "Received "
                + request.getTransactions().size()
                + " unknown transactions.";
    }
}
