package pl.summarizer.codewarriorsproject.Calendar.Week;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.Event.EventType;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/weeks")
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

    @PostMapping
    public ResponseEntity<Week> createWeek(@RequestBody Week week) {
        Week createdWeek = weekService.createWeek(week);
        return new ResponseEntity<>(createdWeek, HttpStatus.CREATED);
    }
}
