package hanghaeplus.api.concert.controller;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.dto.ConcertResponse;
import hanghaeplus.application.concert.facade.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.openapi.api.ConcertsApi;
import org.openapi.model.AvailableSeatsHttpResponse;
import org.openapi.model.QueueEnrollmentHttpResponse;
import org.openapi.model.QueuePositionHttpResponse;
import org.openapi.model.SeatReservationHttpResponse;
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
                new QueuePositionHttpResponse().count(response.concertQueuePosition()));
    }

    @Override
    public ResponseEntity<QueueEnrollmentHttpResponse> enrollConcertQueue(String tokenId, Long concertId) {
        ConcertRequest.ConcertQueueEnrollment request = new ConcertRequest.ConcertQueueEnrollment(tokenId, concertId);
        concertFacade.enrollConcertQueue(request);

        return ResponseEntity.ok(
                new QueueEnrollmentHttpResponse().message("콘서트 대기열에 등록되었습니다."));
    }

    @Override
    public ResponseEntity<SeatReservationHttpResponse> reserveConcertSeat(String tokenId, Long concertId, Long detailId, Long seatId) {
        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);
        concertFacade.reserveConcertSeat(request);

        return ResponseEntity.ok(
                new SeatReservationHttpResponse().message("좌석이 임시 배정되었습니다."));
    }

    @Override
    public ResponseEntity<List<String>> selectConcertAvailableDates(String tokenId, Long concertId) {
        ConcertRequest.ConcertAvailableDates request = new ConcertRequest.ConcertAvailableDates(tokenId, concertId);
        ConcertResponse.ConcertAvailableDates response = concertFacade.selectConcertAvailableDates(request);

        return ResponseEntity.ok(response.concertAvailableDates().stream()
                .map(LocalDate::toString)
                .toList());
    }

    @Override
    public ResponseEntity<List<AvailableSeatsHttpResponse>> selectConcertAvailableSeats(String tokenId, Long concertId, Long detailId) {
        ConcertRequest.ConcertAvailableSeats request = new ConcertRequest.ConcertAvailableSeats(tokenId, concertId, detailId);
        List<ConcertResponse.ConcertAvailableSeats> response = concertFacade.selectConcertAvailableSeats(request);

        return ResponseEntity.ok(response.stream()
                .map(res -> new AvailableSeatsHttpResponse()
                        .seatId(res.seatId())
                        .seatName(res.seatName())
                        .price(res.price()))
                .toList());
    }

}
