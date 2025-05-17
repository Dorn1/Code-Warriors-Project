package pl.summarizer.codewarriorsproject.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
    private final ChatClient chatClient;

    public OllamaService(OllamaChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String getAnswer(String message) {
        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        return chatResponse.getResult().getOutput().getText();
    }
}
