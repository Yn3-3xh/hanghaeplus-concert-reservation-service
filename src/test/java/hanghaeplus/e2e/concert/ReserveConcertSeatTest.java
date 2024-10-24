package hanghaeplus.e2e.concert;

import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.facade.ConcertFacade;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.ResourceAccessException;

import java.net.HttpRetryException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReserveConcertSeatTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate sut;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @MockBean
    private ConcertFacade concertFacade; // 파사드까지는 통합 테스트 했으므로 모킹해서 컨트롤러까지 e2e 테스트

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
        queueRepository.deleteAll();
        queueTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("좌석 예약 테스트 - 통과")
    void pass_reserveConcertSeatTest() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        Queue queue = new Queue(null, 1L, 50);
        queueRepository.save(queue);

        QueueToken queueToken = QueueToken.createWaiting(1L, tokenId);
        queueTokenRepository.save(queueToken);

        QueueToken queueTokenActivated = QueueToken.createActivated(1L, 1L, tokenId);
        queueTokenRepository.save(queueTokenActivated);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/concerts/1/details/1/seats/1";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        doNothing().when(concertFacade).reserveConcertSeat(new ConcertRequest.SeatReservation(tokenId, 1L, 1L, 1L));

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("좌석 예약 테스트 - 실패 - 유저 토큰이 없는 경우")
    void fail_reserveConcertSeatTest1() {
        // given
        String tokenId = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/concerts/1/details/1/seats/1";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            sut.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class);
        });

        // then
        HttpRetryException clientError = (HttpRetryException) exception.getCause();
        assertThat(clientError.responseCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("좌석 예약 테스트 - 실패 - 대기열 토큰이 없는 경우")
    void fail_reserveConcertSeatTest2() {
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", null);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/concerts/1/details/1/seats/1";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}


