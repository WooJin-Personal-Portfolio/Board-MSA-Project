package exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BoardException extends RuntimeException {

    private final String display;
    private final HttpStatus status;

    public BoardException(ErrorCode errorCode) {
        this.display = errorCode.getDisplay();
        this.status = errorCode.getStatus();
    }
}
