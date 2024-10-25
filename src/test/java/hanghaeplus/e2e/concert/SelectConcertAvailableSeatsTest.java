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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SelectConcertAvailableSeatsTest {

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
    @DisplayName("예약 가능 좌석 조회 테스트 - 통과")
    void pass_selectConcertAvailableSeatsTest() {
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

        String url = "http://localhost:" + port + "/concerts/1/details/1/available-seats";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(concertFacade.selectConcertAvailableSeats(new ConcertRequest.ConcertAvailableSeats(tokenId, 1L, 1L)))
                .thenReturn(List.of());

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 테스트 - 실패 - 유저 토큰이 없는 경우")
    void fail_selectConcertAvailableSeatsTest1() {
        // given
        String tokenId = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/concerts/1/details/1/available-seats";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 테스트 - 실패 - 대기열 토큰이 없는 경우")
    void fail_selectConcertAvailableSeatsTest2() {
        // given
        Long userId = 1L;
        String tokenId = UUID.randomUUID().toString();

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-TOKEN", tokenId);
        headers.add("X-QUEUE-TOKEN", null);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:" + port + "/concerts/1/details/1/available-seats";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // when
        ResponseEntity<String> response = sut.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
