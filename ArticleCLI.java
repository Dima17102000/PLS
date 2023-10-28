import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArticleCLI {
    private static final String FILE_EXTENSION = ".dat";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java ArticleCLI <file> <command>");
            System.exit(1);
        }

        String filename = args[0] + FILE_EXTENSION;
        String command = args[1];

        try {
            if (command.equals("add")) {
                if (args.length < 4) {
                    throw new IllegalArgumentException("Error: Invalid parameter.");
                }

                if (args[2].equals("book") && args.length == 8) {
                    addBook(filename, args[3], args[4], args[5], args[6], args[7], args[8]);
                } else if (args[2].equals("dvd") && args.length == 8) {
                    addDVD(filename, args[3], args[4], args[5], args[6], args[7], args[8], args[9]);
                } else {
                    throw new IllegalArgumentException("Error: Invalid parameter.");
                }
            } else if (command.equals("list")) {
                listArticles(filename);
            } else {
                throw new IllegalArgumentException("Error: Invalid parameter.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
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

            if (articleExists(filename, articleId)) {
                throw new IllegalArgumentException("Error: Article already exists. (id=" + articleId + ")");
            }

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename, true))) {
                outputStream.writeObject(book);
                System.out.println("Info: Article " + articleId + " added.");
            } catch (IOException e) {
                System.err.println("Error: Could not add the article.");
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

            if (articleExists(filename, articleId)) {
                throw new IllegalArgumentException("Error: Article already exists. (id=" + articleId + ")");
            }

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename, true))) {
                outputStream.writeObject(dvd);
                System.out.println("Info: Article " + articleId + " added.");
            } catch (IOException e) {
                System.err.println("Error: Could not add the article.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid parameter.");
        }
    }

    private static boolean articleExists(String filename, int articleId) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                Article article = (Article) inputStream.readObject();
                if (article.getId() == articleId) {
                    return true;
                }
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: Could not read articles.");
        }
        return false;
    }

    private static void listArticles(String filename) {
        List<Article> articles = new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                Article article = (Article) inputStream.readObject();
                articles.add(article);
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: Could not read articles.");
        }

        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }
}
