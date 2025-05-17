package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Calendar.Week.WeekRepository;
import pl.summarizer.codewarriorsproject.Exception.ResourceNotFoundException;
import pl.summarizer.codewarriorsproject.ai.OllamaService;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final WeekRepository weekRepository;
    private final EventTypeClassifier eventTypeClassifier;
    private final OllamaService ollamaService;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            WeekRepository weekRepository,
            EventTypeClassifier eventTypeClassifier,
            OllamaService ollamaService) {
        this.eventRepository = eventRepository;
        this.weekRepository = weekRepository;
        this.eventTypeClassifier = eventTypeClassifier;
        this.ollamaService = ollamaService;
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

    public String makeSchedulerPrompt(EventSuggestionRequest request) {
        Week week = request.getWeek();
        RequestedEvent requestedEvent = request.getRequestedEvent();
        StringBuilder sb = new StringBuilder();
        sb.append("Here is the user's current schedule for the week:\n");
        sb.append("Week starts: ").append(week.getStartDate()).append("\n");
        sb.append("Week ends  : ").append(week.getEndDate()).append("\n\n");

        sb.append("Existing events:\n");
        for (Event e : week.getEvents().stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .toList()) {
            sb.append("- ")
                    .append(e.getTitle())
                    .append(" [").append(e.getEventType()).append("] ")
                    .append(e.getStartTime()).append(" → ").append(e.getEndTime())
                    .append("\n");
        }

        sb.append("\nUser requests:\n");
            sb.append("Event: ").append(requestedEvent.getTitle())
                    .append(" \nOccurrences: ").append(requestedEvent.getOccurrences())
                    .append(" \nDuration: ").append(formatDuration(requestedEvent.getNeededDuration()))
                    .append(" \nBefore ").append(requestedEvent.getDeadline())
                    .append("\n");

        sb.append(
                "\nInstruction:\n" +
                        "You are a friendly planning assistant. Based on the existing events above, " +
                        "find free time slots in this week that fit each request. " +
                        "For each requested task, suggest specific start times (in ISO‑8601 or `EEE MMM d HH:mm` format). " +
                        "If no slot is available, politely say so. " +
                        "Return a short, list of available time slots for the requested event " +
                        "Based on the number of occurrences so propose at least as many available time slots as many there are occurrences" +
                        "List only the time slots and info that you found them\n"
        );

        System.out.println(sb.toString());

        return ollamaService.getAnswer(sb.toString());
    }

    public String formatDuration(Duration duration) {
        if(duration != null) {
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();

            StringBuilder sb = new StringBuilder();
            if (hours > 0) {
                sb.append(hours).append("h");
            }
            if (minutes > 0) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(minutes).append("min");
            }
            return sb.toString();
        }
        return "";
    }


    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "eventId", eventId));

        Week week = event.getWeek();
        event.setWeek(null);
        eventRepository.save(event);
        week.getEvents().remove(event);
        weekRepository.save(week);
        eventRepository.delete(event);
    }
}
