package qbooks_ai_assistant.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import qbooks_ai_assistant.quickbooks.dto.ExpenseRequest;

@Service
public class QuickBooksApiService {

        private final WebClient webClient = WebClient.builder()
                        .baseUrl("https://sandbox-quickbooks.api.intuit.com")
                        .build();

        public String getCompanyInfo(String realmId, String accessToken) {

                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/v3/company/" + realmId + "/companyinfo/1")
                                                .build())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .header(HttpHeaders.ACCEPT, "application/json")
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }

        public String getTransactions(String realmId, String accessToken) {

                String query = "SELECT * FROM Customer MAXRESULTS 10";

                System.out.println("QB QUERY: " + query);

                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("https")
                                                .host("sandbox-quickbooks.api.intuit.com")
                                                .path("/v3/company/" + realmId + "/query")
                                                .queryParam(
                                                                "query",
                                                                java.net.URLEncoder.encode(
                                                                                query,
                                                                                java.nio.charset.StandardCharsets.UTF_8))
                                                .queryParam("minorversion", 75)
                                                .build())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .header(HttpHeaders.ACCEPT, "application/json")
                                .retrieve()
                                .onStatus(
                                                status -> status.isError(),
                                                response -> response.bodyToMono(String.class)
                                                                .doOnNext(body -> System.out.println(
                                                                                "🔥 QB ERROR RESPONSE: " + body))
                                                                .map(RuntimeException::new))
                                .bodyToMono(String.class)
                                .block();
        }

        public String getChartOfAccounts(String realmId, String accessToken) {

                String query = "SELECT * FROM Account";

                System.out.println("QB QUERY: " + query);

                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("https")
                                                .host("sandbox-quickbooks.api.intuit.com")
                                                .path("/v3/company/" + realmId + "/query")
                                                .queryParam(
                                                                "query",
                                                                java.net.URLEncoder.encode(
                                                                                query,
                                                                                java.nio.charset.StandardCharsets.UTF_8))
                                                .queryParam("minorversion", 75)
                                                .build())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .header(HttpHeaders.ACCEPT, "application/json")
                                .retrieve()
                                .onStatus(
                                                status -> status.isError(),
                                                response -> response.bodyToMono(String.class)
                                                                .doOnNext(body -> System.out.println(
                                                                                "🔥 QB ERROR RESPONSE: " + body))
                                                                .map(RuntimeException::new))
                                .bodyToMono(String.class)
                                .block();
        }

        public String createExpense(
                        String realmId,
                        String accessToken,
                        ExpenseRequest expenseRequest) {

                System.out.println("Creating QuickBooks Expense...");

                return webClient.post()
                                .uri("/v3/company/" + realmId + "/expense")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .header(HttpHeaders.ACCEPT, "application/json")
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .bodyValue(expenseRequest)
                                .retrieve()
                                .onStatus(
                                                status -> status.isError(),
                                                response -> response.bodyToMono(String.class)
                                                                .doOnNext(body -> System.out.println(
                                                                                "🔥 QB ERROR RESPONSE: " + body))
                                                                .map(RuntimeException::new))
                                .bodyToMono(String.class)
                                .block();
        }

}
