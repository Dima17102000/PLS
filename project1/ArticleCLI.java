/**
 * @author Dmytro Kostariev
 * @id 11848552
 */

//
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArticleCLI {
    
    private static final String FILE_EXTENSION = ".dat";
    public static final DecimalFormat df = new DecimalFormat("0.00");

   

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                throw new IllegalArgumentException("Error: Invalid parameter.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (args.length >= 2) {
            String filename = args[0] + FILE_EXTENSION;
            String command = args[1];

            try {
                if (command.equals("add")) {
                    if (args.length < 4) {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }

                    if (args[2].equals("book") && args.length == 9) {
                        addBook(filename, args[3], args[4], args[5], args[6], args[7], args[8]);
                    } else if (args[2].equals("dvd") && args.length == 10) {
                        addDVD(filename, args[3], args[4], args[5], args[6], args[7], args[8], args[9]);
    
                    } else {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }
                } else if (command.equals("list")) {
                    ArticleManagement arm = new ArticleManagement(new SerializedArticleDAO(filename));
                    java.util.List<Article> temp = arm.getAllArticles();
                    if (args.length == 3) {
                        int articleId;
                        try {
                            articleId = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Error: Article ID must be a number.");
                        }
                        listArticles(filename, articleId);
                    } else if (args.length == 2) {
                        listArticles(filename);
                    } 
                    }
                    else if (command.equals("delete")) {
                if (args.length != 3) {
        throw new IllegalArgumentException("Error: Invalid parameter.");
        }
    
    int articleId;
    try {
        articleId = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Error: Article ID must be a number.");
    }

    ArticleManagement arm = new ArticleManagement(new SerializedArticleDAO(filename));
    try {
        arm.deleteArticle(articleId);
        System.out.println("Info: Article "  + articleId + " deleted.");
    } catch (IllegalArgumentException e) {
        System.out.println("Error: Article not found. " + "(id=" + articleId + ")");
    }
}
else if(command.equals("count"))
{
   ArticleManagement arm = new ArticleManagement(new SerializedArticleDAO(filename));
    int totalArticleCount = arm.getTotalArticleCount();
    int totalBookCount = arm.getTotalBookCount();
    int totalDVDCount = arm.getTotalDvdCount();
    if(args.length == 3)
    {
        if(args[2].equals("book"))
        {
            System.out.println(totalBookCount);
        }
        else if(args[2].equals("dvd"))
        {
            System.out.println(totalDVDCount); 
        }
    }
    else
    {
    System.out.println(totalArticleCount);
    }
}
else if(command.equals("meanprice"))
{
ArticleManagement arm = new ArticleManagement(new SerializedArticleDAO(filename));
    double meanPrice = arm.getMeanPrice();
    System.out.println(df.format(meanPrice));
}
else if(command.equals("oldest"))
{
 ArticleManagement arm = new ArticleManagement(new SerializedArticleDAO(filename));
    List<Integer> oldestArticleIds = arm.getOldestArticleIds();
    if (oldestArticleIds.isEmpty()) {
        System.out.println("No articles found.");
    } else {
        for (Integer id : oldestArticleIds) {
            System.out.println("Id: " + id);
        }
    }
}
                    else {
                        throw new IllegalArgumentException("Error: Invalid parameter.");
                    }
                //}
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    

    private static void addBook(String filename, String id, String title, String publisher, String releaseYear,
                            String basePrice, String pages) {
    try {
        int articleId = Integer.parseInt(id);
        int year = Integer.parseInt(releaseYear);
        double price = Double.parseDouble(basePrice);
        int numPages = Integer.parseInt(pages);

        if (year < 0 || year > 9999) {
            throw new IllegalArgumentException("Error: Invalid release year.");
        }

        if (numPages <= 0) {
            throw new IllegalArgumentException("Error: Invalid parameter.");
        }

        Book book = new Book(articleId, title, year, publisher, price, numPages);

        SerializedArticleDAO s = new SerializedArticleDAO(filename);

         try {
            s.saveArticle(book);
            //double discountedPrice = book.getPrice();
            System.out.println("Info: Article " + articleId + " added.");
            //System.out.println("Discounted Price: " + df.format(discountedPrice));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Could not add the article.");
        }
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Error: Invalid parameter.");
    }
    
}

private static void addDVD(String filename, String id, String title, String publisher, String releaseYear,
                           String basePrice, String length, String ageRating) {
    try {
        int articleId = Integer.parseInt(id);
        int year = Integer.parseInt(releaseYear);
        double price = Double.parseDouble(basePrice);
        int minutes = Integer.parseInt(length);
        int rating = Integer.parseInt(ageRating);

        if (year < 0 || year > 9999) {
            throw new IllegalArgumentException("Error: Invalid release year.");
        }

        if (rating != 0 && rating != 6 && rating != 12 && rating != 16 && rating != 18) {
            throw new IllegalArgumentException("Error: Invalid age rating.");
        }

        DVD dvd = new DVD(articleId, title, year, publisher, price, minutes, rating);

        SerializedArticleDAO s = new SerializedArticleDAO(filename);

        try {
            for(Article a:s.getArticleList())
            {
                if(a.getId() == articleId)
                {
                    System.out.println("Error: Article already exists." + " (id=" + dvd.getId() + ")");
                    throw new IllegalArgumentException("Error: Article already exists.(id=" + dvd.getId() + ")");
                }
    
            }
            if(s.getArticleList().contains(dvd))
            {
                System.out.println("Error: Article already exists." + dvd.getId());
                throw new IllegalArgumentException("Error: Article already exists.(id=" + dvd.getId() + ")");
            }
            s.saveArticle(dvd);
            double discountedPrice = dvd.getPrice();
            System.out.println("Info: Article " + articleId + " added.");
            //System.out.println("Discounted Price: " + df.format(discountedPrice));
        } catch (IllegalArgumentException e) {
           //throw new IllegalArgumentException("Error: Could not add the article.");
        }
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Error: Invalid parameter.");
    }
}
    

    private static void listArticles(String filename) {
        List<Article> articles = new ArrayList<>();
        
        //List<Article> articles = (List<Article>) objectInputStream.readObject();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            articles = (List<Article>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization1." + e.getMessage());
            //System.exit(1);
        }
        
        /*
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                Object obj = inputStream.readObject();
                if (obj instanceof Article) { 
                    Article article = (Article) obj;
                    articles.add(article);
                } else {
                    // Handle unexpected object type
                    System.out.println("Error: Unexpected object type found in the file." + obj.getClass().getName());
                }
                //articles.add(article);
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Could not read articles.");
        }
        */

        
        for (Article article : articles) {

            System.out.println(article.toString());
        }
        
  }

  private static void listArticles(String filename, int articleId) 
    {
    List<Article> articles = new ArrayList<>();
    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
        articles = (List<Article>) objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error during deserialization: " + e.getMessage());
        return;
    }

    Article foundArticle = null;
    for (Article article : articles) {
        if (article.getId() == articleId) {  
            foundArticle = article;
            break;
        }
    }

    if (foundArticle != null) {
        System.out.println(foundArticle);
    } else {
        System.out.println("Error: Article not found. " + "(id=" + articleId + ")");
        }
    }  
}

