package article.init;

import article.entity.Article;
import article.repository.ArticleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import key.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ArticleInit {

    private static int BULK_SIZE = 2000;
    private static int EXECUTE_SIZE = 4000;

    private final TransactionTemplate transactionTemplate;
    private final ArticleRepository articleRepository;

    Snowflake snowflake = new Snowflake();

    @PostConstruct
    void init() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(EXECUTE_SIZE);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < EXECUTE_SIZE; i++) {
            executor.execute(() -> {
                try {
                    insert();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
    }

    @PreDestroy
    void destroy() {
        transactionTemplate.execute(status -> {
            articleRepository.deleteAll();
            return null;
        });
    }

    void insert() {
        transactionTemplate.execute(status -> {
            List<Article> articles = new ArrayList<>();
            for (int i = 0; i < BULK_SIZE; i++) {
                articles.add(Article.createArticle(snowflake.nextId(), "title" + (i + 1), "content" + (i + 1), 1L, 1L));
            }
            articleRepository.saveAll(articles);
            return null;
        });
    }
}