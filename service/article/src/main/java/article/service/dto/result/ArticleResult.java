package article.service.dto.result;

import article.entity.Article;

import java.time.LocalDateTime;

public record ArticleResult(Long articleId,
                            String title,
                            String content,
                            Long boardId,
                            Long writerId,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {

    public static ArticleResult from(Article article) {
        return new ArticleResult(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getBoardId(),
                article.getWriterId(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }
}
