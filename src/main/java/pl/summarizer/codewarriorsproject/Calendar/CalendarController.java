package pl.summarizer.codewarriorsproject.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    CalendarService calendarService;
    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
}
