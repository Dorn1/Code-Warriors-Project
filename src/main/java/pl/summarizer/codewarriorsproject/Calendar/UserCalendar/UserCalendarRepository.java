package pl.summarizer.codewarriorsproject.Calendar.UserCalendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.summarizer.codewarriorsproject.User.User;

import java.util.Set;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {

    Set<UserCalendar> getUserCalendarByUser(User user);
}
