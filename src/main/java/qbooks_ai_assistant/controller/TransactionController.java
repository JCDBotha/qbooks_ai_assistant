package qbooks_ai_assistant.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.dto.PredictionResponseDTO;

@CrossOrigin(origins = "https://qbo.intuit.com")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @PostMapping("/predict")
    public List<PredictionResponseDTO> predictCategories(
            @RequestBody List<UnknownTransactionDTO> transactions) {

        System.out.println("🔥 /predict endpoint hit");
        System.out.println("Received transactions: " + transactions.size());

        List<PredictionResponseDTO> results = new ArrayList<>();

        for (UnknownTransactionDTO tx : transactions) {
            results.add(new PredictionResponseDTO(tx.getDescription(), 0.0));
        }

        return results;
    }

}
