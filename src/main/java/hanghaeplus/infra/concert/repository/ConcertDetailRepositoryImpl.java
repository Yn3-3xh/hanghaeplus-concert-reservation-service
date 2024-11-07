package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.infra.concert.jpa.ConcertDetailJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertDetailRepositoryImpl implements ConcertDetailRepository {

    private final ConcertDetailJpaRepository concertDetailJpaRepository;

    @Override
    public List<LocalDate> selectConcertAvailableDates(Long concertId) {
        return concertDetailJpaRepository.findAvailableDatesByConcertId(concertId).stream()
                .map(Date::toLocalDate)
                .toList();
    }

    @Override
    public void saveConcertDetail(ConcertDetail concertDetail) {
        concertDetailJpaRepository.save(concertDetail);
    }

    @Override
    public Optional<ConcertDetail> findById(Long concertDetailId) {
        return concertDetailJpaRepository.findById(concertDetailId);
    }
}
