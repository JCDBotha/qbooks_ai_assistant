package qbooks_ai_assistant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import qbooks_ai_assistant.dto.DashboardActionDTO;
import qbooks_ai_assistant.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "companyName",
                dashboardService.getCompanyName());

        model.addAttribute(
                "transactions",
                dashboardService.getTransactions());

        model.addAttribute(
                "accounts",
                dashboardService.getChartOfAccounts());

        return "dashboard";
    }

    @PostMapping("/dashboard/action")
    public ResponseEntity<String> dashboardAction(
            @RequestBody DashboardActionDTO dto) {

        System.out.println();
        System.out.println("==============================");
        System.out.println("CONTROLLER REACHED");
        System.out.println("==============================");
        System.out.println("Transaction : " + dto.getTransactionId());
        System.out.println("Account     : " + dto.getAccountId());
        System.out.println("Action      : " + dto.getAction());

        dashboardService.performAction(dto);

        return ResponseEntity.ok("Success");
    }

}
