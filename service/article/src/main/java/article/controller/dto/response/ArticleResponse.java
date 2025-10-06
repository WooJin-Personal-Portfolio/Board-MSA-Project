package article.controller.dto.response;

import article.service.dto.result.ArticleResult;

import java.time.LocalDateTime;

public record ArticleResponse(Long articleId,
                              String title,
                              String content,
                              Long boardId,
                              Long writerId,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {

    public static ArticleResponse from(ArticleResult result) {
        return new ArticleResponse(
                result.articleId(),
                result.title(),
                result.content(),
                result.boardId(),
                result.writerId(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
