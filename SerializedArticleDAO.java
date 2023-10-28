import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializedArticleDAO implements ArticleDAO {
    private String fileName;

    public SerializedArticleDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Article> getArticleList() {
        List<Article> articles = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            articles = (List<Article>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization.");
            System.exit(1);
        }
        return articles;
    }

    @Override
    public Article getArticle(int id) {
        List<Article> articles = getArticleList();
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    @Override
    public void saveArticle(Article article) throws IllegalArgumentException{
        List<Article> articles = getArticleList();
        for(Article a_:articles)
        {
            if(a_.getId() == article.getId())
            throw new IllegalArgumentException("Error: Article already exists.(id=" + article.getId() + ")");
        }
        articles.add(article);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(articles);
        } catch (IOException e) {
            System.err.println("Error during serialization.");
            System.exit(1);
        }
    }

    @Override
    public void deleteArticle(int id) throws IllegalArgumentException{
        List<Article> articles = getArticleList();
        Article articleToRemove = null;
        for (Article article : articles) {
            if (article.getId() == id) {
                articleToRemove = article;
                break;
            }
        }
        if (articleToRemove != null) {
            articles.remove(articleToRemove);
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
                objectOutputStream.writeObject(articles);
            } catch (IOException e) {
                System.err.println("Error during serialization.");
                System.exit(1);
            }
        }
        else throw new IllegalArgumentException("Can't delete an article.");
    }
}
