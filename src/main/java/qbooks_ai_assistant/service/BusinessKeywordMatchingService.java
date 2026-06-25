package qbooks_ai_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import qbooks_ai_assistant.entity.BusinessKeyword;

@Service
public class BusinessKeywordMatchingService {

    private final BusinessKeywordService businessKeywordService;

    public BusinessKeywordMatchingService(
            BusinessKeywordService businessKeywordService) {

        this.businessKeywordService = businessKeywordService;
    }

    public BusinessKeyword findMatch(String description) {

        List<BusinessKeyword> keywords = businessKeywordService.getAllKeywords();

        String text = description.toLowerCase();

        for (BusinessKeyword keyword : keywords) {

            if (text.contains(
                    keyword.getKeyword().toLowerCase())) {

                return keyword;
            }
        }

        return null;
    }
}