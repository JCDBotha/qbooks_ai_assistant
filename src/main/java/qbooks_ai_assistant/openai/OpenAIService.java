package qbooks_ai_assistant.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import qbooks_ai_assistant.entity.ChartOfAccount;
import qbooks_ai_assistant.entity.Client;
import qbooks_ai_assistant.dto.UnknownTransactionDTO;
import qbooks_ai_assistant.openai.dto.AISuggestion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class OpenAIService {

        @Value("${openai.api.key}")
        private String apiKey;

        @Value("${openai.model}")
        private String model;

        private final PromptBuilder promptBuilder;

        private final ObjectMapper mapper = new ObjectMapper();

        public OpenAIService(PromptBuilder promptBuilder) {
                this.promptBuilder = promptBuilder;
        }

        public AISuggestion suggestAccounts(
                        UnknownTransactionDTO transaction,
                        Client client,
                        List<ChartOfAccount> accounts,
                        String businessKeyword) {

                try {

                        String prompt = promptBuilder.buildPrompt(
                                        transaction,
                                        client,
                                        accounts,
                                        businessKeyword);

                        String body = """
                                        {
                                          "model":"%s",
                                          "input":%s
                                        }
                                        """.formatted(
                                        model,
                                        mapper.writeValueAsString(prompt));

                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create("https://api.openai.com/v1/responses"))
                                        .header("Authorization", "Bearer " + apiKey)
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(body))
                                        .build();

                        HttpClient httpClient = HttpClient.newHttpClient();

                        HttpResponse<String> response = httpClient.send(
                                        request,
                                        HttpResponse.BodyHandlers.ofString());

                        JsonNode root = mapper.readTree(response.body());

                        String json = root.path("output")
                                        .get(0)
                                        .path("content")
                                        .get(0)
                                        .path("text")
                                        .asText();

                        json = json.replace("```json", "");
                        json = json.replace("```", "");
                        json = json.trim();

                        System.out.println();
                        System.out.println("========== AI RAW JSON ==========");
                        System.out.println(json);
                        System.out.println("=================================");

                        return mapper.readValue(
                                        json,
                                        AISuggestion.class);

                } catch (IOException | InterruptedException e) {

                        throw new RuntimeException(e);
                }
        }

}
