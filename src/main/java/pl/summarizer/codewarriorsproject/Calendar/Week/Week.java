package pl.summarizer.codewarriorsproject.Calendar.Week;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;
import pl.summarizer.codewarriorsproject.User.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany
    private Set<Event> events = new HashSet<>();

    @ManyToOne
    private UserCalendar user;
}
