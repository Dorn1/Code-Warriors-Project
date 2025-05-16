package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Calendar.Week.WeekRepository;
import pl.summarizer.codewarriorsproject.Exception.ResourceNotFoundException;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final WeekRepository weekRepository;
    private final EventTypeClassifier eventTypeClassifier;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            WeekRepository weekRepository,
            EventTypeClassifier eventTypeClassifier) {
        this.eventRepository = eventRepository;
        this.weekRepository = weekRepository;
        this.eventTypeClassifier = eventTypeClassifier;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public Event createEvent(Event event, Long weekId) {
        Week week = weekRepository.findById(weekId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "weekId", weekId));
        week.getEvents().add(event);
        weekRepository.save(week);

        event.setWeek(week);
        event.setEventType(eventTypeClassifier.classify(event.getTitle(), event.getDescription()));
        return eventRepository.save(event);
    }
}
