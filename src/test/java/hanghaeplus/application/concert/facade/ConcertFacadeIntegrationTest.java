package hanghaeplus.application.concert.facade;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.dto.ConcertResponse;
import hanghaeplus.application.concert.service.*;
import hanghaeplus.application.queue.service.QueueQueryService;
import hanghaeplus.application.queue.service.QueueTokenCommandService;
import hanghaeplus.application.queue.service.QueueTokenQueryService;
import hanghaeplus.application.token.service.TokenQueryService;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hanghaeplus.application.concert.error.ConcertErrorCode.NOT_FOUND_SEAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Concert 통합 테스트")
@SpringBootTest
class ConcertFacadeIntegrationTest {

    @Autowired
    private ConcertFacade sut;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private ConcertDetailRepository concertDetailRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";

    @BeforeEach
    void setUp() {
        Queue queue = new Queue(1L, 1L, 50);
        queueRepository.save(queue);

        for (int i = 0; i < 5; i++) {
            QueueToken queueToken2 = QueueToken.createWaiting(1L, tokenId+i);
            queueTokenRepository.save(queueToken2);
        }
        QueueToken queueToken = QueueToken.createWaiting(1L, tokenId);
        queueTokenRepository.save(queueToken);
    }

    @Test
    @DisplayName("콘서트 대기열 순서 조회")
    void pass_getConcertQueuePositionTest() {
        // given
        Long concertId = 1L;
        int position = 6;

        ConcertRequest.ConcertQueuePosition request = new ConcertRequest.ConcertQueuePosition(tokenId, concertId);

        // when
        ConcertResponse.ConcertQueuePosition result = sut.getConcertQueuePosition(request);

        // then
        assertThat(result.concertQueuePosition()).isEqualTo(position);
    }

    @Test
    @DisplayName("콘서트 대기열 등록")
    void pass_enrollConcertQueueTest() {
        // given
        Long concertId = 1L;
        Long queueId = 1L;

        ConcertRequest.ConcertQueueEnrollment request = new ConcertRequest.ConcertQueueEnrollment(tokenId, concertId);
        int beforeCount = queueTokenRepository.findByQueueId(queueId).size();

        // when
        sut.enrollConcertQueue(request);

        // then
        int afterCount = queueTokenRepository.findByQueueId(queueId).size();
        assertThat(afterCount).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("콘서트 사용 가능한 날짜 조회")
    void pass_selectConcertAvailableDatesTest() {
        // given
        Long concertId = 1L;
        List<LocalDate> availableDates = List.of(
                LocalDate.of(2024, 10, 20),
                LocalDate.of(2024, 10, 21),
                LocalDate.of(2024, 10, 22));

        ConcertRequest.ConcertAvailableDates request = new ConcertRequest.ConcertAvailableDates(tokenId, concertId);

        ConcertDetail concertDetail = new ConcertDetail(null, 1L, "Hall-A", 100,
                LocalDate.of(2024, 10, 30),
                LocalDate.of(2024, 10, 20),
                LocalDate.of(2024, 10, 22),
                ConcertDetailStatus.OPEN);
        concertDetailRepository.saveConcertDetail(concertDetail);

        // when
        ConcertResponse.ConcertAvailableDates result = sut.selectConcertAvailableDates(request);

        // then
        assertThat(result.concertAvailableDates()).containsExactlyElementsOf(availableDates);
    }

    @Test
    @DisplayName("콘서트 사용 가능한 좌석 조회")
    void pass_selectConcertAvailableSeatsTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;

        ConcertRequest.ConcertAvailableSeats request = new ConcertRequest.ConcertAvailableSeats(tokenId, concertId, detailId);

        Seat seat1 = new Seat(1L, 1L, "A-1", 20000, SeatStatus.EMPTY);
        Seat seat2 = new Seat(2L, 1L, "C-1", 15000, SeatStatus.EMPTY);
        seatRepository.save(seat1);
        seatRepository.save(seat2);

        // when
        List<ConcertResponse.ConcertAvailableSeats> result = sut.selectConcertAvailableSeats(request);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("콘서트 좌석 예약")
    void pass_reserveConcertSeatTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        Long queueId = 1L;
        Long userId = 1L;
        LocalDateTime expiredAt = LocalDateTime.of(2024, 10, 18, 11, 0);

        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);

        Token token = Token.create(tokenId, userId, expiredAt);
        tokenRepository.save(token);

        Optional<QueueToken> queueTokenOpt = queueTokenRepository.findByTokenId(tokenId);
        QueueToken queueToken = QueueToken.createActivated(queueTokenOpt.get().getId(), queueId, tokenId);
        queueTokenRepository.save(queueToken);

        Seat seat1 = new Seat(1L, 1L, "A-1", 20000, SeatStatus.EMPTY);
        Seat seat2 = new Seat(2L, 1L, "C-1", 15000, SeatStatus.EMPTY);
        seatRepository.save(seat1);
        seatRepository.save(seat2);

        // when
        sut.reserveConcertSeat(request);

        // then
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        assertThat(seatOpt.get().getStatus()).isEqualTo(SeatStatus.PENDING);
    }
}