package qbooks_ai_assistant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.BusinessRule;
import qbooks_ai_assistant.entity.Client;

public interface BusinessRuleRepository
        extends JpaRepository<BusinessRule, Long> {

    Optional<BusinessRule> findByClientAndKeywordIgnoreCaseAndActiveTrue(
            Client client,
            String keyword);

}
