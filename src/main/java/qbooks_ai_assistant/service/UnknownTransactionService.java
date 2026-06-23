package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;

@Service
public class UnknownTransactionService {

    public void process(List<UnknownTransactionDTO> transactions) {

        System.out.println();
        System.out.println("Processing " + transactions.size() + " unknown transactions...");

    }

}
