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
                4. The returned account name MUST exactly match one account from the supplied Chart of Accounts.
                5. If none of the supplied Chart of Accounts are suitable, return:

                {
                  "account":"UNKNOWN",
                  "confidence":0,
                  "reason":"No suitable account found."
                }

                6. Return ONLY valid JSON.
                7. DO NOT wrap the JSON inside ```json or ``` blocks.
                8. Return plain JSON only.
                9. Do not explain your answer outside the JSON.
                10. The transaction amount is important. Use both the description and amount when making your decision.

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

        prompt.append("Transaction Amount:\n");
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

        prompt.append("""

                ====================================================

                Think carefully before answering.

                Return ONLY one JSON object.

                Never explain your answer outside the JSON.

                The account MUST exactly match one account from the supplied Chart of Accounts.

                Do not abbreviate account names.

                Do not create new account names.

                If unsure, return UNKNOWN.
                """);

        return prompt.toString();
    }
}