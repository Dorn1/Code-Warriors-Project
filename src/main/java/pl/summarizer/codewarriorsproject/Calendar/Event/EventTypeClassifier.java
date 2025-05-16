package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class EventTypeClassifier {

    private final ChatClient chatClient;

    public EventTypeClassifier(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public EventType classify(String title, String description) {
        String prompt = buildPrompt(title, description);

        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        String result = response.getResult().getOutput().getText().trim().toUpperCase();

        try {
            return EventType.valueOf(result);
        } catch (IllegalArgumentException e) {
            return EventType.OTHER;
        }
    }

    private String buildPrompt(String title, String description) {
        return """
                You need to classify an event into one of these categories:
                - PHYSICAL_ACTIVITY
                - CHILL
                - SELF_IMPROVEMENT
                - WORK
                - SOCIAL_ACTIVITY
                - OTHER
                
                Classify the following event by returning only one category name.
                
                Title: %s
                Description: %s
                """.formatted(title, description);
    }
}
