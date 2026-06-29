package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.AccountMatch;
import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.BusinessKeyword;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.openai.OpenAIService;
import qbooks_ai_assistant.openai.dto.AISuggestion;
import java.util.Optional;

@Service
public class MatchingService {

        private final ChartMatchingService chartMatchingService;
        private final BusinessKeywordMatchingService businessKeywordMatchingService;
        private final OpenAIService openAIService;
        private final AIValidationService aiValidationService;
        private final BusinessRuleMatchingService businessRuleMatchingService;

        public MatchingService(
                        ChartMatchingService chartMatchingService,
                        BusinessKeywordMatchingService businessKeywordMatchingService,
                        OpenAIService openAIService,
                        AIValidationService aiValidationService,
                        BusinessRuleMatchingService businessRuleMatchingService) {

                this.chartMatchingService = chartMatchingService;
                this.businessKeywordMatchingService = businessKeywordMatchingService;
                this.openAIService = openAIService;
                this.aiValidationService = aiValidationService;
                this.businessRuleMatchingService = businessRuleMatchingService;

        }

        public AccountMatch findBestMatch(
                        UnknownTransactionDTO transaction,
                        Client client,
                        List<ChartOfAccount> accounts) {

                System.out.println();
                System.out.println("==============================");
                System.out.println("MATCHING ENGINE");
                System.out.println("==============================");

                // STEP 1 - Business Rules

                Optional<ChartOfAccount> businessRule = businessRuleMatchingService.findRule(
                                client,
                                transaction.getDescription());

                if (businessRule.isPresent()) {

                        System.out.println();
                        System.out.println("==============================");
                        System.out.println("BUSINESS RULE APPLIED");
                        System.out.println("==============================");
                        System.out.println("Account : "
                                        + businessRule.get().getAccountName());

                        return new AccountMatch(
                                        businessRule.get(),
                                        100,
                                        "Business Rule");
                }

                // STEP 2 - Business Keyword
                BusinessKeyword keyword = businessKeywordMatchingService.findMatch(
                                transaction.getDescription());

                if (keyword != null) {

                        System.out.println();
                        System.out.println("BUSINESS KEYWORD FOUND");
                        System.out.println("----------------------");
                        System.out.println("Keyword : " + keyword.getKeyword());
                        System.out.println("Meaning : " + keyword.getMeaning());

                } else {

                        System.out.println();
                        System.out.println("No Business Keyword found.");
                }

                // STEP 3 - Chart Matching
                List<AccountMatch> matches = chartMatchingService.findMatches(
                                transaction,
                                accounts);

                if (!matches.isEmpty()) {

                        System.out.println();
                        System.out.println("Chart Match Found");
                        System.out.println("Suggested Account : "
                                        + matches.get(0).getAccount().getAccountName());

                        return matches.get(0);
                }

                System.out.println();
                System.out.println("No Chart Match Found.");

                // STEP 4 - OpenAI
                AISuggestion rawSuggestion = openAIService.suggestAccounts(
                                transaction,
                                client,
                                accounts,
                                keyword != null ? keyword.getMeaning() : "");

                AISuggestion aiSuggestion = aiValidationService.validate(
                                rawSuggestion,
                                accounts);

                System.out.println();
                System.out.println("==============================");
                System.out.println("AI SUGGESTION");
                System.out.println("==============================");
                System.out.println("Account     : " + aiSuggestion.getAccount());
                System.out.println("Confidence  : " + aiSuggestion.getConfidence());
                System.out.println("Reason      : " + aiSuggestion.getReason());

                ChartOfAccount selectedAccount = accounts.stream()
                                .filter(account -> account.getAccountName().equalsIgnoreCase(
                                                aiSuggestion.getAccount()))
                                .findFirst()
                                .orElse(null);

                return new AccountMatch(
                                selectedAccount,
                                aiSuggestion.getConfidence(),
                                aiSuggestion.getReason());
        }
}