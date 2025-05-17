package pl.summarizer.codewarriorsproject.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama")
@CrossOrigin("*")
public class OllamaController {

    private OllamaService ollamaService;

    @Autowired
    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping("/{message}")
    public ResponseEntity<String> getAnswerFromString(@PathVariable String message){
        String answer = ollamaService.getAnswer(message);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

}