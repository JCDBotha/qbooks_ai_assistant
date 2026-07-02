package qbooks_ai_assistant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import qbooks_ai_assistant.dto.DashboardAction;
import qbooks_ai_assistant.dto.DashboardActionDTO;
import qbooks_ai_assistant.service.DashboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import qbooks_ai_assistant.entity.Transaction;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public String dashboard(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        model.addAttribute(
                "companyName",
                dashboardService.getCompanyName());

        org.springframework.data.domain.Page<Transaction> transactionPage = dashboardService.getTransactions(page);

        model.addAttribute(
                "transactions",
                transactionPage.getContent());

        model.addAttribute(
                "currentPage",
                transactionPage.getNumber());

        model.addAttribute(
                "totalPages",
                transactionPage.getTotalPages());

        model.addAttribute(
                "totalTransactions",
                transactionPage.getTotalElements());

        model.addAttribute(
                "accounts",
                dashboardService.getChartOfAccounts());

        return "dashboard";
    }

    @PostMapping("/action")
    public ResponseEntity<String> dashboardAction(
            @RequestBody DashboardActionDTO dto) {

        System.out.println("================================");
        System.out.println("ACTION RECEIVED: " + dto.getAction());
        System.out.println("Transaction ID : " + dto.getTransactionId());
        System.out.println("Account ID     : " + dto.getAccountId());
        System.out.println("================================");

        dashboardService.performAction(dto);

        if (dto.getAction() == DashboardAction.ALWAYS) {

            return ResponseEntity.ok("✅ Business Rule created successfully.");

        } else {

            return ResponseEntity.ok("✅ Transaction saved successfully.");

        }
    }

    @PostMapping("/accept-selected")
    @ResponseBody
    public String acceptSelected(@RequestBody List<Long> transactionIds) {

        dashboardService.acceptSelected(transactionIds);

        return "Accepted";

    }

    @PostMapping("/remember-selected")
    @ResponseBody
    public String rememberSelected(@RequestBody List<Long> transactionIds) {

        dashboardService.rememberAll(transactionIds);

        return "Rules Created";
    }
}
