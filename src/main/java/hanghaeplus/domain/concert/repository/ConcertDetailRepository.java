package hanghaeplus.domain.concert.repository;

import hanghaeplus.domain.concert.entity.ConcertDetail;

import java.time.LocalDate;
import java.util.List;

public interface ConcertDetailRepository {

    List<LocalDate> selectConcertAvailableDates(Long concertId);

    void saveConcertDetail(ConcertDetail concertDetail);
}
