package hanghaeplus.api.concert.controller;

import com.schooldevops.apifirst.openapi.domain.AvailableSeatsResponseInner;
import com.schooldevops.apifirst.openapi.domain.QueueCountResponse;
import com.schooldevops.apifirst.openapi.domain.SeatReservationRequest;
import com.schooldevops.apifirst.openapi.domain.SeatReservationResponse;
import com.schooldevops.apifirst.openapi.rest.ConcertsApi;
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
    public ResponseEntity<QueueCountResponse> checkConcertDateQueueCount(UUID X_USER_TOKEN, Integer concertId, Date date) {
        return ResponseEntity.ok(new QueueCountResponse(5));
    }

    @Override
    public ResponseEntity<SeatReservationResponse> reserveConcertSeat(Integer concertId, Date date, Integer seatId, UUID X_USER_TOKEN, SeatReservationRequest seatReservationRequest) {
        return ResponseEntity.ok(new SeatReservationResponse("좌석이 임시 배정되었습니다."));
    }

    @Override
    public ResponseEntity<List<Date>> selectAvailableConcertDates(Integer concertId, UUID X_USER_TOKEN) {
        LocalDate localDate = LocalDate.of(2024, 10, 20);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return ResponseEntity.ok(List.of(date));
    }

    @Override
    public ResponseEntity<List<AvailableSeatsResponseInner>> selectAvailableConcertSeats(Integer concertId, Date date, UUID X_USER_TOKEN) {
        return ResponseEntity.ok(List.of(
                new AvailableSeatsResponseInner(1, "A-1"),
                new AvailableSeatsResponseInner(2, "A-2")));
    }
}
