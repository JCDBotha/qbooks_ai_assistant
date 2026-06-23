package qbooks_ai_assistant.controller;

import qbooks_ai_assistant.config.QuickBooksConfig;
import qbooks_ai_assistant.service.QuickBooksService;
import qbooks_ai_assistant.service.QuickBooksApiService;
import qbooks_ai_assistant.dto.QuickBooksTokenResponse;
import qbooks_ai_assistant.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final QuickBooksConfig quickBooksConfig;
    private final QuickBooksService quickBooksService;
    private final QuickBooksApiService quickBooksApiService;

    public HomeController(QuickBooksConfig quickBooksConfig,
            QuickBooksService quickBooksService,
            QuickBooksApiService quickBooksApiService) {
        this.quickBooksConfig = quickBooksConfig;
        this.quickBooksService = quickBooksService;
        this.quickBooksApiService = quickBooksApiService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("companies", 12);
        model.addAttribute("transactions", 347);
        model.addAttribute("status", "Running");

        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(new Transaction("Checkers", 850.00, "Groceries"));
        transactionList.add(new Transaction("Eskom", 1200.00, "Utilities"));
        transactionList.add(new Transaction("Shell Fuel", 900.00, "Transport"));

        model.addAttribute("transactionList", transactionList);

        return "home";
    }

    @GetMapping("/connect")
    public String connect() {

        String state = java.util.UUID.randomUUID().toString();

        String redirectUri = java.net.URLEncoder.encode(
                quickBooksConfig.getRedirectUri(),
                java.nio.charset.StandardCharsets.UTF_8);

        String authUrl = "https://appcenter.intuit.com/connect/oauth2" +
                "?client_id=" + quickBooksConfig.getClientId() +
                "&response_type=code" +
                "&scope=com.intuit.quickbooks.accounting" +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        return "redirect:" + authUrl;
    }

    @GetMapping("/callback")
    public String callback(
            @org.springframework.web.bind.annotation.RequestParam("code") String code,
            @org.springframework.web.bind.annotation.RequestParam("realmId") String realmId,
            Model model) {

        System.out.println("Authorization Code: " + code);
        System.out.println("Realm ID: " + realmId);

        // 1. Exchange code for token
        QuickBooksTokenResponse tokenResponse = quickBooksService.exchangeCodeForToken(code);

        if (tokenResponse == null) {
            throw new RuntimeException("Token response is null - QuickBooks auth failed");
        }

        System.out.println("TOKEN RESPONSE: " + tokenResponse);

        String accessToken = tokenResponse.getAccess_token();

        System.out.println("ACCESS TOKEN: " + accessToken);

        // 2. Call QuickBooks API
        String companyInfo = quickBooksApiService.getCompanyInfo(realmId, accessToken);

        String transactions = quickBooksApiService.getTransactions(realmId, accessToken);

        System.out.println("COMPANY INFO: " + companyInfo);
        System.out.println("TRANSACTIONS: " + transactions);

        // 3. Send to UI
        model.addAttribute("authCode", code);
        model.addAttribute("realmId", realmId);
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("companyInfo", companyInfo);

        return "home";
    }

}