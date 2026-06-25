package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.AccountMatch;
import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.BusinessKeyword;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;

@Service
public class MatchingService {

        private final ChartMatchingService chartMatchingService;
        private final BusinessKeywordMatchingService businessKeywordMatchingService;

        public MatchingService(
                        ChartMatchingService chartMatchingService,
                        BusinessKeywordMatchingService businessKeywordMatchingService) {

                this.chartMatchingService = chartMatchingService;
                this.businessKeywordMatchingService = businessKeywordMatchingService;
        }

        public AccountMatch findBestMatch(
                        UnknownTransactionDTO transaction,
                        Client client,
                        List<ChartOfAccount> accounts) {

                System.out.println();
                System.out.println("==============================");
                System.out.println("MATCHING ENGINE");
                System.out.println("==============================");

                // STEP 1 - Find Business Keyword
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

                // STEP 2 - Chart Matching
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

                return new AccountMatch(
                                null,
                                0,
                                "No match");
        }

}