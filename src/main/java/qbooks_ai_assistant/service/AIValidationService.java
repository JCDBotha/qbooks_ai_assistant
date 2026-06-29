package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.openai.dto.AISuggestion;

@Service
public class AIValidationService {

    public AISuggestion validate(
            AISuggestion suggestion,
            List<ChartOfAccount> accounts) {

        if (suggestion == null) {
            return null;
        }

        for (ChartOfAccount account : accounts) {

            if (account.getAccountName()
                    .equalsIgnoreCase(suggestion.getAccount())) {

                System.out.println();
                System.out.println("==============================");
                System.out.println("AI VALIDATION");
                System.out.println("==============================");
                System.out.println("VALID ACCOUNT FOUND");
                System.out.println(account.getAccountName());

                return suggestion;
            }
        }

        System.out.println();
        System.out.println("==============================");
        System.out.println("AI VALIDATION");
        System.out.println("==============================");
        System.out.println("INVALID ACCOUNT");
        System.out.println(suggestion.getAccount());

        suggestion.setAccount("UNKNOWN");
        suggestion.setConfidence(0);
        suggestion.setReason(
                "AI selected an account that does not exist in this client's Chart of Accounts.");

        return suggestion;
    }
}
