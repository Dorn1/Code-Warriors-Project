package pl.summarizer.codewarriorsproject.Calendar.Week;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.Event.EventType;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class WeekService {

    private final WeekRepository weekRepository;

    @Autowired
    public WeekService(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    public HashMap<EventType, String> getWeekBalanceSuggestions(Set<Event> events) {
        HashMap<EventType, Duration> weekBalances = initWeekBalances();

        for (Event event : events) {
            Duration duration = Duration.between(event.getStartTime(), event.getEndTime());
            if (duration != null) {
                weekBalances.computeIfPresent(
                        event.getEventType(),
                        (k, currentDuration) -> currentDuration.plus(duration));
            }
        }

        HashMap<EventType, String> eventInbalances = checkWeekBalanceSuggestion(weekBalances);

        return eventInbalances;

    }

    public HashMap<EventType, Duration>  initWeekBalances() {
        HashMap<EventType, Duration> weekBalances = new HashMap<>();
        List<EventType> eventTypes = List.of(EventType.values());
        for (EventType eventType : eventTypes) {
            weekBalances.put(eventType, Duration.ZERO);
        }
        return weekBalances;
    }

    public HashMap<EventType, String> checkWeekBalanceSuggestion(
            HashMap<EventType, Duration> weekBalances) {
        HashMap<EventType, String> eventInbalances = new HashMap<>();
        weekBalances.forEach((eventType, duration) -> {
            Long hours = duration.toHours();
            switch (eventType) {
                case EventType.PHYSICAL_ACTIVITY:
                    if (hours < 2) {
                        eventInbalances.put(eventType, "A little movement can go a long way. Consider adding some physical activity to your week for a boost in energy and mood!");
                    }
                    else if (hours > 10) {
                        eventInbalances.put(eventType, "Impressive dedication to staying active! Just ensure you're also giving your body ample time to rest and recover.");
                    }
                    break;
                case EventType.CHILL:
                    if (hours < 10) {
                        eventInbalances.put(eventType, "Remember to carve out some time to relax and unwind. Your well-being benefits from moments of calm.");
                    }
                    else if (hours > 20) {
                        eventInbalances.put(eventType, "It's great to see you taking time to relax. Balance is key—ensure you're also engaging in activities that develop you.");
                    }
                    break;
                case EventType.HOBBY:
                    if (hours < 5) {
                        eventInbalances.put(eventType, "Engaging in hobbies can be a delightful way to recharge. Perhaps explore activities that bring you joy this week.");
                    }
                    else if (hours > 20) {
                        eventInbalances.put(eventType, "Your passion is evident! Just be mindful to balance your hobby time with other aspects of your life to maintain overall well-being.");
                    }
                    break;
                case EventType.WORK:
                    if (hours < 30) {
                        eventInbalances.put(eventType, "It seems you've had a lighter workweek. Hopefully, this provided an opportunity to recharge and focus on other fulfilling activities.");
                    }
                    if(hours > 45) {
                        eventInbalances.put(eventType, "You've been putting in significant work hours. Remember to take breaks and prioritize self-care to sustain your productivity and health.");
                    }
                    break;
                case EventType.SOCIAL_ACTIVITY:
                    if (hours < 1) {
                        eventInbalances.put(eventType, "Connecting with others can uplift your spirits. Consider reaching out to friends or family to share some quality time.");
                    }
                    break;
        }
        });

        return eventInbalances;
    }

    public Week createWeek(Week week) {
        return weekRepository.save(week);
    }
}
