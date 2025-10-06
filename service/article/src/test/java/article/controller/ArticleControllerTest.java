package article.controller;

import article.controller.dto.request.ArticleCreateRequest;
import article.controller.dto.request.ArticleUpdateRequest;
import article.mapper.ArticleMapper;
import article.service.ArticleService;
import article.service.dto.result.ArticleResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import key.Snowflake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ArticleMapper articleMapper;
    @MockitoBean private ArticleService articleService;

    Snowflake snowflake = new Snowflake();

    @Test
    @DisplayName("신규 게시물을 등록한다.")
    void 신규_게시물을_등록한다() throws Exception {

        // given
        ArticleCreateRequest request = new ArticleCreateRequest("title", "content", 1L, 1L);
        ArticleResult result = new ArticleResult(snowflake.nextId(), request.title(), request.content(), request.boardId(), request.writerId(), LocalDateTime.now(), LocalDateTime.now());
        given(articleService.createArticle(any(ArticleCreateRequest.class))).willReturn(result);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("게시물을 상세 조회한다.")
    void 게시물을_상세_조회한다() throws Exception {

        // given
        Long articleId = snowflake.nextId();
        ArticleCreateRequest request = new ArticleCreateRequest("title", "content", 1L, 1L);
        ArticleResult result = new ArticleResult(articleId, request.title(), request.content(), request.boardId(), request.writerId(), LocalDateTime.now(), LocalDateTime.now());
        given(articleService.getArticle(anyLong())).willReturn(result);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/" + articleId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(articleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"));
    }

    @Test
    @DisplayName("게시물을 수정한다.")
    void 게시물을_수정한다() throws Exception {

        // given
        Long articleId = snowflake.nextId();
        ArticleUpdateRequest request = new ArticleUpdateRequest("updated title", "updated content");
        ArticleResult result = new ArticleResult(articleId, "updated title", "updated content", 1L, 1L, LocalDateTime.now(), LocalDateTime.now());
        given(articleService.updateArticle(anyLong(), any(ArticleUpdateRequest.class))).willReturn(result);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/articles/" + articleId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(articleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("updated title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("updated content"));
    }
}
