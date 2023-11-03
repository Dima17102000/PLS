/**
 * @author Dmytro Kostariev
 * @id 11848552
 */
//
import java.util.List;
import java.util.ArrayList;

public class ArticleManagement {
    private ArticleDAO articleDAO;

    public ArticleManagement(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    // Return all data of all articles.
    public List<Article> getAllArticles() {
        return articleDAO.getArticleList();
    }

    // Return all data of a specific article.
    public Article getArticle(int id) {
        return articleDAO.getArticle(id);
    }

    // Add a new article.
    public void addArticle(Article article) {
        articleDAO.saveArticle(article);
    }

    // Delete an article.
    public void deleteArticle(int id) {
        articleDAO.deleteArticle(id);
    }

    // Determine the total number of all articles.
    public int getTotalArticleCount() {
        return articleDAO.getArticleList().size();
    }

    // Determine the total number of all books.
    public int getTotalBookCount() {
        int bookCount = 0;
        for (Article article : articleDAO.getArticleList()) {
            if (article instanceof Book) {
                bookCount++;
            }
        }
        return bookCount;
    }

    // Determine the total number of all DVDs.
    public int getTotalDvdCount() {
        int dvdCount = 0;
        for (Article article : articleDAO.getArticleList()) {
            if (article instanceof DVD) {
                dvdCount++;
            }
        }
        return dvdCount;
    }

    // Determine the mean price of all articles.
    public double getMeanPrice() {
        List<Article> articles = articleDAO.getArticleList();
        double totalPrice = articles.stream().mapToDouble(Article::getPrice).sum();
        return totalPrice / articles.size();
    }

    // Determine the ID(s) of the oldest article(s).
    public List<Integer> getOldestArticleIds() {
    List<Article> articles = articleDAO.getArticleList();
    int oldestYear = Integer.MAX_VALUE;
    List<Integer> oldestArticleIds = new ArrayList<>();

    for (Article article : articles) {
        if (article.getReleaseYear() < oldestYear) {
            oldestYear = article.getReleaseYear();
            oldestArticleIds.clear();
            oldestArticleIds.add(article.getId());
        } else if (article.getReleaseYear() == oldestYear) {
            oldestArticleIds.add(article.getId());
        }
    }

    return oldestArticleIds;
 }
}

