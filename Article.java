/**
 * @author Dmytro Kostariev
 * @id 11848552
 */

import java.time.Year;
import java.util.Objects;
import java.io.Serializable;
public abstract class Article implements Serializable {
    private int id;
    private String title;
    private int releaseYear;
    private String publisher;
    private double basePrice;

    // Constructor
    public Article(int id, String title, int releaseYear, String publisher, double basePrice) {
        setId(id);
        setTitle(title);
        setReleaseYear(releaseYear);
        setPublisher(publisher);
        setBasePrice(basePrice);
    }

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Article ID cannot be negative.");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        int currentYear = Year.now().getValue();
        if (releaseYear > currentYear) {
            throw new IllegalArgumentException("Error: Invalid release year.");
        }
        this.releaseYear = releaseYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher == null || publisher.isEmpty()) {
            throw new IllegalArgumentException("Publisher cannot be null or empty.");
        }
        this.publisher = publisher;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative.");
        }
        this.basePrice = basePrice;
    }

    // Abstract method for getting the discount
    public abstract double getDiscount();

    // Method to calculate the age of the article
    public int getAge() {
        int currentYear = Year.now().getValue();
        return currentYear - releaseYear;
    }

    // Method to calculate the price of the article after applying the discount
    public double getPrice() {
        double discount = getDiscount();
        return basePrice - (basePrice * discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                releaseYear == article.releaseYear &&
                Double.compare(article.basePrice, basePrice) == 0 &&
                Objects.equals(title, article.title) &&
                Objects.equals(publisher, article.publisher);
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", publisher='" + publisher + '\'' +
                ", basePrice=" + basePrice +
                '}';
    }
}


