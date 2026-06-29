package qbooks_ai_assistant.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.BusinessRule;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.repository.BusinessRuleRepository;

@Service
public class BusinessRuleMatchingService {

    private final BusinessRuleRepository businessRuleRepository;
    private final BusinessKeywordMatchingService businessKeywordMatchingService;

    public BusinessRuleMatchingService(
            BusinessRuleRepository businessRuleRepository,
            BusinessKeywordMatchingService businessKeywordMatchingService) {

        this.businessRuleRepository = businessRuleRepository;
        this.businessKeywordMatchingService = businessKeywordMatchingService;
    }

    public Optional<ChartOfAccount> findRule(
            Client client,
            String description) {

        var keyword = businessKeywordMatchingService.findMatch(description);

        if (keyword == null) {

            return Optional.empty();
        }

        Optional<BusinessRule> rule = businessRuleRepository
                .findByClientAndKeywordIgnoreCaseAndActiveTrue(
                        client,
                        keyword.getKeyword());

        if (rule.isPresent()) {

            System.out.println();
            System.out.println("==============================");
            System.out.println("BUSINESS RULE MATCH");
            System.out.println("==============================");
            System.out.println("Keyword : " + keyword.getKeyword());
            System.out.println("Account : "
                    + rule.get().getChartOfAccount().getAccountName());

            return Optional.of(rule.get().getChartOfAccount());
        }

        return Optional.empty();
    }

}
