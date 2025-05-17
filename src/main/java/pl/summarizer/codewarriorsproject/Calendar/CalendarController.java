package pl.summarizer.codewarriorsproject.Calendar;

import org.apache.catalina.User;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;
import pl.summarizer.codewarriorsproject.User.AppUser;
import pl.summarizer.codewarriorsproject.User.AppUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Set;

@RestController
public class CalendarController {
    CalendarService calendarService;
    AppUserService appUserService;
    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "/getDay")
    public ResponseEntity<java.util.List<Event>> getEventsInDay(@RequestParam Long userId, @RequestParam String day) {
        try {
            AppUser user = appUserService.getUser(userId);
            UserCalendar userCalendar = calendarService.getUserCalendar(user);
            LinkedList<Event> eventList = new LinkedList<>();
            for(Week week : userCalendar.getWeekSet()){
                for (Event event : week.getEvents()){
                    if (event.getStartTime().getDayOfYear() == LocalDateTime.parse(day).getDayOfYear() && event.getStartTime().getYear() == LocalDateTime.parse(day).getYear()){
                        eventList.add(event);
                    }
                }
            }
            return ResponseEntity.ok(eventList);
        } catch (DoesntExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
