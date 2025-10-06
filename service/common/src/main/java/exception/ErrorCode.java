package exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ARTICLE_NOT_FOUND("Article not found", HttpStatus.NOT_FOUND),



    ;

    private final String display;
    private final HttpStatus status;

    ErrorCode(String display, HttpStatus status) {
        this.display = display;
        this.status = status;
    }
}
