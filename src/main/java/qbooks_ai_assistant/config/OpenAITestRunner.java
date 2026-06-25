package qbooks_ai_assistant.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import qbooks_ai_assistant.service.OpenAIService;

@Component
public class OpenAITestRunner implements CommandLineRunner {

    private final OpenAIService openAIService;

    public OpenAITestRunner(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @Override
    public void run(String... args) {

        System.out.println();
        System.out.println("====================================");
        System.out.println("OPENAI CONNECTION TEST");
        System.out.println("====================================");

        String response = openAIService.testConnection();

        System.out.println(response);

        System.out.println("====================================");
    }
}
