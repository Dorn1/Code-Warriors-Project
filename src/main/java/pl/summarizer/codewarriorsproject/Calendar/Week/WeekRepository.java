package pl.summarizer.codewarriorsproject.Calendar.Week;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
}
