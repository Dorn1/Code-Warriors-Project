package pl.summarizer.codewarriorsproject.Calendar.Event;

import lombok.Getter;
import lombok.Setter;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;

import java.util.Set;

@Getter
@Setter
public class EventSuggestionRequest {
    private Week week;
    private RequestedEvent requestedEvent;
}

