package qbooks_ai_assistant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import qbooks_ai_assistant.entity.BusinessKeyword;

public interface BusinessKeywordRepository
                extends JpaRepository<BusinessKeyword, Long> {

        Optional<BusinessKeyword> findByKeywordIgnoreCase(
                        String keyword);

        boolean existsByKeywordIgnoreCase(String keyword);

        List<BusinessKeyword> findByActiveTrue();

}
