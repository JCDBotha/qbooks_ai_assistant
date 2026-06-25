package qbooks_ai_assistant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.BusinessKeyword;
import qbooks_ai_assistant.repository.BusinessKeywordRepository;

@Service
public class BusinessKeywordService {

    private final BusinessKeywordRepository repository;

    public BusinessKeywordService(
            BusinessKeywordRepository repository) {

        this.repository = repository;
    }

    public List<BusinessKeyword> getAllKeywords() {

        return repository.findByActiveTrue();
    }

    public Optional<BusinessKeyword> findKeyword(
            String keyword) {

        return repository.findByKeywordIgnoreCase(
                keyword);
    }

    public BusinessKeyword save(
            BusinessKeyword keyword) {

        return repository.save(keyword);
    }

}
