package pl.summarizer.codewarriorsproject.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.User.AppUser;
import pl.summarizer.codewarriorsproject.User.AppUserService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CalendarController {
    CalendarService calendarService;
    AppUserService appUserService;

    @Autowired
    public CalendarController(CalendarService calendarService, AppUserService appUserService) {
        this.calendarService = calendarService;
        this.appUserService = appUserService;
    }
    @CrossOrigin
    @PostMapping(value = "/addWeek")
    public ResponseEntity<String> addDay(@RequestParam Long userId, @RequestParam String startDay, @RequestParam String endDay) {
        AppUser user = appUserService.getUser(userId);
        UserCalendar userCalendar = calendarService.getUserCalendar(user);
        calendarService.addWeek(userCalendar, startDay, endDay);
        return ResponseEntity.ok("Added week");
    }

    @CrossOrigin
    @PostMapping(value = "/getEventsByDay")
    public ResponseEntity<List<Event>> getEventsByDay(@RequestParam Long userId, @RequestParam String day) {
        AppUser user = appUserService.getUser(userId);
        UserCalendar userCalendar = calendarService.getUserCalendar(user);
        List<Week> weeks = calendarService.weekRepository.getAllByUser(userCalendar);
        List<Event> events = new LinkedList<>();
        LocalDateTime dayTime = LocalDateTime.parse(day);
        for (Week week : weeks) {
            if (dayTime.isBefore(week.getEndDate())&& dayTime.isAfter(week.getStartDate())) {
                for (Event event : calendarService.getEvents(week)) {
                    if (event.getStartTime().getDayOfYear() <= dayTime.getDayOfYear()
                            && event.getEndTime().getDayOfYear() >= dayTime.getDayOfYear()) {
                        events.add(event);
                    }
                }
            }
        }
        return ResponseEntity.ok(events);
    }
}