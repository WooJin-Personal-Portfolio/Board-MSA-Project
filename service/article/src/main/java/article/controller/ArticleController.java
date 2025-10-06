package article.controller;

import article.controller.dto.request.ArticleCreateRequest;
import article.controller.dto.request.ArticleUpdateRequest;
import article.controller.dto.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import article.service.ArticleService;
import article.service.dto.result.ArticleResult;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ArticleResponse getArticle(@PathVariable(name = "articleId") Long articleId) {
        ArticleResult article = articleService.getArticle(articleId);
        return ArticleResponse.from(article);
    }

    @PostMapping
    public ArticleResponse createArticle(@RequestBody ArticleCreateRequest request) {
        ArticleResult article = articleService.createArticle(request);
        return ArticleResponse.from(article);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse updateArticle(@PathVariable(name = "articleId") Long articleId,
                                         @RequestBody ArticleUpdateRequest request) {
        ArticleResult article = articleService.updateArticle(articleId, request);
        return ArticleResponse.from(article);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable(name = "articleId") Long articleId) {
        articleService.deleteArticle(articleId);
    }
}
