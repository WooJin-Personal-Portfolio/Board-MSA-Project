package article.controller.dto.response;

import article.service.dto.result.ArticleResult;

import java.util.List;

public record ArticlesResponse(List<ArticleResponse> articles, Long articleCount) {

    public static ArticlesResponse of(List<ArticleResult> results, Long articleCount) {
        List<ArticleResponse> articles = results.stream()
                .map(ArticleResponse::from)
                .toList();
        return new ArticlesResponse(articles, articleCount);
    }
}
