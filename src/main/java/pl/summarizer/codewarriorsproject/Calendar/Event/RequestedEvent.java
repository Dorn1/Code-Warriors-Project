package pl.summarizer.codewarriorsproject.Calendar.Event;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestedEvent {
    private String title;
    private Duration neededDuration;
    private int occurrences;
    private LocalDateTime deadline;
}
