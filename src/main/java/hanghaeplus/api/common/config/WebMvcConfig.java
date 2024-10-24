package hanghaeplus.api.common.config;

import hanghaeplus.api.common.interceptor.QueueTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final QueueTokenInterceptor queueInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(queueInterceptor)
                .addPathPatterns(
                        "/concerts/**",
                        "/orders/**"
                ).excludePathPatterns(
                        "/tokens/**",
                        "/points/**"
                );
    }
}