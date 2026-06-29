package qbooks_ai_assistant.repository;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;

import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        List<Transaction> findByStatus(String status);

        List<Transaction> findByClientOrderByIdDesc(Client client);

        boolean existsByDescriptionAndAmount(
                        String description,
                        BigDecimal amount);

        Optional<Transaction> findTopByDescriptionOrderByIdDesc(
                        String description);
}
