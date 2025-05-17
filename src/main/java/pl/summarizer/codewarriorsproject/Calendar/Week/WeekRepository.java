package pl.summarizer.codewarriorsproject.Calendar.Week;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.summarizer.codewarriorsproject.Calendar.UserCalendar.UserCalendar;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    Set<Week> getAllByStartDateAfter(LocalDateTime parse);
}
