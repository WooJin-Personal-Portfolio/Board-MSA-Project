package article.service.dto.result;

import java.util.List;

public record ArticleResults(
        List<ArticleResult> results,
        Long articleCount
) {

    public static ArticleResults of(List<ArticleResult> results, Long articleCount) {
        return new ArticleResults(results, articleCount);
    }
}
