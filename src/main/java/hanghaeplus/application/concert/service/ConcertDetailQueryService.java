package hanghaeplus.application.concert.service;

import hanghaeplus.domain.concert.dto.ConcertQuery;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertDetailQueryService {

    private final ConcertDetailRepository concertDetailRepository;

    @Transactional(readOnly = true)
    public List<LocalDate> selectConcertAvailableDates(ConcertQuery.CreateConcertAvailableDates query) {
        return concertDetailRepository.selectConcertAvailableDates(query.concertId());
    }

}
