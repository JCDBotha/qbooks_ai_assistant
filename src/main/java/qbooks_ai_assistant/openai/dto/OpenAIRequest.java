package qbooks_ai_assistant.openai.dto;

public class OpenAIRequest {

    private String prompt;

    public OpenAIRequest() {
    }

    public OpenAIRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
