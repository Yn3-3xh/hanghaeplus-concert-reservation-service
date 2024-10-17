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
import hanghaeplus.domain.token.dto.TokenQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        List<LocalDate> concertAvailableDates = concertDetailQueryService.selectConcertAvailableDates(
                new ConcertQuery.CreateConcertAvailableDates(request.concertId()));

        return new ConcertResponse.ConcertAvailableDates(concertAvailableDates);
    }

    public List<ConcertResponse.ConcertAvailableSeats> selectConcertAvailableSeats(ConcertRequest.ConcertAvailableSeats request) {
        List<Seat> concertAvailableSeats = seatQueryService.selectConcertAvailableSeats(
                new SeatQuery.CreateConcertAvailableSeats(request.detailId()));

        return concertAvailableSeats.stream()
                .map(seat -> new ConcertResponse.ConcertAvailableSeats(seat.getId(), seat.getName(), seat.getPrice()))
                .toList();
    }

    public void reserveConcertSeat(ConcertRequest.SeatReservation request) {
        Long userId = tokenQueryService.getUserId(
                new TokenQuery.Create(request.tokenId()));

        reservationCommandService.reserveConcertSeat(
                new ReservationCommand.Create(request.seatId(), userId));
    }
}
