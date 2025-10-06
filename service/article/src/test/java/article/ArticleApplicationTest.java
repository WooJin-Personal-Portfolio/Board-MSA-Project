package article;

import article.init.ArticleInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleApplicationTest {

    @Autowired private ArticleInit articleInit;


}
