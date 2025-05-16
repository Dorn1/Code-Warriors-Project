package pl.summarizer.codewarriorsproject.Calendar;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.Exception.DoesntExistException;
import pl.summarizer.codewarriorsproject.User.AppUser;
import pl.summarizer.codewarriorsproject.User.AppUserService;

import java.util.Calendar;

@RestController
public class CalendarController {
    CalendarService calendarService;
    AppUserService appUserService;
    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "/getCalendar")
    public ResponseEntity<UserCalendar> getCalendar(@RequestParam Long userId) {
        try {
            AppUser user = appUserService.getUser(userId);
            return ResponseEntity.ok().body(calendarService.getUserCalendar(user));
        } catch (DoesntExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
