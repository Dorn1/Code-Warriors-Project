package pl.summarizer.codewarriorsproject.Chatbot;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Chatbot.dto.ChatRequest;


@RestController
@RequestMapping("/chatbot")
@CrossOrigin("*")
public class ChatbotController {

    private ChatClient chatClient;

    public ChatbotController(OllamaChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }
    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> getAnswer(@RequestBody ChatRequest request) {

        ChatResponse chatResponse = chatClient.prompt(request.getMessage()).call().chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response = chatResponse.getResult().getOutput().getText();

        return ResponseEntity.ok(response);
    }
}
