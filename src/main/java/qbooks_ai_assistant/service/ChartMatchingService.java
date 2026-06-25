package qbooks_ai_assistant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.dto.AccountMatch;
import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.entity.ChartOfAccount;

@Service
public class ChartMatchingService {

    public List<AccountMatch> findMatches(
            UnknownTransactionDTO transaction,
            List<ChartOfAccount> accounts) {

        List<AccountMatch> matches = new ArrayList<>();

        String description = transaction.getDescription().toLowerCase();

        for (ChartOfAccount account : accounts) {

            String accountName = account.getAccountName().toLowerCase();

            if (description.contains(accountName)
                    || accountName.contains(description)) {

                matches.add(
                        new AccountMatch(
                                account,
                                100,
                                "Exact text match"));
            }
        }

        return matches;
    }

}
