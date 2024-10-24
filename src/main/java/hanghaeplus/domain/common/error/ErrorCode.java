package hanghaeplus.domain.common.error;

import org.springframework.boot.logging.LogLevel;

public interface ErrorCode {

    ErrorStatus getStatus();

    String getCode();

    String getMessage();

    LogLevel getLogLevel();
}
