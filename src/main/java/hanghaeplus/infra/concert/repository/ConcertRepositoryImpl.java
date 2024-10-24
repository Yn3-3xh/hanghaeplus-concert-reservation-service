package hanghaeplus.infra.concert.repository;

import hanghaeplus.domain.concert.entity.Concert;
import hanghaeplus.domain.concert.repository.ConcertRepository;
import hanghaeplus.infra.concert.jpa.ConcertJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public void insertConcert(Concert concert) {
        concertJpaRepository.save(concert);
    }
}
