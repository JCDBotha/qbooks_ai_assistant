package qbooks_ai_assistant.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import qbooks_ai_assistant.service.BusinessKeywordImportService;

@Component
public class BusinessKeywordImportController
        implements CommandLineRunner {

    private final BusinessKeywordImportService service;

    public BusinessKeywordImportController(
            BusinessKeywordImportService service) {

        this.service = service;
    }

    @Override
    public void run(String... args)
            throws Exception {

        service.importExcel(
                "C:/dev/qbooks_ai_assistant/data/Business_Keywords.xlsx");
    }

}
