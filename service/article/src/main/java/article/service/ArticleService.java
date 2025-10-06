package article.service;

import article.controller.dto.request.ArticleCreateRequest;
import article.controller.dto.request.ArticleUpdateRequest;
import article.entity.Article;
import article.service.dto.command.ArticleCreateCommand;
import exception.BoardException;
import key.Snowflake;
import lombok.RequiredArgsConstructor;
import article.mapper.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import article.repository.ArticleRepository;
import article.service.dto.result.ArticleResult;

import static exception.ErrorCode.ARTICLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private Snowflake snowflake = new Snowflake();

    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;

    // Test Code 작성을 용이하게 하기 위해 POST 요청 시에도 반환 타입을 지정하도록
    @Transactional
    public ArticleResult createArticle(ArticleCreateRequest request) {
        ArticleCreateCommand command = articleMapper.toArticleCreateCommand(request);
        Article article = Article.createArticle(snowflake.nextId(), command.title(), command.content(), command.boardId(), command.writerId());
        Article savedArticle = articleRepository.save(article);
        return ArticleResult.from(savedArticle);
    }

    @Transactional
    public ArticleResult updateArticle(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BoardException(ARTICLE_NOT_FOUND));
        article.update(request.title(), request.content());
        return ArticleResult.from(article);
    }

    public ArticleResult getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new BoardException(ARTICLE_NOT_FOUND));
        return ArticleResult.from(article);
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
