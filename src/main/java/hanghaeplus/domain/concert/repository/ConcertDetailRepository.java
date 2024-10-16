package hanghaeplus.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

public interface ConcertDetailRepository {

    List<LocalDate> selectConcertAvailableDates(Long concertId);
}
