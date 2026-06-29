package qbooks_ai_assistant.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.BusinessRule;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.repository.BusinessRuleRepository;

@Service
public class BusinessRuleService {

    private final BusinessRuleRepository businessRuleRepository;

    public BusinessRuleService(
            BusinessRuleRepository businessRuleRepository) {

        this.businessRuleRepository = businessRuleRepository;
    }

    public void saveRule(
            Client client,
            String keyword,
            ChartOfAccount account) {

        Optional<BusinessRule> existingRule = businessRuleRepository
                .findByClientAndKeywordIgnoreCaseAndActiveTrue(
                        client,
                        keyword);

        BusinessRule rule;

        if (existingRule.isPresent()) {

            rule = existingRule.get();

        } else {

            rule = new BusinessRule();

            rule.setClient(client);

            rule.setKeyword(keyword);

        }

        rule.setChartOfAccount(account);

        rule.setActive(true);

        businessRuleRepository.save(rule);

        System.out.println();
        System.out.println("==============================");
        System.out.println("BUSINESS RULE SAVED");
        System.out.println("==============================");
        System.out.println("Client  : " + client.getCompanyName());
        System.out.println("Keyword : " + keyword);
        System.out.println("Account : " + account.getAccountName());
    }
}