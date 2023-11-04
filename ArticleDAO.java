/**
 * @author Dmytro Kostariev
 * @id 11848552
 * @comment READ-ONLY FILE 
 */

import java.util.List;

public interface ArticleDAO {

    public List<Article> getArticleList();

    public Article getArticle(int id);

    public void saveArticle(Article article);

    public void deleteArticle(int id);
}


