package pl.summarizer.codewarriorsproject.Calendar.Event;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;

import java.sql.Time;

@Entity
@Getter
@Service
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private EventType eventType;
    private Time startTime;
    private Time endTime;

    @ManyToOne
    private Week week;
}
