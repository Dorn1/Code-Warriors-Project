package pl.summarizer.codewarriorsproject.Calendar.Event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private EventType eventType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    private Week week;
}
