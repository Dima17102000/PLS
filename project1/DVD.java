/**
 * @author Dmytro Kostariev
 * @id 11848552
 */

//
public class DVD extends Article {
    private int lengthMinutes;
    private int ageRating; // 0 (no age restriction), 6, 12, 16, or 18

    public DVD(int id, String title, int releaseYear, String publisher, double basePrice, int lengthMinutes, int ageRating) {
        super(id, title, releaseYear, publisher, basePrice);
        setLengthMinutes(lengthMinutes);
        setAgeRating(ageRating);
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(int lengthMinutes) {
        if (lengthMinutes <= 0) {
            throw new IllegalArgumentException("Length must be positive.");
        }
        this.lengthMinutes = lengthMinutes;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        if (ageRating != 0 && ageRating != 6 && ageRating != 12 && ageRating != 16 && ageRating != 18) {
            throw new IllegalArgumentException("Invalid age rating.");
        }
        this.ageRating = ageRating;
    }

    @Override
    public double getDiscount() {
        switch (ageRating) {
            case 0:
                return 0.20; // 20% discount for no age restriction
            case 6:
                return 0.15; // 15% discount for ages 6 and up
            case 12:
                return 0.10; // 10% discount for ages 12 and up
            case 16:
                return 0.05; // 5% discount for ages 16 and up
            case 18:
                return 0.0;  // 0% discount for ages 18 and up
            default:
                return 0.0;  // Default to no discount
        }
    }

    @Override
    public String toString() {
        return "Type:       DVD" +
                "\nId:         " + getId() +
                "\nTitle:      " + getTitle() +
                "\nYear:       " + getReleaseYear() +
                "\nPublisher:  " + getPublisher() +
                "\nBase price: " + getBasePrice() +
                "\nPrice:      " +ArticleCLI.df.format(getPrice()) +
                "\nLength:     " + lengthMinutes +
                "\nAge rating: " + getAgeRating() + "\n";
    }

}

