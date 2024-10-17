package hanghaeplus.application.concert.facade;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.dto.ConcertResponse;
import hanghaeplus.application.concert.service.ConcertDetailQueryService;
import hanghaeplus.application.concert.service.ReservationCommandService;
import hanghaeplus.application.concert.service.SeatQueryService;
import hanghaeplus.application.queue.service.QueueQueryService;
import hanghaeplus.application.queue.service.QueueTokenCommandService;
import hanghaeplus.application.queue.service.QueueTokenQueryService;
import hanghaeplus.application.token.service.TokenQueryService;
import hanghaeplus.domain.concert.dto.ConcertQuery;
import hanghaeplus.domain.concert.dto.ReservationCommand;
import hanghaeplus.domain.concert.dto.SeatQuery;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.queue.dto.QueueCommand;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.token.dto.TokenQuery;
import hanghaeplus.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertDetailQueryService concertDetailQueryService;

    private final SeatQueryService seatQueryService;

    private final ReservationCommandService reservationCommandService;

    private final QueueQueryService queueQueryService;
    private final QueueTokenQueryService queueTokenQueryService;
    private final QueueTokenCommandService queueTokenCommandService;

    private final TokenQueryService tokenQueryService;

    public ConcertResponse.ConcertQueuePosition getConcertQueuePosition(ConcertRequest.ConcertQueuePosition request) {
        QueueToken queueToken = queueTokenQueryService.getQueueToken(
                new QueueQuery.CreateToken(request.tokenId()));
        queueToken.checkExpired();

        Queue queue = queueQueryService.getQueue(
                new QueueQuery.Create(request.concertId()));

        int concertQueuePosition = queueTokenQueryService.getWaitingPosition(
                new QueueQuery.CreateTokenWaiting(request.tokenId(), queue.getId()));

        return new ConcertResponse.ConcertQueuePosition(concertQueuePosition);
    }

    public void enrollConcertQueue(ConcertRequest.ConcertQueueEnrollment request) {
        Queue queue = queueQueryService.getQueue(
                new QueueQuery.Create(request.concertId()));

        queueTokenCommandService.enrollQueueTokenWaiting(
                new QueueCommand.CreateTokenWaiting(request.tokenId(), queue.getId()));
    }

    public ConcertResponse.ConcertAvailableDates selectConcertAvailableDates(ConcertRequest.ConcertAvailableDates request) {
        QueueToken queueToken = queueTokenQueryService.getQueueToken(
                new QueueQuery.CreateToken(request.tokenId()));
        queueToken.checkExpired();

        List<LocalDate> concertAvailableDates = concertDetailQueryService.selectConcertAvailableDates(
                new ConcertQuery.CreateConcertAvailableDates(request.concertId()));

        return new ConcertResponse.ConcertAvailableDates(concertAvailableDates);
    }

    public List<ConcertResponse.ConcertAvailableSeats> selectConcertAvailableSeats(ConcertRequest.ConcertAvailableSeats request) {
        QueueToken queueToken = queueTokenQueryService.getQueueToken(
                new QueueQuery.CreateToken(request.tokenId()));
        queueToken.checkExpired();

        List<Seat> concertAvailableSeats = seatQueryService.selectConcertAvailableSeats(
                new SeatQuery.CreateConcertAvailableSeats(request.detailId()));

        return concertAvailableSeats.stream()
                .map(seat -> new ConcertResponse.ConcertAvailableSeats(seat.getId(), seat.getName(), seat.getPrice()))
                .toList();
    }

    public void reserveConcertSeat(ConcertRequest.SeatReservation request) {
        QueueToken queueToken = queueTokenQueryService.getQueueToken(
                new QueueQuery.CreateToken(request.tokenId()));
        queueToken.checkExpired();

        Token token = tokenQueryService.getToken(
                new TokenQuery.Create(request.tokenId()));

        reservationCommandService.reserveConcertSeat(
                new ReservationCommand.Create(request.seatId(), token.getUserId()));
    }

    public void concertQueueScheduler() {
        // 토큰 만료 체크 > 대기열 진입
    }

    public void concertReservationScheduler() {
        // 예약 만료시간 체크 > 예약 만료로 수정 > Seat는 빈공간으로 수정
    }
}
