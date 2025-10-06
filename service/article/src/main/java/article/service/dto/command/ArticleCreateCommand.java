package article.service.dto.command;

public record ArticleCreateCommand(String title, String content, Long boardId, Long writerId) {
}
