package pl.summarizer.codewarriorsproject.Calendar.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
