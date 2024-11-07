package hanghaeplus.application.concert.service;

import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.concert.dto.ConcertQuery;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_CONCERT_DETAIL;

@Service
@RequiredArgsConstructor
public class ConcertDetailQueryService {

    private final ConcertDetailRepository concertDetailRepository;

    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "available-dates", key = "#query.concertId()")
    public List<LocalDate> selectConcertAvailableDates(ConcertQuery.CreateConcertAvailableDates query) {
        return concertDetailRepository.selectConcertAvailableDates(query.concertId());
    }

    public ConcertDetail getConcertDetail(ConcertQuery.CreateConcertDetail query) {
        return concertDetailRepository.findById(query.concertDetailId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_CONCERT_DETAIL));
    }
}
