package hanghaeplus.api.common.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanghaeplus.application.queue.service.QueueTokenQueryService;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.QueueToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final QueueTokenQueryService queueTokenQueryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        if (method.equals("POST") && uri.endsWith("/queues")) {
            return true;
        }

        String requestQueueToken = request.getHeader("X-QUEUE-TOKEN");
        QueueToken queueToken = null;
        Long concertId = null;
        if (method.equals("GET")) {
            concertId = Long.valueOf(uri.split("/")[2]);
            if (uri.endsWith("/queues")) {
                queueToken = queueTokenQueryService.getWaitingQueueToken(new QueueQuery.CreateWaitingQueueToken(concertId, requestQueueToken));
            } else {
                queueToken = queueTokenQueryService.getActiveQueueToken(new QueueQuery.CreateActiveQueueToken(concertId, requestQueueToken));
            }
            if (queueToken != null) {
                return true;
            }
        }

        if (!uri.endsWith("/queues")) {
            if (uri.startsWith("/concerts")) {
                concertId = Long.valueOf(uri.split("/")[2]);
            } else {
                ReadableRequestBodyWrapper wrapper = new ReadableRequestBodyWrapper(request);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(wrapper.getRequestBody());
                concertId = jsonNode.get("concertId").asLong();
            }

            queueToken = queueTokenQueryService.getActiveQueueToken(new QueueQuery.CreateActiveQueueToken(concertId, requestQueueToken));
            if (queueToken != null) {
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
