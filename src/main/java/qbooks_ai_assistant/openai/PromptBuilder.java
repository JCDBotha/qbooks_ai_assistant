package qbooks_ai_assistant.openai;

import java.util.List;

import org.springframework.stereotype.Component;

import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;

@Component
public class PromptBuilder {

    public String buildPrompt(
            UnknownTransactionDTO transaction,
            Client client,
            List<ChartOfAccount> accounts,
            String businessKeyword) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are a senior South African Chartered Accountant.

                You are helping an accounting firm categorise ONLY QuickBooks Online
                "For Review" bank transactions.

                IMPORTANT RULES

                1. You MUST choose ONLY ONE account.
                2. You MUST choose ONLY from the supplied Chart of Accounts.
                3. NEVER invent a new account.
                4. If none are suitable, return UNKNOWN.
                5. Return ONLY valid JSON.

                Expected JSON format:

                {
                  "account":"Account Name",
                  "confidence":95,
                  "reason":"Short explanation"
                }

                ====================================================

                """);

        prompt.append("Company:\n");
        prompt.append(client.getCompanyName());
        prompt.append("\n\n");

        prompt.append("Transaction Description:\n");
        prompt.append(transaction.getDescription());
        prompt.append("\n\n");

        prompt.append("Amount:\n");
        prompt.append(transaction.getAmount());
        prompt.append("\n\n");

        if (businessKeyword != null && !businessKeyword.isBlank()) {

            prompt.append("Business Meaning:\n");
            prompt.append(businessKeyword);
            prompt.append("\n\n");
        }

        prompt.append("Client Chart Of Accounts:\n\n");

        for (ChartOfAccount account : accounts) {

            prompt.append("- ");
            prompt.append(account.getAccountName());
            prompt.append("\n");
        }

        return prompt.toString();
    }
}