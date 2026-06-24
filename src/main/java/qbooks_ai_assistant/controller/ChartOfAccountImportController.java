package qbooks_ai_assistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import qbooks_ai_assistant.service.ChartOfAccountImportService;

@RestController
public class ChartOfAccountImportController {

    private final ChartOfAccountImportService importService;

    public ChartOfAccountImportController(
            ChartOfAccountImportService importService) {

        this.importService = importService;
    }

    @GetMapping("/import-chart-of-accounts")
    public String importAccounts() {

        importService.importWorkbook(
                "C:/dev/qbooks_ai_assistant/data/Combined_Chart_of_Accounts.xlsx");

        return "Chart of Accounts import completed.";
    }
}