package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event, Long weekId) {
        Event createdEvent = eventService.createEvent(event, weekId);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<Event> deleteEvent(@RequestParam Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/suggest-event-time")
    public ResponseEntity<String> getSuggestedEventTime(
            @RequestBody EventSuggestionRequest eventSuggestionRequest) {
        String suggestedEventTime = eventService.makeSchedulerPrompt(eventSuggestionRequest);
        return new ResponseEntity<>(suggestedEventTime, HttpStatus.OK);
    }
}
