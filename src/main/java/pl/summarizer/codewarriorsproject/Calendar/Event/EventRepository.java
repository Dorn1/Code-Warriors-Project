package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.summarizer.codewarriorsproject.Calendar.Week.Week;

import java.time.LocalDateTime;
import java.util.Set;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Set<Event> getAllByWeek(Week week);

    Set<Event> getAllByStartTimeAfter(LocalDateTime startDate);
}
