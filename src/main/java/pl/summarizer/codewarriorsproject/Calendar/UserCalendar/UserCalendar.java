package pl.summarizer.codewarriorsproject.Calendar.UserCalendar;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;
import pl.summarizer.codewarriorsproject.User.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class UserCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToMany
    private Set<Week> weekSet = new HashSet<>();
}
