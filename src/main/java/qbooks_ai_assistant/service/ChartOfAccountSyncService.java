package qbooks_ai_assistant.service;

import org.springframework.stereotype.Service;

@Service
public class ChartOfAccountSyncService {

    private final QuickBooksApiService quickBooksApiService;

    public ChartOfAccountSyncService(
            QuickBooksApiService quickBooksApiService) {

        this.quickBooksApiService = quickBooksApiService;
    }

    public void downloadChartOfAccounts(
            String realmId,
            String accessToken) {

        String json = quickBooksApiService.getChartOfAccounts(
                realmId,
                accessToken);

        System.out.println();
        System.out.println("======================================");
        System.out.println("CHART OF ACCOUNTS JSON");
        System.out.println("======================================");
        System.out.println(json);
        System.out.println("======================================");
    }
}
