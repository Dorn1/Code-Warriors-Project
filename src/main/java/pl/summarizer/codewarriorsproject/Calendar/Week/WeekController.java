package pl.summarizer.codewarriorsproject.Calendar.Week;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.Event.EventType;

import java.util.HashMap;
import java.util.Set;

@RestController
public class WeekController {

    private final WeekService weekService;

    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    @GetMapping("/balance-suggestions")
    public ResponseEntity<HashMap<EventType, String>> getWeekBalanceSuggestions(@RequestBody Set<Event> events) {
        HashMap<EventType, String> weekBalanceSuggestions = weekService.getWeekBalanceSuggestions(events);
        return new ResponseEntity<>(weekBalanceSuggestions, HttpStatus.OK);
    }
}
