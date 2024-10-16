package hanghaeplus.api.concert.controller;

import org.openapi.api.ConcertsApi;
import org.openapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class ConcertController implements ConcertsApi {

    @Override
    public ResponseEntity<QueueCountHttpResponse> checkConcertDateQueueCount(UUID X_USER_TOKEN, Long concertId, String date) {
        return ResponseEntity.ok(new QueueCountHttpResponse(5));
    }

    @Override
    public ResponseEntity<SeatReservationHttpResponse> reserveConcertSeat(Long concertId, String date, Long seatId, UUID X_USER_TOKEN, SeatReservationHttpRequest seatReservationHttpRequest) {
        return ResponseEntity.ok(new SeatReservationHttpResponse("좌석이 임시 배정되었습니다."));
    }

    @Override
    public ResponseEntity<List<String>> selectAvailableConcertDates(Long concertId, UUID X_USER_TOKEN) {
        LocalDate localDate = LocalDate.of(2024, 10, 20);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return ResponseEntity.ok(List.of(date.toString()));
    }

    @Override
    public ResponseEntity<List<AvailableSeatsHttpResponse>> selectAvailableConcertSeats(Long concertId, String date, UUID X_USER_TOKEN) {
        return ResponseEntity.ok(List.of(
                new AvailableSeatsHttpResponse(1L, "A-1"),
                new AvailableSeatsHttpResponse(2L, "A-2")));
    }
}
