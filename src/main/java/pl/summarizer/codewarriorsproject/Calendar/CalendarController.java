package pl.summarizer.codewarriorsproject.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;
import pl.summarizer.codewarriorsproject.Exception.TimeCollisionException;
import pl.summarizer.codewarriorsproject.User.AppUser;
import pl.summarizer.codewarriorsproject.User.AppUserService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        try {
            AppUser user = appUserService.getUser(userId);
            UserCalendar userCalendar = calendarService.getUserCalendar(user);
            calendarService.addWeek(userCalendar, startDay, endDay);
            return ResponseEntity.ok("Added week");
        } catch (DoesntExistException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping(value = "/addEvent")
    public ResponseEntity<String> addEvent(@RequestParam Long userId, @RequestParam String start, @RequestParam String end, @RequestParam String description, @RequestParam String title) {
        try {
            AppUser user = appUserService.getUser(userId);
            UserCalendar userCalendar = calendarService.getUserCalendar(user);
            List<Week> weeks = userCalendar.getWeekSet().stream().toList();
            Week week = weeks.getFirst();
            calendarService.addEvent(week, title, description, start, end);
            return ResponseEntity.ok("Added event");
        } catch (DoesntExistException | TimeCollisionException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getDay")
    public ResponseEntity<java.util.List<Event>> getEvents(@RequestParam Long userId, @RequestParam String day) {
        return ResponseEntity.ok(calendarService.eventRepository.getAllByStartTimeAfter(LocalDateTime.parse("2025-01-01T00:00:01")).stream().toList());
    }
}


