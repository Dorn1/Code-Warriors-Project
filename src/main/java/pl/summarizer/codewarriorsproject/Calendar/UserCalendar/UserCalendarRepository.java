package pl.summarizer.codewarriorsproject.Calendar.UserCalendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {

}
