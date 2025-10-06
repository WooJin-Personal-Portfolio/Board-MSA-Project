package article.mapper;

import article.controller.dto.request.ArticleCreateRequest;
import article.controller.dto.response.ArticleResponse;
import article.entity.Article;
import org.mapstruct.Mapper;
import article.service.dto.command.ArticleCreateCommand;
import article.service.dto.result.ArticleResult;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleCreateCommand toArticleCreateCommand(ArticleCreateRequest request);

}
