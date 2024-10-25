package hanghaeplus.application.concert.facade;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.dto.ConcertResponse;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Concert 통합 테스트")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Autowired
    private ReservationRepository reservationRepository;

    private String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";

    @BeforeEach
    void setUp() {
        tokenRepository.deleteAll();

        Queue queue = new Queue(1L, 1L, 50);
        queueRepository.save(queue);

        for (int i = 0; i < 50; i++) {
            QueueToken queueToken = QueueToken.createWaiting(1L, tokenId + i);
            queueTokenRepository.save(queueToken);
        }
        QueueToken queueToken = QueueToken.createWaiting(1L, tokenId);
        queueTokenRepository.save(queueToken);
    }

    @Test
    @DisplayName("콘서트 대기열 순서 조회 테스트")
    void pass_getConcertQueuePositionTest() {
        // given
        Long concertId = 1L;
        int position = 51;

        ConcertRequest.ConcertQueuePosition request = new ConcertRequest.ConcertQueuePosition(tokenId, concertId);

        // when
        ConcertResponse.ConcertQueuePosition result = sut.getConcertQueuePosition(request);

        // then
        assertThat(result.concertQueuePosition()).isEqualTo(position);
    }

    @Test
    @DisplayName("콘서트 대기열 등록 테스트")
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
    @DisplayName("콘서트 사용 가능한 날짜 조회 테스트")
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
    @DisplayName("콘서트 사용 가능한 좌석 조회 테스트")
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
    @DisplayName("콘서트 좌석 예약 테스트")
    void pass_reserveConcertSeatTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        Long queueId = 1L;
        Long userId = 1L;

        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        Optional<QueueToken> queueTokenOpt = queueTokenRepository.findByTokenId(tokenId);
        QueueToken queueToken = QueueToken.createActivated(queueTokenOpt.get().getId(), queueId, tokenId);
        queueTokenRepository.save(queueToken);

        Seat seat = new Seat(1L, 1L, "A-1", 20000, SeatStatus.EMPTY);
        seatRepository.save(seat);

        // when
        sut.reserveConcertSeat(request);

        // then
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        assertThat(seatOpt.get().getStatus()).isEqualTo(SeatStatus.PENDING);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 동시성 테스트")
    void reserveConcertSeatConcurrentTest() {
        // given
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        Long userId = 1L;

        for (int i = 0; i < 30; i++) {
            Token token = Token.create(tokenId + i, userId + i);
            tokenRepository.save(token);

            QueueToken queueToken = QueueToken.createActivated((long) i + 1, 1L, tokenId + i);
            queueTokenRepository.save(queueToken);
        }

        Seat seat = new Seat(2L, 1L, "B-1", 15000, SeatStatus.EMPTY);
        seatRepository.save(seat);

        // when
        for (int i = 0; i < 100; i++) {
            int idx = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId + idx, concertId, detailId, seatId);
                sut.reserveConcertSeat(request);
            });
            futures.add(future);
        }

        // given
        List<Reservation> reservations = reservationRepository.selectPendingReservations(seatId);
        assertThat(reservations.size()).isEqualTo(1);
    }
}