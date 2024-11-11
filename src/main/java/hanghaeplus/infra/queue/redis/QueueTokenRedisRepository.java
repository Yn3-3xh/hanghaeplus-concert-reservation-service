package hanghaeplus.infra.queue.redis;

import hanghaeplus.domain.queue.entity.QueueToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class QueueTokenRedisRepository {


    private final RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String, String> zSetOperations;

    @PostConstruct
    public void init() {
        zSetOperations = redisTemplate.opsForZSet();
    }

    public int getWaitingPosition(String tokenId, Long queueId) {
        String key = "WAITING_QUEUE:" + queueId;
        Long rank = zSetOperations.rank(key, tokenId);

        if (rank == null) {
            throw new NoSuchElementException();
        }

        return rank.intValue() + 1;
    }

    public void save(QueueToken queueToken) {
        String key = "WAITING_QUEUE:" + queueToken.getQueueId();
        long score = System.currentTimeMillis();

        zSetOperations.add(key, queueToken.getTokenId(), score);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    public void save2(QueueToken queueToken) {
        String key = "WAITING_QUEUE:" + queueToken.getQueueId();
        long score = System.nanoTime();

        zSetOperations.add(key, queueToken.getTokenId(), score);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    public Optional<Integer> getExpiredActiveQueueTokenCount(Long queueId) {
        String key = "ACTIVE_QUEUE" + queueId;
        Long count = zSetOperations.zCard(key);

        return Optional.ofNullable(count)
                .map(Long::intValue);
    }

    public List<QueueToken> popWaitingQueueToken(Long queueId, int waitingToActivatedCount) {
        String key = "WAITING_QUEUE:" + queueId;
        Set<ZSetOperations.TypedTuple<String>> topTokens = zSetOperations.popMin(key, waitingToActivatedCount);

        return Optional.ofNullable(topTokens)
                .map(tokens -> tokens.stream()
                        .map(tuple -> QueueToken.create(queueId, tuple.getValue()))
                        .toList())
                .orElseGet(Collections::emptyList);
    }

    public void insertActivatedQueueToken(List<QueueToken> queueTokens) {
        for (QueueToken queueToken : queueTokens) {
            String key = "ACTIVE_QUEUE:" + queueToken.getQueueId();

            redisTemplate.opsForList().rightPush(key, queueToken.getTokenId());
            redisTemplate.expire(key, 10, TimeUnit.MINUTES);
        }
    }

    public void deleteQueueToken(Long queueId, String tokenId) {
        String key = "ACTIVE_QUEUE:" + queueId;
        redisTemplate.opsForList().remove(key, 0, tokenId);
    }

    public Optional<QueueToken> findWaitingQueueToken(Long queueId, String tokenId) {
        String key = "WAITING_QUEUE:" + queueId;
        Double score = zSetOperations.score(key, tokenId);

        return score != null
                ? Optional.of(QueueToken.create(queueId, tokenId))
                : Optional.empty();
    }

    public Optional<QueueToken> findActiveQueueToken(Long queueId, String tokenId) {
        String key = "ACTIVE_QUEUE:" + queueId;
        String value = redisTemplate.opsForList().index(key, 0);

        return value != null
                ? Optional.of(QueueToken.create(queueId, tokenId))
                : Optional.empty();
    }
}
