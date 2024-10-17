package hanghaeplus.api.concert.controller;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.dto.ConcertResponse;
import hanghaeplus.application.concert.facade.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.openapi.api.ConcertsApi;
import org.openapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcertController implements ConcertsApi {

    private final ConcertFacade concertFacade;

    @Override
    public ResponseEntity<QueuePositionHttpResponse> getConcertQueuePosition(String tokenId, Long concertId) {
        ConcertRequest.ConcertQueuePosition request = new ConcertRequest.ConcertQueuePosition(tokenId, concertId);
        ConcertResponse.ConcertQueuePosition response = concertFacade.getConcertQueuePosition(request);

        return ResponseEntity.ok(
                new QueuePositionHttpResponse(response.concertQueuePosition()));
    }

    @Override
    public ResponseEntity<QueueEnrollmentHttpResponse> enrollConcertQueue(String tokenId, Long concertId) {
        ConcertRequest.ConcertQueueEnrollment request = new ConcertRequest.ConcertQueueEnrollment(tokenId, concertId);
        concertFacade.enrollConcertQueue(request);

        return ResponseEntity.ok(
                new QueueEnrollmentHttpResponse("콘서트 대기열에 등록되었습니다."));
    }

    @Override
    public ResponseEntity<SeatReservationHttpResponse> reserveConcertSeat(Long concertId, Long detailId, Long seatId, String tokenId) {
        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);
        concertFacade.reserveConcertSeat(request);

        return ResponseEntity.ok(
                new SeatReservationHttpResponse("좌석이 임시 배정되었습니다."));
    }

    @Override
    public ResponseEntity<List<String>> selectConcertAvailableDates(Long concertId, String tokenId) {
        ConcertRequest.ConcertAvailableDates request = new ConcertRequest.ConcertAvailableDates(tokenId, concertId);
        ConcertResponse.ConcertAvailableDates response = concertFacade.selectConcertAvailableDates(request);

        return ResponseEntity.ok(response.concertAvailableDates().stream()
                .map(LocalDate::toString)
                .toList());
    }

    @Override
    public ResponseEntity<List<AvailableSeatsHttpResponse>> selectConcertAvailableSeats(Long concertId, Long detailId, String tokenId) {
        ConcertRequest.ConcertAvailableSeats request = new ConcertRequest.ConcertAvailableSeats(tokenId, concertId, detailId);
        List<ConcertResponse.ConcertAvailableSeats> response = concertFacade.selectConcertAvailableSeats(request);

        return ResponseEntity.ok(response.stream()
                .map(res -> new AvailableSeatsHttpResponse(res.seatId(), res.seatName(), res.price()))
                .toList());
    }

}
