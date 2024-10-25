package hanghaeplus.api.common.interceptor;

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
            return false;
        }

        String requestQueueToken = request.getHeader("X-QUEUE-TOKEN");
        QueueToken queueToken = queueTokenQueryService.getQueueToken(new QueueQuery.CreateToken(requestQueueToken));
        if (queueToken.isWaiting() && uri.endsWith("/queues")) {
            return true;
        }
        if (queueToken.isActivated()) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
