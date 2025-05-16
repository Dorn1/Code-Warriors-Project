package pl.summarizer.codewarriorsproject.Calendar.Week;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.summarizer.codewarriorsproject.Calendar.Event.Event;
import pl.summarizer.codewarriorsproject.User.User;

import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;

    @OneToMany
    private Set<Event> events;

    @ManyToOne
    private User user;
}
