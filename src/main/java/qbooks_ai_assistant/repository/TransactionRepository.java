package qbooks_ai_assistant.repository;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatus(String status);

    boolean existsByDescriptionAndAmount(
            String description,
            BigDecimal amount);

    Optional<Transaction> findTopByDescriptionOrderByIdDesc(
            String description);
}
