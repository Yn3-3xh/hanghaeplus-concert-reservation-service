package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.infra.concert.jpa.ConcertDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertDetailRepositoryImpl implements ConcertDetailRepository {

    private final ConcertDetailJpaRepository concertDetailJpaRepository;

    @Override
    public List<LocalDate> selectConcertAvailableDates(Long concertId) {
        return concertDetailJpaRepository.findAvailableDatesByConcertId(concertId);
    }
}
