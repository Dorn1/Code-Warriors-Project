package pl.summarizer.codewarriorsproject.Calendar.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @JsonIgnore
    private Week week;
}
