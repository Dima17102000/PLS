/**
 * @author Dmytro Kostariev
 * @id 11848552
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializedArticleDAO implements ArticleDAO {

    private String Name;
    private File filename;
    private List<Article> articles = new ArrayList<>();

    public SerializedArticleDAO(String Name) {
        this.Name = Name;
        this.filename = new File(Name);
        // deserialize if file exists
        if((filename.exists()))
        {
           deserializeArticles();
        }
    }

    @Override
    public List<Article> getArticleList() {
        return articles;
    }

    @Override
    public Article getArticle(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    public void deserializeArticles() 
    {
        if (filename.exists()) 
        {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            articles = (List<Article>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization1." + e.getMessage());
            
        }
        }
    }

    public void serializeArticles(List<Article> articles) {
        
        File file = new File(Name);
    if (file.exists()) file.delete();
    try {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename, true));
        writer.writeObject(articles);
        writer.close(); 
    } catch (Exception e) {
        System.out.println("Error during serialization: " + e.getMessage());
    }
    }

    @Override
    public void saveArticle(Article article) throws IllegalArgumentException{
        if(articles.contains(article))
        {
            System.out.println("Error: Article already exists." + "(id=" + article.getId() + ")");
        }
        
            articles.add(article);

        
        
        
        File file = new File(Name);
        if (file.exists()) file.delete();
        try {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename, true));
        writer.writeObject(articles);
        writer.close();
        }
        catch (Exception e) {
        System.out.println("Error during serialization2: " + e.getMessage());
         
        }

    }

    @Override
    public void deleteArticle(int id) throws IllegalArgumentException{

    Article articleToRemove = null;
    for (Article article : articles) {
        if (article.getId() == id) {
            articleToRemove = article;
            break;
        }
    }

    if (articleToRemove != null) {
        articles.remove(articleToRemove);
        serializeArticles(articles);
    } else {
        throw new IllegalArgumentException("Can't delete an article.");
    }
    
    }
}
