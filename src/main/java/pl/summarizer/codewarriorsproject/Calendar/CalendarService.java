package pl.summarizer.codewarriorsproject.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.Event.EventRepository;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendarRepository;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Calendar.Week.WeekRepository;
import pl.summarizer.codewarriorsproject.Exception.TimeCollisionException;
import pl.summarizer.codewarriorsproject.User.AppUser;
import pl.summarizer.codewarriorsproject.User.AppUserService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class CalendarService {
    EventRepository eventRepository;
    WeekRepository weekRepository;
    UserCalendarRepository userCalendarRepository;
    AppUserService userService;
    @Autowired
    public CalendarService(EventRepository eventRepository, WeekRepository weekRepository, UserCalendarRepository userCalendarRepository, AppUserService userService) {
        this.eventRepository = eventRepository;
        this.weekRepository = weekRepository;
        this.userCalendarRepository = userCalendarRepository;
        this.userService = userService;
    }

    public void addEvent(Week week,
                         String title,
                         String description,
                         String startTime,
                         String endTime) throws TimeCollisionException {
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
    public void addWeek(UserCalendar userCalendar,
                        String startTime,
                        String endTime){
        Week week = new Week();
        LocalDateTime startTimer = LocalDateTime.parse(startTime);
        LocalDateTime endTimer = LocalDateTime.parse(endTime);
        week.setStartDate(startTimer);
        week.setEndDate(endTimer);
        week.setUser(userCalendar);
        weekRepository.save(week);

    }
    public Set<Event> getEvents(Week week){
        return week.getEvents();
    }
    public Set<Week> getWeeks(UserCalendar userCalendar){
        return userCalendar.getWeekSet();
    }
    public UserCalendar getUserCalendar(AppUser user){
        if(userCalendarRepository.getUserCalendarByUser(user).isEmpty()){
            UserCalendar userCalendar = new UserCalendar();
            userCalendar.setUser(user);
            userCalendarRepository.save(userCalendar);
        }
        return userCalendarRepository.getUserCalendarByUser(user).stream().toList().getFirst();
    }
}
