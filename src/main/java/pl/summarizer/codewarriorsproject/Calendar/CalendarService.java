package pl.summarizer.codewarriorsproject.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.Event.EventRepository;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendarRepository;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Calendar.Week.WeekRepository;
import pl.summarizer.codewarriorsproject.Exception.TimeCollisionException;
import pl.summarizer.codewarriorsproject.User.UserService;

import java.time.LocalDateTime;

@Service
public class CalendarService {
    EventRepository eventRepository;
    WeekRepository weekRepository;
    UserCalendarRepository userCalendarRepository;
    UserService userService;
    @Autowired
    public CalendarService(EventRepository eventRepository, WeekRepository weekRepository, UserCalendarRepository userCalendarRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.weekRepository = weekRepository;
        this.userCalendarRepository = userCalendarRepository;
        this.userService = userService;
    }

    public void addEvent(Week week,
                         String title,
                         String description,
                         String startTime,
                         String endTime){
        LocalDateTime startTimer = LocalDateTime.parse(startTime);
        LocalDateTime endTimer = LocalDateTime.parse(endTime);
        for (Event event1 : week.getEvents()) {
            if((startTimer.isBefore(event1.getEndTime()) && startTimer.isAfter(event1.getStartTime()))
            || (startTimer.isAfter(event1.getStartTime()) && endTimer.isBefore(event1.getEndTime()))){
                throw new TimeCollisionException("Collision with "+ event1.getTitle());
            }
        }
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(startTimer);
        event.setEndTime(endTimer);
        eventRepository.save(event);
    }
}
