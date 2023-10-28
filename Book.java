public class Book extends Article {
    private int numPages;

    public Book(int id, String title, int releaseYear, String publisher, double basePrice, int numPages) {
        super(id, title, releaseYear, publisher, basePrice);
        setNumPages(numPages);
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        if (numPages <= 0) {
            throw new IllegalArgumentException("Number of pages must be positive.");
        }
        this.numPages = numPages;
    }

    @Override
    public double getDiscount() {
        int age = getAge();
        double discount = age * 0.05; // 5% discount per year
        if (discount > 0.3) {
            discount = 0.3; // Maximum discount of 30%
        }
        if (numPages > 1000) {
            discount += 0.03; // Additional 3% discount for books with more than 1000 pages
        }
        return discount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + getId() +
                ", Title='" + getTitle() + '\'' +
                ", Release Year=" + getReleaseYear() +
                ", Publisher='" + getPublisher() + '\'' +
                ", Base Price=" + getBasePrice() + " Euro" +
                ", Number of Pages=" + numPages +
                ", Age=" + getAge() + " years" +
                ", Discount=" + (getDiscount() * 100) + "%" +
                ", Price=" + getPrice() + " Euro" +
                '}';
    }
}


